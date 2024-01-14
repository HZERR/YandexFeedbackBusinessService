package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

@FXController
@FXEntity(fxml = "yandexFeedbackBusinessService.fxml", internationalization = "yandexFeedbackBusinessService.json", theme = "yandexFeedbackBusinessService.css")
public class YandexFeedbackBusinessServiceController extends Controller {

    @FXML
    private AnchorPane content, tabContent;
    @FXML
    private Label creatorMailRuAccountTitleLabel, yandexAccountCreatorTitleLabel, settingsTitleLabel;

    private ILogProvider logProvider;

    @Override
    public void onInit() {
        FXEngine.getContext().getEntityLoader().loadAsync(SpringLoadMetaData.from(MailRuAccountMasterController.class), Parent.class)
                .thenFXAccept(mailRuAccountMasterControllerParentEntity -> {
                    logProvider.getLogger().debug("Добавление Mail.Ru Account Master Tab...");
                    tabContent.getChildren().addFirst(mailRuAccountMasterControllerParentEntity.getNode());
                }).exceptionallyAsync(t -> {
                    t.printStackTrace();
                    return null;
                });
    }

    @Override
    public void onChangeLanguage(ILocalization localization) {
        creatorMailRuAccountTitleLabel.setText(localization.getConfiguration().getString("mailRuAccountMasterTabText"));
        yandexAccountCreatorTitleLabel.setText(localization.getConfiguration().getString("yandexManagerTabText"));
        settingsTitleLabel.setText(localization.getConfiguration().getString("applicationSettingsTabText"));
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
