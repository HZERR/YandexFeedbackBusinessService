package ru.hzerr.service.mail;

import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;
import ru.hzerr.generator.ILoginGenerator;
import ru.hzerr.generator.MailRuCreationInterruptedException;
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

@Registered
public class MailRuService implements IEmailService {

    private final int LOGIN_CHARACTER_LENGTH = 12;
    private final ILoginGenerator loginGenerator = new RandomAlphanumericLoginGenerator(LOGIN_CHARACTER_LENGTH);
    private final IMailRuServiceSelectorNamingStrategy namingStrategy = new MailRuServiceSelectorNamingStrategy();
    private IProxyService proxyService;

    private ILogProvider logProvider;

    @Override
    public Response<MailRuRecord> create(RandomData data) throws MailRuCreationInterruptedException {
        MailRuRecord record = new MailRuRecord();
        record.setFirstName(data.getFirstName());
        record.setLastName(data.getLastName());
        record.setGender(data.getGender());
        record.setDateOfBirth(data.getDateOfBirth());

        logProvider.getLogger().debug("Запускаем браузер...");
        try (Browser browser = Browser.create(BrowserOptions.create()
                .setBrowserType(BrowserType.CHROMIUM)
                .setLaunchOptions(new com.microsoft.playwright.BrowserType.LaunchOptions().setHeadless(false)))) {

            try (Page page = browser.openPage("https://account.mail.ru/signup")) {
                logProvider.getLogger().debug("Заполняем форму...");

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

                record.setReservedEmail("FILL IT");
                page.fill(namingStrategy.reservedEmailSelector(), record.getReservedEmail());

                // if proxy invalid => reload form

                // click 'OK' button
                page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > button");
            }
        }

        return Response.from(record);
    }

    private boolean isInvalidLogin(Page page) {
        ElementHandle mailCreatedElement = page.querySelector(namingStrategy.invalidLoginSelector());
        return mailCreatedElement != null && mailCreatedElement.innerText().equalsIgnoreCase("Ящик с таким именем уже существует. Возможно, вам понравятся имена:");
    }

    private void sleep(int millis) throws MailRuCreationInterruptedException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            throw new MailRuCreationInterruptedException(ie);
        }
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    @Include
    public void setProxyService(IProxyService proxyService) {
        this.proxyService = proxyService;
    }
}
