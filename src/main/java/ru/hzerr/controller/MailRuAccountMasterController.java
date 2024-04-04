package ru.hzerr.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.controller.processor.CheckAccountProcessor;
import ru.hzerr.controller.processor.CreateMailRuAccountEventProcessor;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.EntityLoader;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.javafx.list.BasicCellFactory;
import ru.hzerr.fx.engine.core.javafx.list.BasicListCell;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.model.Gender;
import ru.hzerr.model.MailRuRecord;

import java.time.format.DateTimeFormatter;

@FXController
@FXEntity(fxml = "mailRuAccountMaster.fxml", internationalization = "managerMailRu.json", theme = "managerMailRu.css")
public class MailRuAccountMasterController extends Controller {

    @FXML private AnchorPane informationPane;
    @FXML private ListView<MailRuRecord> accountsList;

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
    private IEmailRepository<MailRuRecord> repository;

    @Override
    protected void onInit() {
        createMailRuAccountEventProcessor.setAccounts(accountsList);
        createAccountButton.setOnAction(createMailRuAccountEventProcessor);
        checkAccountProcessor.setListView(accountsList);
        checkDataButton.setOnAction(checkAccountProcessor);
        accountsList.setCellFactory(new BasicCellFactory<>() {
            @Override
            public BasicListCell<MailRuRecord> createListCell() {
                return new BasicListCell<>() {
                    @Override
                    public void onUpdateItem(MailRuRecord item, boolean empty) {
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
                acceptLocalizationProvider(localizationProvider -> sexText.setText(localizationProvider.getLocalization().getConfiguration().getString("sexMale")));
            } else
                acceptLocalizationProvider(localizationProvider -> sexText.setText(localizationProvider.getLocalization().getConfiguration().getString("sexFemale")));

            if (nValue.isBlocked()) {
                acceptLocalizationProvider(localizationProvider -> blockingText.setText(localizationProvider.getLocalization().getConfiguration().getString("blockingYes")));
            } else
                acceptLocalizationProvider(localizationProvider -> blockingText.setText(localizationProvider.getLocalization().getConfiguration().getString("blockingNo")));
        });

        accountsList.getSelectionModel().selectFirst();
        changeSelectedAccountButton.setOnAction(event -> entityLoader.view(SpringLoadMetaData.from(InputCaptchaController.class), Parent.class));
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
    public void setRepository(IEmailRepository<MailRuRecord> repository) {
        this.repository = repository;
    }

    @Include
    public void setEntityLoader(EntityLoader entityLoader) {
        this.entityLoader = entityLoader;
    }
}
