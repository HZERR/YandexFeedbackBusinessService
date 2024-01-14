package ru.hzerr.configuration.theme;

import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

@Registered
public class AsusThemeMetaData implements ThemeMetaData {
    @Override
    public String getName() {
        return "Asus";
    }

    @Override
    public String getPackage() {
        return "asus";
    }
}
