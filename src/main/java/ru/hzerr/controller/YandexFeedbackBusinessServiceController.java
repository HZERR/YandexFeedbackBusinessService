package ru.hzerr.controller;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.language.Localization;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;

@Registered
@FXController
@FXEntity(fxml = "yandexFeedbackBusinessService.fxml", internationalization = "yandexFeedbackBusinessService.json", theme = "yandexFeedbackBusinessService.css")
public class YandexFeedbackBusinessServiceController extends Controller {

    private final Tab mailRuAccountManagerTab = new Tab();

    private ILogProvider logProvider;

    @Override
    public void onInit() {
        FXEngine.getContext().getEntityLoader().loadAsync(SpringLoadMetaData.from(MailRuAccountMasterController.class), Parent.class)
                .thenFXAccept(mailRuAccountMasterControllerParentEntity -> {
                    logProvider.getLogger().debug("Добавление Mail.Ru Account Master Tab...");
                    mailRuAccountManagerTab.setContent(mailRuAccountMasterControllerParentEntity.getNode());
                    getContent(TabPane.class).getTabs().add(0, mailRuAccountManagerTab);
                }).exceptionallyAsync(t -> {
                    t.printStackTrace();
                    return null;
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

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
