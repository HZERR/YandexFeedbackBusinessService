package ru.hzerr.configuration;

import ch.qos.logback.classic.Level;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.configuration.logging.ReadOnlyLoggingConfiguration;

import java.util.Locale;

@Registered
public class BaseReadOnlyLoggingConfiguration extends ReadOnlyLoggingConfiguration {

    @Override
    public Locale getEngineLocale() {
        return LOCALE_RU;
    }

    @Override
    public boolean isEngineLoggingEnabled() {
        return true;
    }

    @Override
    public Level getLoggerLevel() {
        return Level.DEBUG;
    }
}
