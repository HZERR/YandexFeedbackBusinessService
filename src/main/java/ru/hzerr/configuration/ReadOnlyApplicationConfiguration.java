package ru.hzerr.configuration;

import org.jetbrains.annotations.NotNull;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.configuration.application.IReadOnlyApplicationConfiguration;

import java.util.Locale;

@Registered
public class ReadOnlyApplicationConfiguration implements IReadOnlyApplicationConfiguration {

    @NotNull
    @Override
    public Locale getLocale() {
        return Locale.ENGLISH;
    }

    @NotNull
    @Override
    public String getThemeName() {
        return "White";
    }
}
