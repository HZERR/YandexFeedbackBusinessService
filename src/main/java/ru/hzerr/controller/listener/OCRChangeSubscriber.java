package ru.hzerr.controller.listener;

import ru.hzerr.configuration.application.IApplicationSettings;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.javafx.subscriber.ChangeSubscriber;

@Registered
public class OCRChangeSubscriber extends ChangeSubscriber<Boolean, Boolean> {

    private IApplicationSettings applicationSettings;

    @Override
    public void onAccept(Boolean oValue, Boolean nValue) {
        if (oValue == null || nValue == null) return;

        if (nonEquals(oValue, nValue)) {
            getLogProvider().getLogger().debug("Настройка OCR изменена на {}", nValue);
            applicationSettings.setOCREnabled(nValue);
        }
    }

    private boolean nonEquals(Boolean oValue, Boolean nValue) {
        return !oValue.equals(nValue);
    }

    @Include
    public void setApplicationSettings(IApplicationSettings applicationSettings) {
        this.applicationSettings = applicationSettings;
    }
}
