package ru.hzerr.controller;

import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;
import ru.hzerr.configuration.database.repository.IEmailRepository;
import ru.hzerr.controller.processor.CreateMailRuAccountEventProcessor;
import ru.hzerr.controller.template.MailRuAccountMasterControllerTemplate;
import ru.hzerr.controller.user.data.Block;
import ru.hzerr.controller.user.data.Sex;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.model.MailRuAccount;

import java.time.format.DateTimeFormatter;

@FXController
@FXEntity(fxml = "mailRuAccountMaster.fxml", internationalization = "managerMailRu.json", theme = "managerMailRu.css")
public class MailRuAccountMasterController extends MailRuAccountMasterControllerTemplate {

    private CreateMailRuAccountEventProcessor createMailRuAccountEventProcessor;
    private IEmailRepository<MailRuAccount> repository;

    @Override
    protected void onInit() {
        blockingText.setUserData(Block.NONE);
        sexText.setUserData(Sex.NONE);
        createMailRuAccountEventProcessor.setAccounts(accountsList);
        createAccountButton.setOnAction(createMailRuAccountEventProcessor);
        accountsList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<MailRuAccount> call(ListView<MailRuAccount> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(MailRuAccount item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setText(item.getLogin());
                        }
                    }
                };
            }
        });
        accountsList.setItems(FXCollections.observableList(repository.getEmails()));
        accountsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        accountsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            firstNameTextField.setText(newValue.getFirstName());
            lastNameTextField.setText(newValue.getLastName());
            loginTextField.setText(newValue.getLogin());
            passwordTextField.setText(newValue.getPassword());
            dateOfBirthTextField.setText(newValue.getDateOfBirth());
            creationDateTextField.setText(newValue.getCreatedDate().format(DateTimeFormatter.ISO_DATE));
//            if (newValue.getGender() == Gender.MALE) {
//                sexText.setUserData(Sex.MALE);
//                sexText.setText();
//            } else {
//                sexToggleButton.setText("женщина");
//                sexToggleButton.setSelected(false);
//            }
//            blockedToggleButton.setSelected(newValue.isBlocked());
        });
    }

    @Override
    public void onChangeLanguage(ILocalization localization) {
        createAccountButton.setText(localization.getConfiguration().getString("createButtonText"));
        removeAccountButton.setText(localization.getConfiguration().getString("removeButtonText"));
        changeSelectedAccount.setText(localization.getConfiguration().getString("changeSelectedButtonText"));
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
