package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.ILocalization;

@FXController
@FXEntity(fxml = "appSettings.fxml", internationalization = "appSettings.json", theme = "appSettings.css")
public class AppSettingsController extends Controller {

    @FXML
    private ComboBox<?> themeComboBox, languageComboBox;

    @Override
    protected void onInit() {

    }

    @Override
    protected void onChangeLanguage(ILocalization localization) {

    }

    @Override
    protected String id() {
        return "appSettings";
    }
}
