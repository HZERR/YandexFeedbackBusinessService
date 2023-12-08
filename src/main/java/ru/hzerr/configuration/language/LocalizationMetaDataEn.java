package ru.hzerr.configuration.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.ApplicationLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

import java.util.Locale;

@Registered
public class LocalizationMetaDataEn extends ApplicationLocalizationMetaData {

    protected LocalizationMetaDataEn() {
        super(Locale.ENGLISH, new BaseLocation("en-EN"), ConfigSyntax.JSON);
    }
}
