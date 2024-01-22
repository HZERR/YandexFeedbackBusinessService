package ru.hzerr.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.controller.processor.CreateMailRuAccountEventProcessor;
import ru.hzerr.controller.user.data.Block;
import ru.hzerr.controller.user.data.Sex;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.javafx.list.BasicCellFactory;
import ru.hzerr.fx.engine.core.javafx.list.BasicListCell;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.model.MailRuAccount;

import java.time.format.DateTimeFormatter;

@FXController
@FXEntity(fxml = "mailRuAccountMaster.fxml", internationalization = "managerMailRu.json", theme = "managerMailRu.css")
public class MailRuAccountMasterController extends Controller {

    @FXML private AnchorPane informationPane;
    @FXML private ListView<MailRuAccount> accountsList;

    @FXML
    private Label blockedFilledText, creationDateFilledText, dateOfBirthFilledText, firstNameFilledText, lastNameFilledText, loginFilledText, passwordFilledText, sexFilledText;
    @FXML
    private TextField creationDateTextField, dateOfBirthTextField, firstNameTextField, lastNameTextField, loginTextField, passwordTextField;
    @FXML
    private Label blockingText, sexText;
    @FXML
    private Button changeSelectedAccountButton, checkDataButton, createAccountButton, removeAccountButton, resetToDefaultButton;
    @FXML
    private ImageView switchBlockedImageView, switchSexImageView;

    private CreateMailRuAccountEventProcessor createMailRuAccountEventProcessor;
    private IEmailRepository<MailRuAccount> repository;

    @Override
    protected void onInit() {
        blockingText.setUserData(Block.NONE);
        sexText.setUserData(Sex.NONE);
        createMailRuAccountEventProcessor.setAccounts(accountsList);
        createAccountButton.setOnAction(createMailRuAccountEventProcessor);
        accountsList.setCellFactory(new BasicCellFactory<>() {
            @Override
            public BasicListCell<MailRuAccount> createListCell() {
                return new BasicListCell<>() {
                    @Override
                    public void onUpdateItem(MailRuAccount item, boolean empty) {
                        if (!empty) {
                            setText(item.getLogin());
                        }
                    }
                };
            }
        });
        accountsList.setItems(FXCollections.observableList(repository.getEmails()));
        accountsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        accountsList.getSelectionModel().selectedItemProperty().subscribe((oValue, nValue) -> {
            firstNameTextField.setText(nValue.getFirstName());
            lastNameTextField.setText(nValue.getLastName());
            loginTextField.setText(nValue.getLogin());
            passwordTextField.setText(nValue.getPassword());
            dateOfBirthTextField.setText(nValue.getDateOfBirth());
            creationDateTextField.setText(nValue.getCreatedDate().format(DateTimeFormatter.ISO_DATE));
//            if (nValue.getGender() == Gender.MALE) {
//                sexText.setUserData(Sex.MALE);
//                sexText.setText();
//            } else {
//                sexToggleButton.setText("женщина");
//                sexToggleButton.setSelected(false);
//            }
//            blockedToggleButton.setSelected(nValue.isBlocked());
        });
    }

    @Override
    public void onChangeLanguage(ILocalization localization) {
        createAccountButton.setText(localization.getConfiguration().getString("createAccountButtonText"));
        removeAccountButton.setText(localization.getConfiguration().getString("removeAccountButtonText"));
        changeSelectedAccountButton.setText(localization.getConfiguration().getString("changeSelectedAccountButtonText"));
        resetToDefaultButton.setText(localization.getConfiguration().getString("resetToDefaultButtonText"));
        checkDataButton.setText(localization.getConfiguration().getString("checkDataButtonText"));
        blockedFilledText.setText(localization.getConfiguration().getString("blockingFilledText"));
        creationDateFilledText.setText(localization.getConfiguration().getString("creationDateFilledText"));
        dateOfBirthFilledText.setText(localization.getConfiguration().getString("dateOfBirthFilledText"));
        firstNameFilledText.setText(localization.getConfiguration().getString("firstNameFilledText"));
        lastNameFilledText.setText(localization.getConfiguration().getString("lastNameFilledText"));
        loginFilledText.setText(localization.getConfiguration().getString("loginFilledText"));
        passwordFilledText.setText(localization.getConfiguration().getString("passwordFilledText"));
        sexFilledText.setText(localization.getConfiguration().getString("sexFilledText"));
    }

    @Override
    protected void onConnectDestroyEvent() {
    }

    @Override
    protected String id() {
        return "managerMailRu";
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
