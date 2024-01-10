package ru.hzerr.configuration.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.EntityLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

import java.util.Locale;

@Registered
public class LocalizationMetaDataEn extends EntityLocalizationMetaData {

    protected LocalizationMetaDataEn() {
        super(Locale.ENGLISH, RelativeDirectoryLocation.of("en-EN"), ConfigSyntax.JSON);
    }
}
