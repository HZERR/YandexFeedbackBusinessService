package ru.hzerr.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.controller.processor.CheckAccountProcessor;
import ru.hzerr.controller.processor.CreateMailRuAccountEventProcessor;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.concurrent.function.FXConsumer;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.Entity;
import ru.hzerr.fx.engine.core.entity.EntityLoader;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.javafx.list.BasicCellFactory;
import ru.hzerr.fx.engine.core.javafx.list.BasicListCell;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.model.Gender;
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

    private EntityLoader entityLoader;

    private CreateMailRuAccountEventProcessor createMailRuAccountEventProcessor;
    private CheckAccountProcessor checkAccountProcessor;
    private IEmailRepository<MailRuAccount> repository;

    @Override
    protected void onInit() {
        createMailRuAccountEventProcessor.setAccounts(accountsList);
        createAccountButton.setOnAction(createMailRuAccountEventProcessor);
        checkAccountProcessor.setListView(accountsList);
        checkDataButton.setOnAction(checkAccountProcessor);
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
            if (nValue.getGender() == Gender.MALE) {
                sexText.setText(getLocalizationProvider().getLocalization().getConfiguration().getString("sexMale"));
            } else
                sexText.setText(getLocalizationProvider().getLocalization().getConfiguration().getString("sexFemale"));

            if (nValue.isBlocked()) {
                blockingText.setText(getLocalizationProvider().getLocalization().getConfiguration().getString("blockingYes"));
            } else
                blockingText.setText(getLocalizationProvider().getLocalization().getConfiguration().getString("blockingNo"));
        });

        accountsList.getSelectionModel().selectFirst();
        changeSelectedAccountButton.setOnAction(event -> {
            getLogProvider().getLogger().info("Stage before init: {}", FXEngine.getContext().getStage());
            FXEngine.getContext().setStage((Stage)((Node) event.getSource()).getScene().getWindow());
            getLogProvider().getLogger().info("Stage after init: {}", FXEngine.getContext().getStage());
            entityLoader.loadAsync(SpringLoadMetaData.from(InputCaptchaController.class), Parent.class).thenFXAccept(entity -> {
                InputCaptchaController inputCaptchaController = entity.getController();
                inputCaptchaController.view();
            });
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
        if (selectedItemExists()) {
            if (accountsList.getSelectionModel().getSelectedItem().getGender() == Gender.MALE) {
                sexText.setText(localization.getConfiguration().getString("sexMale"));
            } else
                sexText.setText(localization.getConfiguration().getString("sexFemale"));

            if (accountsList.getSelectionModel().getSelectedItem().isBlocked()) {
                blockingText.setText(localization.getConfiguration().getString("blockingYes"));
            } else
                blockingText.setText(localization.getConfiguration().getString("blockingNo"));
        }
    }

    @Override
    protected void onConnectDestroyEvent() {
    }

    @Override
    protected String id() {
        return "managerMailRu";
    }

    private boolean selectedItemExists() {
        return !accountsList.getSelectionModel().isEmpty();
    }

    @Include
    public void setCreateMailRuAccountEventProcessor(CreateMailRuAccountEventProcessor createMailRuAccountEventProcessor) {
        this.createMailRuAccountEventProcessor = createMailRuAccountEventProcessor;
    }

    @Include
    public void setCheckAccountProcessor(CheckAccountProcessor checkAccountProcessor) {
        this.checkAccountProcessor = checkAccountProcessor;
    }

    @Include
    public void setRepository(IEmailRepository<MailRuAccount> repository) {
        this.repository = repository;
    }

    @Include
    public void setEntityLoader(EntityLoader entityLoader) {
        this.entityLoader = entityLoader;
    }
}
