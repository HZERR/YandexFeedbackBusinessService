package ru.hzerr.controller.listener;

import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.entity.IApplicationManager;
import ru.hzerr.fx.engine.core.javafx.subscriber.ChangeSubscriber;

import java.util.Locale;

@Registered
public class LanguageChangeSubscriber extends ChangeSubscriber<Locale, Locale> {

    private IApplicationManager applicationManager;

    @Override
    public void onAccept(Locale oLocale, Locale nLocale) {
        if (nonEquals(oLocale, nLocale)) {
            applicationManager.changeLanguage(nLocale);
            getLogProvider().getLogger().debug(STR."Язык приложения изменен на '\{nLocale.getLanguage()}'");
        }
    }

    private boolean nonEquals(Locale oLocale, Locale nLocale) {
        return !nLocale.equals(oLocale);
    }

    @Include
    public void setApplicationManager(IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }
}
