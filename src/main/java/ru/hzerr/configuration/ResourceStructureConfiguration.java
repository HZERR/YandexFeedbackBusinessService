package ru.hzerr.configuration;

import org.jetbrains.annotations.Nullable;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.path.BaseLocation;

@Registered
public class ResourceStructureConfiguration implements IResourceStructureConfiguration {

    @Override
    public BaseLocation getFXMLPackage() {
        return new BaseLocation("fxml");
    }

    @Override
    public BaseLocation getThemePackage() {
        return new BaseLocation("theme");
    }

    @Nullable
    @Override
    public BaseLocation getApplicationInternationalizationPackage() {
        return new BaseLocation("language");
    }

    @Nullable
    @Override
    public BaseLocation getApplicationLoggingInternationalizationPackage() {
        return null;
    }
}
