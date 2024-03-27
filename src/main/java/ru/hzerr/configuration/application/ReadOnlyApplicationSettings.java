package ru.hzerr.configuration.application;

import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
public class ReadOnlyApplicationSettings implements IReadOnlyApplicationSettings {

    @Override
    public boolean isOCREnabled() {
        return false;
    }
}
