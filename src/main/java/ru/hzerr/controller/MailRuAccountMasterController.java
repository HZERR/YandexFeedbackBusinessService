package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

    @Override
    protected void onInit() {
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
}
