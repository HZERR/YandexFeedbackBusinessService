package ru.hzerr.configuration.theme;

import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

@Registered
public class DarkThemeMetaData implements ThemeMetaData {

    @Override
    public String getName() {
        return "Dark";
    }

    @Override
    public String getPackage() {
        return "dark";
    }
}
