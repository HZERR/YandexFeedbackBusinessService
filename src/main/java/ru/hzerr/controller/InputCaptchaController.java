package ru.hzerr.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.PopupController;
import ru.hzerr.fx.engine.core.language.ILocalization;

@FXController
@FXEntity(fxml = "popup/inputCaptcha.fxml", internationalization = "popup/inputCaptcha.json", theme = "popup/inputCaptcha.css")
public class InputCaptchaController extends PopupController {

    @FXML private Label title;
    @FXML private ImageView captchaImage;
    @FXML private TextField captchaTextField;
    @FXML private Button apply, close;

    @Override
    protected void onInit() {
        popup.setAutoFix(true);
        popup.setAutoHide(false);
        popup.getContent().add(getContentAsParent());
    }

    @Override
    protected void onChangeLanguage(ILocalization localization) {
        title.setText(localization.getConfiguration().getString("inputCaptchaTitle"));
        close.setText(localization.getConfiguration().getString("close"));
        apply.setText(localization.getConfiguration().getString("apply"));
    }
}
