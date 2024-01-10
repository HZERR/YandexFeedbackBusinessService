package ru.hzerr.configuration.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.EntityLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

@Registered
public class LocalizationMetaDataRu extends EntityLocalizationMetaData {

    protected LocalizationMetaDataRu() {
        super(LOCALE_RU, RelativeDirectoryLocation.of("ru-RU"), ConfigSyntax.JSON);
    }
}
