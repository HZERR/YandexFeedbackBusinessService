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
import ru.hzerr.fx.engine.core.javafx.list.BasicCellFactory;
import ru.hzerr.fx.engine.core.javafx.list.BasicListCell;
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
        getLogProvider().getLogger().debug("Изменение локализации...");
        createAccountButton.setText(localization.getConfiguration().getString("createButtonText"));
        removeAccountButton.setText(localization.getConfiguration().getString("removeButtonText"));
        changeSelectedAccount.setText(localization.getConfiguration().getString("changeSelectedButtonText"));
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
