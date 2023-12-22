package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.controller.processor.CreateMailRuAccountEventProcessor;
import ru.hzerr.fx.engine.core.annotation.*;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.model.MailRuAccount;

@Registered
@FXController
@FXEntity(fxml = "mailRuAccountMaster.fxml", internationalization = "mailRuAccountMaster.json", theme = "mailRuAccountMaster.css")
public class MailRuAccountMasterController extends Controller {

    @FXML
    private Button createAccountButton;
    @FXML
    private Button removeAccountButton;
    @FXML
    private Button changeSelectedAccount;
    @FXML
    private ListView<MailRuAccount> accountsList;

    private CreateMailRuAccountEventProcessor createMailRuAccountEventProcessor;
    private IEmailRepository<MailRuAccount> repository;

    @Override
    protected void onInit() {
        accountsList.getItems().add(repository.getEmail("hSoA8wgJZHpu"));
        createMailRuAccountEventProcessor.setAccounts(accountsList);
        createAccountButton.setOnAction(createMailRuAccountEventProcessor);
    }

    @Override
    public void onChangeLanguage(Localization localization) {

    }

    @Override
    protected String id() {
        return null;
    }

    @Include
    public void setCreateMailRuAccountEventProcessor(CreateMailRuAccountEventProcessor createMailRuAccountEventProcessor) {
        this.createMailRuAccountEventProcessor = createMailRuAccountEventProcessor;
    }

    @Include
    public void setRepository(IEmailRepository<MailRuAccount> repository) {
        this.repository = repository;
    }
}
