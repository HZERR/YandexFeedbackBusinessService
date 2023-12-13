package ru.hzerr.configuration.theme;

import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.theme.ThemeMetaData;

@Registered
public class WhiteThemeMetaData implements ThemeMetaData {
    @Override
    public String getName() {
        return "White";
    }

    @Override
    public String getPackage() {
        return "white";
    }
}
