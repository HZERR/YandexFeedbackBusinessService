package ru.hzerr.controller;

import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.Controller;
import ru.hzerr.fx.engine.core.language.Localization;

@FXEntity(fxml = "mailRuAccountMaster.fxml", internationalization = "mailRuAccountMaster.json", theme = "mailRuAccountMaster.css")
public class MailRuAccountMasterController extends Controller {

    @Override
    protected void onInit() {

    }

    @Override
    public void onChangeLanguage(Localization languagePack) {

    }

    @Override
    protected String id() {
        return null;
    }
}
