package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import ru.hzerr.controller.converter.LocaleToStringConverter;
import ru.hzerr.controller.listener.LanguageChangeSubscriber;
import ru.hzerr.fx.engine.configuration.application.IApplicationConfiguration;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.ILocalization;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

import java.util.Locale;

@FXController
@FXEntity(fxml = "appSettings.fxml", internationalization = "appSettings.json", theme = "appSettings.css")
public class AppSettingsController extends Controller {

    @FXML
    private ComboBox<Locale> languageComboBox;
    @FXML
    private ComboBox<Class<? extends ThemeMetaData>> themeComboBox;

    private LanguageChangeSubscriber languageChangeSubscriber;
    private Locale LOCALE_RU = Locale.of("ru", "RU");
    private IApplicationConfiguration applicationConfiguration;

    @Override
    protected void onInit() {
        languageComboBox.getItems().addAll(LOCALE_RU, Locale.ENGLISH);
        languageComboBox.setConverter(new LocaleToStringConverter());
        languageComboBox.getSelectionModel().select(applicationConfiguration.getLocale());
        languageComboBox.getSelectionModel().selectedItemProperty().subscribe(languageChangeSubscriber);
    }

    @Override
    protected void onConnectDestroyEvent() {
    }

    @Override
    protected void onChangeLanguage(ILocalization localization) {

    }

    @Override
    protected String id() {
        return "appSettings";
    }

    @Include
    public void setApplicationConfiguration(IApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Include
    public void setLanguageChangeSubscriber(LanguageChangeSubscriber languageChangeSubscriber) {
        this.languageChangeSubscriber = languageChangeSubscriber;
    }
}
