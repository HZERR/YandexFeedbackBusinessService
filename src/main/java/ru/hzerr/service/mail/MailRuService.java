package ru.hzerr.service.mail;

import com.google.common.base.Strings;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import javafx.application.Platform;
import javafx.scene.Parent;
import ru.hzerr.configuration.application.IApplicationSettings;
import ru.hzerr.controller.InputCaptchaController;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.concurrent.ExtendedCompletableFuture;
import ru.hzerr.fx.engine.core.entity.IEntityLoader;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.entity.exception.LoadControllerException;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;
import ru.hzerr.generator.ILoginGenerator;
import ru.hzerr.service.mail.exception.*;
import ru.hzerr.generator.RandomAlphanumericLoginGenerator;
import ru.hzerr.generator.RandomData;
import ru.hzerr.model.Gender;
import ru.hzerr.model.MailRuRecord;
import ru.hzerr.playwright.Browser;
import ru.hzerr.playwright.BrowserOptions;
import ru.hzerr.playwright.BrowserType;
import ru.hzerr.service.mail.naming.strategy.IMailRuServiceSelectorNamingStrategy;
import ru.hzerr.service.mail.naming.strategy.MailRuServiceSelectorNamingStrategy;
import ru.hzerr.service.proxy.IProxyService;

import java.io.IOException;
import java.time.LocalDateTime;

@Registered
public class MailRuService implements IEmailService {

    private final int LOGIN_CHARACTER_LENGTH = 12;
    private final ILoginGenerator loginGenerator = new RandomAlphanumericLoginGenerator(LOGIN_CHARACTER_LENGTH);
    private final IMailRuServiceSelectorNamingStrategy namingStrategy = new MailRuServiceSelectorNamingStrategy();

    private IApplicationSettings applicationSettings;
    private IProxyService proxyService;

    private IEntityLoader entityLoader;

    private ILogProvider logProvider;

    @Override
    public MailRuRecord create(RandomData data) throws MailRuCreationException {
        MailRuRecord record = new MailRuRecord();
        record.setFirstName(data.getFirstName());
        record.setLastName(data.getLastName());
        record.setGender(data.getGender());
        record.setDateOfBirth(data.getDateOfBirth());

        logProvider.getLogger().debug("Запускаем браузер...");
        try (Browser browser = Browser.create(BrowserOptions.create()
                .setBrowserType(BrowserType.CHROMIUM)
                .setLaunchOptions(new com.microsoft.playwright.BrowserType.LaunchOptions().setHeadless(true)))) {

            try (Page page = browser.openPage("https://account.mail.ru/signup")) {
                logProvider.getLogger().debug("Заполняем форму...");
                page.waitForLoadState(LoadState.NETWORKIDLE);

                page.fill(namingStrategy.firstNameSelector(), record.getFirstName());
                page.fill(namingStrategy.lastNameSelector(), record.getLastName());

                String[] dateOfBirth = record.getDateOfBirth().split("\\.");

                page.click(namingStrategy.openDaysSelector());
                page.click(namingStrategy.calculateTargetDaySelector(dateOfBirth[0]));

                page.click(namingStrategy.openMonthsSelector());
                page.click(namingStrategy.calculateTargetMonthSelector(dateOfBirth[1]));

                page.click(namingStrategy.openYearsSelector());
                page.click(namingStrategy.calculateTargetYearSelector(dateOfBirth[2]));

                page.click(record.getGender() == Gender.FEMALE ?
                        namingStrategy.femaleSelector() :
                        namingStrategy.maleSelector());

                do {
                    record.setLogin(loginGenerator.generate());
                    page.fill(namingStrategy.loginSelector(), record.getLogin());
                    sleep(1000);
                } while (isInvalidLogin(page));
                record.setLogin(STR."\{record.getLogin()}@mail.ru");

                page.click(namingStrategy.generatePasswordSelector());
                record.setPassword(page.locator("#password").inputValue());

                page.click(namingStrategy.changeFocusSelector());

                page.click(namingStrategy.showReservedEmailInputSelector());

                record.setReservedEmail("vadimvyazloy@yandex.ru");
                page.fill(namingStrategy.reservedEmailSelector(), record.getReservedEmail());

                // if proxy invalid => change proxy and reload form

                // click 'OK' button
                page.click(namingStrategy.switchCaptchaFormSelector());

                bypass(page);

                page.waitForURL(url -> url.contains("e.mail.ru/inbox"));

                record.setCreated(true);
                record.setCreatedDate(LocalDateTime.now());
                logProvider.getLogger().debug(STR."Аккаунт успешно создан: \{record}");
            }
        }

        return record;
    }

    private void bypass(Page page) throws MailRuCreationEntityLoadException, MailRuCreationInterruptedException, MailRuCreationCancelledException, MailRuUnknownFooterTextException {
        ElementHandle captchaLocator = page.waitForSelector(namingStrategy.captchaImageSelector());

        // wait loading the captcha
        sleep(2000);

        byte[] captcha = captchaLocator.screenshot();

        String decoded = decode(captcha);

        if (Strings.isNullOrEmpty(decoded)) {
            throw new MailRuCreationCancelledException("Операция была прервана пользователем");
        }

        page.fill(namingStrategy.captchaTextFieldSelector(), decoded);
        page.click(namingStrategy.applyCaptchaSelector());

        page.waitForLoadState(LoadState.NETWORKIDLE);

        ElementHandle errorElement = page.querySelector(namingStrategy.errorSelector());
        if (errorElement != null) {
            if (errorElement.innerText().equalsIgnoreCase("Вы указали неправильный код с картинки")) {

                page.click(namingStrategy.reloadCaptchaSelector());
                page.waitForLoadState(LoadState.NETWORKIDLE);

                bypass(page);
                return;
            }

            logProvider.getLogger().warn("Неизвестная ошибка отображаемая в error-footer-text: {}", errorElement.innerText());
            throw new MailRuUnknownFooterTextException(STR."Неизвестная ошибка отображаемая в error-footer-text: \{errorElement.innerText()}");
        }
    }

    private String decode(byte[] captcha) throws MailRuCreationEntityLoadException, MailRuCreationInterruptedException {
        boolean ocrEnabled = applicationSettings.isOCREnabled();
        // if (ocrEnabled) {} else {} e.t.c

        InputCaptchaController controller;

        try {
            controller = entityLoader.view(SpringLoadMetaData.from(InputCaptchaController.class, (Object) captcha), Parent.class)
                    .getController();
        } catch (IOException | LoadControllerException e) {
            throw new MailRuCreationEntityLoadException(e.getMessage(), e);
        }

        try {
            return controller.awaitDecodedCaptcha();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MailRuCreationInterruptedException(e.getMessage(), e);
        }
    }

    private boolean isInvalidLogin(Page page) {
        ElementHandle mailCreatedElement = page.querySelector(namingStrategy.invalidLoginSelector());
        return mailCreatedElement != null && mailCreatedElement.innerText().equalsIgnoreCase("Ящик с таким именем уже существует. Возможно, вам понравятся имена:");
    }

    private void sleep(int millis) throws MailRuCreationInterruptedException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new MailRuCreationInterruptedException(ie.getMessage(), ie);
        }
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    @Include
    public void setEntityLoader(IEntityLoader entityLoader) {
        this.entityLoader = entityLoader;
    }

    @Include
    public void setProxyService(IProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @Include
    public void setApplicationSettings(IApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
    }
}
