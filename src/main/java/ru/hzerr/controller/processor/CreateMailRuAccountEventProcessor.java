package ru.hzerr.controller.processor;

import com.microsoft.playwright.*;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.javafx.event.async.AsyncActionEventProcessor;
import ru.hzerr.generator.*;
import ru.hzerr.model.Gender;
import ru.hzerr.model.MailRuRecord;
import ru.hzerr.service.mail.IEmailService;
import ru.hzerr.service.mail.Response;

import java.time.LocalDateTime;

// TODO НАПИСАТЬ СПЯЩИЙ РЕЖИМ ПРОГРАММЫ ПРИ СОЗДАНИИ АККАУНТА
@Registered
public class CreateMailRuAccountEventProcessor extends AsyncActionEventProcessor {

    public static final int LOGIN_CHARACTER_LENGTH = 12;
    private ListView<MailRuRecord> accounts;

    private IEmailService emailService;
    private final ILoginGenerator loginGenerator = new RandomAlphanumericLoginGenerator(LOGIN_CHARACTER_LENGTH);
    private RandomDataToolsGenerator randomDataGenerator;
    private final IEmailRepository<MailRuRecord> repository;

    private static final String BACKUP_EMAIL_ADDRESS = "vadimvyazloy@yandex.ru";

    private final Converter<RandomData, MailRuRecord> mailRuAccountConverter = new GenerationResultToMailRuAccountConverter();

    @Include
    public CreateMailRuAccountEventProcessor(IEmailRepository<MailRuRecord> repository) {
        this.repository = repository;
    }

    @Override
    protected void onProcess(ActionEvent actionEvent) throws Exception {
        getLogProvider().getLogger().debug("Генерируем данные...");
        RandomData randomData = randomDataGenerator
                .addFirstName()
                .addLastName()
                .addDateOfBirth()
                .addGender()
                .generate();

        Response<MailRuRecord> recordResponse = emailService.create(randomData);
        if (recordResponse.isSuccess()) {
            repository.addEmail(recordResponse.getResponse());
            getLogProvider().getLogger().debug(STR."Аккаунт \{recordResponse.getResponse().getLogin()} успешно зарегистрирован в базе данных");
            accounts.getItems().add(recordResponse.getResponse());
        } else
            getLogProvider().getLogger().debug("Операция создания аккаунта была отменена пользователем");

        if (true) {
            return;
        }

        getLogProvider().getLogger().debug("Запускаем браузер...");
        try (Playwright playwright = Playwright.create()) {
            try (Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false))) {
                try (BrowserContext context = browser.newContext()) {
                    try (Page page = context.newPage()) {
                        getLogProvider().getLogger().debug("Заполняем форму...");
                        MailRuRecord account = mailRuAccountConverter.convert(randomData);
                        page.navigate("https://account.mail.ru/signup");
                        page.fill("#fname", account.getFirstName());
                        page.fill("#lname", account.getLastName());
                        String[] dateOfBirth = account.getDateOfBirth().split("\\.");

                        page.click("//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[1]");
                        page.click(STR."#react-select-2-option-\{Integer.parseInt(dateOfBirth[0]) - 1}");
                        page.click("//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[3]");
                        page.click(STR."#react-select-3-option-\{Integer.parseInt(dateOfBirth[1]) - 1}");
                        page.click("//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[6]/div[2]/div/div/div/div[5]");
                        page.click(STR."#react-select-4-option-\{2023 - Integer.parseInt(dateOfBirth[2])}");

                        page.click(account.getGender() == Gender.FEMALE ?
                                "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[9]/div[2]/div/label[2]/div[1]" :
                                "//*[@id=\"root\"]/div/div[4]/div[4]/div/div/div/div/form/div[9]/div[2]/div/label[1]/div[1]", new Page.ClickOptions().setTimeout(100000D));

                        do {
                            account.setLogin(loginGenerator.generate());
                            page.fill("#aaa__input", account.getLogin());
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ie) {
                                throw new MailRuCreationInterruptedException(ie);
                            }
                        } while (isInvalidLogin(page));
                        account.setLogin(STR."\{account.getLogin()}@mail.ru");

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
                        getLogProvider().getLogger().debug(STR."Аккаунт \{account.getLogin()} успешно создан");
                        repository.addEmail(account);
                        getLogProvider().getLogger().debug(STR."Аккаунт \{account.getLogin()} успешно зарегистрирован в базе данных");
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
        getLogProvider().getLogger().error("CreateMailRuAccountEventProcessor", e);
    }

    @Include
    public void setRandomDataGenerator(RandomDataToolsGenerator randomDataGenerator) {
        this.randomDataGenerator = randomDataGenerator;
    }

    public void setAccounts(ListView<MailRuRecord> accounts) {
        this.accounts = accounts;
    }

    @Include
    public void setEmailService(IEmailService emailService) {
        this.emailService = emailService;
    }
}
