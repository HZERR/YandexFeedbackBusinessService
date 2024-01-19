package ru.hzerr.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.SVGPath;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.entity.Entity;
import ru.hzerr.fx.engine.core.entity.SpringLoadMetaData;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.function.Consumer;

@FXController
@FXEntity(fxml = "yandexFeedbackBusinessService.fxml", internationalization = "yandexFeedbackBusinessService.json", theme = "yandexFeedbackBusinessService.css")
public class YandexFeedbackBusinessServiceController extends Controller {

    @FXML
    private AnchorPane content, tabContent;
    @FXML
    private Label managerMailRuTitleLabel, managerYandexTitleLabel, appSettingsTitleLabel;

    @FXML
    private SVGPath managerMailRuTab, managerYandexTab, appSettingsTab;

    private Entity<MailRuAccountMasterController, Parent> managerMailRuEntity;
    private Entity<AppSettingsController, Parent> appSettingsEntity;

    @Override
    public void onInit() {
        managerMailRuTab.setOnMouseClicked(event -> {
            if (managerMailRuEntity != null) {
                tabContent.getChildren().clear();
                tabContent.getChildren().addFirst(managerMailRuEntity.getNode());
            }
        });

        appSettingsTab.setOnMouseClicked(event -> {
            if (appSettingsEntity != null) {
                tabContent.getChildren().clear();
                tabContent.getChildren().addFirst(appSettingsEntity.getNode());
            }
        });

        FXEngine.getContext().getEntityLoader().loadAsync(SpringLoadMetaData.from(MailRuAccountMasterController.class), Parent.class)
                .thenFXAccept(entity -> {
                    managerMailRuEntity = entity;
                    getLogProvider().getLogger().debug("Добавление вкладки 'Mail.Ru менеджер'");
                    tabContent.getChildren().addFirst(entity.getNode());
                }).exceptionallyAsync((Consumer<Throwable>) Throwable::printStackTrace);
        FXEngine.getContext().getEntityLoader().loadAsync(SpringLoadMetaData.from(AppSettingsController.class), Parent.class)
                .thenFXAccept(entity -> {
                    getLogProvider().getLogger().debug("Добавление вкладки 'Настройки приложения'");
                    appSettingsEntity = entity;
                }).exceptionallyAsync((Consumer<Throwable>) Throwable::printStackTrace);
    }

    @Override
    public void onChangeLanguage(ILocalization localization) {
        managerMailRuTitleLabel.setText(localization.getConfiguration().getString("mailRuAccountMasterTabText"));
        managerYandexTitleLabel.setText(localization.getConfiguration().getString("yandexManagerTabText"));
        appSettingsTitleLabel.setText(localization.getConfiguration().getString("applicationSettingsTabText"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        managerMailRuEntity.getController().onDestroy();
        appSettingsEntity.getController().onDestroy();
    }

    @Override
    protected String id() {
        return "yandexFeedbackBusinessServiceController";
    }
}
