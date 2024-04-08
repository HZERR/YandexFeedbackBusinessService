package ru.hzerr.controller.processor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.javafx.event.async.AsyncActionEventProcessor;
import ru.hzerr.generator.RandomData;
import ru.hzerr.generator.RandomDataToolsGenerator;
import ru.hzerr.model.MailRuRecord;
import ru.hzerr.service.mail.IEmailService;

// TODO НАПИСАТЬ СПЯЩИЙ РЕЖИМ ПРОГРАММЫ ПРИ СОЗДАНИИ АККАУНТА
@Registered
public class CreateMailRuAccountEventProcessor extends AsyncActionEventProcessor {

    private ListView<MailRuRecord> accounts;

    private IEmailService emailService;
    private RandomDataToolsGenerator randomDataGenerator;
    private final IEmailRepository<MailRuRecord> repository;

    @Include
    public CreateMailRuAccountEventProcessor(IEmailRepository<MailRuRecord> repository) {
        this.repository = repository;
    }

    @Override
    protected void onProcess(ActionEvent actionEvent) throws Exception {
        Platform.runLater(() -> accounts.getScene().getRoot().setDisable(true));

        getLogProvider().getLogger().debug("Генерируем данные...");
        RandomData randomData = randomDataGenerator
                .addFirstName()
                .addLastName()
                .addDateOfBirth()
                .addGender()
                .generate();

        MailRuRecord record = emailService.create(randomData);
        repository.addEmail(record);
        getLogProvider().getLogger().debug(STR."Аккаунт \{record.getLogin()} успешно зарегистрирован в базе данных");
        accounts.getItems().add(record);
        getLogProvider().getLogger().debug("Операция создания аккаунта была отменена пользователем");

        Platform.runLater(() -> accounts.getScene().getRoot().setDisable(false));
    }

    @Override
    protected void onFailure(Exception e) {
        getLogProvider().getLogger().error("CreateMailRuAccountEventProcessor", e);
        Platform.runLater(() -> accounts.getScene().getRoot().setDisable(false));
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
