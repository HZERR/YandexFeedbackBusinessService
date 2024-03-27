package ru.hzerr.configuration.application;

public class ApplicationSettingsNamingStrategy implements IApplicationSettingsNamingStrategy  {

    @Override
    public String ocrEnabled() {
        return "ocr.enabled";
    }
}
