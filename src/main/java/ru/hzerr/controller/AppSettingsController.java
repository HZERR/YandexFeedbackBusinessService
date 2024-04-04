package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import ru.hzerr.configuration.application.IApplicationSettings;
import ru.hzerr.controller.converter.LocaleToStringConverter;
import ru.hzerr.controller.listener.LanguageChangeSubscriber;
import ru.hzerr.controller.listener.OCRChangeSubscriber;
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
    @FXML
    private ComboBox<Boolean> ocrComboBox;

    private LanguageChangeSubscriber languageChangeSubscriber;
    private OCRChangeSubscriber ocrChangeSubscriber;
    private Locale LOCALE_RU = Locale.of("ru", "RU");
    private IApplicationSettings applicationSettings;

    @Override
    protected void onInit() {
        languageComboBox.getItems().addAll(LOCALE_RU, Locale.ENGLISH);
        languageComboBox.setConverter(new LocaleToStringConverter());
        languageComboBox.getSelectionModel().select(getApplicationConfiguration().getLocale());
        languageComboBox.getSelectionModel().selectedItemProperty().subscribe(languageChangeSubscriber);
        addLocalizationChangeListener((observable, oValue, nValue) -> {
            ocrComboBox.getItems().setAll(true, false);
            ocrComboBox.getSelectionModel().select(applicationSettings.isOCREnabled());
        });
        ocrComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Boolean ocrEnabled) {
                if (ocrEnabled == null) return null;

                return applyLocalizationProvider(localizationProvider -> localizationProvider.getLocalization().getConfiguration().getString(ocrEnabled ? "yes" : "no"));
            }

            @Override
            public Boolean fromString(String ocrEnabled) {
                if (ocrEnabled == null) return false;

                return applyLocalizationProvider(localizationProvider -> localizationProvider.getLocalization().getConfiguration().getString("yes").equals(ocrEnabled));
            }
        });
        ocrComboBox.getSelectionModel().selectedItemProperty().subscribe(ocrChangeSubscriber);
        ocrComboBox.getItems().addAll(true, false);
        ocrComboBox.getSelectionModel().select(applicationSettings.isOCREnabled());
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
    public void setApplicationSettings(IApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
    }

    @Include
    public void setLanguageChangeSubscriber(LanguageChangeSubscriber languageChangeSubscriber) {
        this.languageChangeSubscriber = languageChangeSubscriber;
    }

    @Include
    public void setOCRChangeSubscriber(OCRChangeSubscriber ocrChangeSubscriber) {
        this.ocrChangeSubscriber = ocrChangeSubscriber;
    }
}
