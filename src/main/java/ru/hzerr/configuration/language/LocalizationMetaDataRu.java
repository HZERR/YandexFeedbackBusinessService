package ru.hzerr.configuration.language;

import com.typesafe.config.ConfigSyntax;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.language.ApplicationLocalizationMetaData;
import ru.hzerr.fx.engine.core.path.BaseLocation;

@Registered
public class LocalizationMetaDataRu extends ApplicationLocalizationMetaData {

    protected LocalizationMetaDataRu() {
        super(LOCALE_RU, new BaseLocation("ru-RU"), ConfigSyntax.JSON);
    }
}
