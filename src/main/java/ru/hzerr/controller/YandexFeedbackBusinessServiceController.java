package ru.hzerr.controller;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.ControllerLoadMetaData;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

@FXEntity(fxml = "yandexFeedbackBusinessService.fxml", internationalization = "yandexFeedbackBusinessService.json", theme = "yandexFeedbackBusinessService.css")
public class YandexFeedbackBusinessServiceController extends Controller {

    private final Tab mailRuAccountManagerTab = new Tab();

    private final ILogProvider logProvider = FXEngine.getContext().getApplicationLogProvider();

    @Override
    public void onInit() {
        FXEngine.getContext().getEntityLoader().loadAsync(ControllerLoadMetaData.from(MailRuAccountMasterController.class), Parent.class).thenAccept(mailRuAccountMasterControllerParentEntity -> {
            Platform.runLater(() -> {
                mailRuAccountManagerTab.setContent(mailRuAccountMasterControllerParentEntity.getNode());
                getContent(TabPane.class).getTabs().add(0, mailRuAccountManagerTab);
            });
        });
    }

    @Override
    public void onChangeLanguage(Localization localization) {
        mailRuAccountManagerTab.setText(localization.getConfiguration().getString("mailRuAccountMaster"));
    }

    @Override
    protected String id() {
        return "yandexFeedbackBusinessServiceController";
    }
}
