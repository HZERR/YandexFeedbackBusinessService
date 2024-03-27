package ru.hzerr.configuration.application;

import org.apache.commons.configuration2.PropertiesConfiguration;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
public class ApplicationSettings implements IApplicationSettings {

    private final PropertiesConfiguration applicationSettingsConfiguration;
    private final IReadOnlyApplicationSettings readOnlyApplicationSettings;
    private final IApplicationSettingsNamingStrategy applicationSettingsNamingStrategy = new ApplicationSettingsNamingStrategy();

    private ApplicationSettings(@IncludeAs("applicationSettingsConfiguration") PropertiesConfiguration applicationSettingsConfiguration, IReadOnlyApplicationSettings readOnlyApplicationSettings) {
        this.applicationSettingsConfiguration = applicationSettingsConfiguration;
        this.readOnlyApplicationSettings = readOnlyApplicationSettings;
    }

    @Override
    public boolean isOCREnabled() {
        return applicationSettingsConfiguration.getBoolean(applicationSettingsNamingStrategy.ocrEnabled(), readOnlyApplicationSettings.isOCREnabled());
    }

    @Override
    public void setOCREnabled(boolean enable) {
        applicationSettingsConfiguration.setProperty(applicationSettingsNamingStrategy.ocrEnabled(), enable);
    }
}
