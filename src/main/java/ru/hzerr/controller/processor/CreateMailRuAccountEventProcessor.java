package ru.hzerr.controller.processor;

import com.microsoft.playwright.*;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import ru.hzerr.configuration.database.IEmailRepository;
import ru.hzerr.fx.engine.core.annotation.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.event.ActionEventProcessor;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;
import ru.hzerr.generator.*;
import ru.hzerr.model.Gender;
import ru.hzerr.model.MailRuAccount;

import java.time.LocalDateTime;

@Registered
public class CreateMailRuAccountEventProcessor extends ActionEventProcessor {

    public static final int LOGIN_CHARACTER_LENGTH = 12;
    private ListView<MailRuAccount> accounts;
    private final ILoginGenerator loginGenerator = new RandomAlphanumericLoginGenerator(LOGIN_CHARACTER_LENGTH);
    private RandomDataToolsGenerator randomDataGenerator;
    private final IEmailRepository<MailRuAccount> repository;
    private ILogProvider logProvider;

    private static final String BACKUP_EMAIL_ADDRESS = "vadimvyazloy@yandex.ru";

    private final Converter<GenerationResult, MailRuAccount> mailRuAccountConverter = new GenerationResultToMailRuAccountConverter();

    @Include
    public CreateMailRuAccountEventProcessor(IEmailRepository<MailRuAccount> repository) {
        this.repository = repository;
    }

    @Override
    protected void onProcess(ActionEvent actionEvent) throws Exception {
        logProvider.getLogger().debug("Генерируем данные...");
        GenerationResult generationResult = randomDataGenerator
                .addFirstName()
                .addLastName()
                .addDateOfBirth()
                .addGender()
                .generate();

        logProvider.getLogger().debug("Запускаем браузер...");
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false))) {
                try (BrowserContext context = browser.newContext()) {
                    try (Page page = context.newPage()) {
                        logProvider.getLogger().debug("Заполняем форму...");
                        MailRuAccount account = new GenerationResultToMailRuAccountConverter().convert(generationResult);
                        page.navigate("https://account.mail.ru/signup");
                        page.fill("#fname", account.getFirstName());
                        page.fill("#lname", account.getLastName());
                        String[] dateOfBirth = account.getDateOfBirth().split("\\.");

                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(7) > div:nth-child(2) > div > div > div > div.select-0-2-127.daySelect-0-2-128 > div > div > div");
                        page.click(STR."#react-select-2-option-\{Integer.parseInt(dateOfBirth[0]) - 1}");
                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(7) > div:nth-child(2) > div > div > div > div.base-0-2-99");
                        page.click(STR."#react-select-3-option-\{Integer.parseInt(dateOfBirth[1]) - 1}");
                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(7) > div:nth-child(2) > div > div > div > div.select-0-2-127.yearSelect-0-2-129");
                        page.click(STR."#react-select-4-option-\{2023 - Integer.parseInt(dateOfBirth[2])}");

                        page.click(account.getGender() == Gender.FEMALE ?
                                "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(10) > div:nth-child(2) > div > label:nth-child(3) > div.radio-0-2-146" :
                                "#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(10) > div:nth-child(2) > div > label:nth-child(1) > div.radio-0-2-146");

                        do {
                            account.setLogin(loginGenerator.generate());
                            page.fill("#aaa__input", account.getLogin());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                                throw new MailRuCreationInterruptedException(ie);
                            }
                        } while (isInvalidLogin(page));

                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div._14NpOeQyQLsxKPw9X2JBIg > span > a");
                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(16) > span");
                        account.setPassword(page.locator("#password").inputValue());

                        // change focus
                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > h3");
                        // hide phone, show email
                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(19) > span");
                        account.setReservedEmail(BACKUP_EMAIL_ADDRESS);
                        page.fill("#extra-email", account.getReservedEmail());

                        page.click("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > button");
                        account.setCreated(true);
                        account.setCreatedDate(LocalDateTime.now());
                        logProvider.getLogger().debug(STR."Аккаунт \{account.getLogin()} успешно создан");
                        logProvider.getLogger().debug(STR."Регистрация аккаунта \{account.getLogin()} в системе...");
                        repository.addEmail(account);
                        accounts.getItems().add(account);
                    }
                }
            }
        }
    }

    private boolean isInvalidLogin(Page page) {
        ElementHandle mailCreatedElement = page.querySelector("#root > div > div.-iGylzk8u50zKdna3C_sh > div:nth-child(4) > div > div > div > div > form > div:nth-child(13) > div > div:nth-child(2) > div.base-1-2-8.hiddenDesktop-1-2-10 > div:nth-child(3) > div > small");
        return mailCreatedElement != null && mailCreatedElement.innerText().equalsIgnoreCase("Ящик с таким именем уже существует. Возможно, вам понравятся имена:");
    }

    @Override
    protected void onFailure(Exception e) {
        logProvider.getLogger().error("CreateMailRuAccountEventProcessor", e);
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    @Include
    public void setRandomDataGenerator(RandomDataToolsGenerator randomDataGenerator) {
        this.randomDataGenerator = randomDataGenerator;
    }
}
