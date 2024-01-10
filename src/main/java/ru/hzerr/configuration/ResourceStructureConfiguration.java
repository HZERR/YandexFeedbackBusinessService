package ru.hzerr.configuration;

import org.jetbrains.annotations.Nullable;
import ru.hzerr.fx.engine.configuration.application.IResourceStructureConfiguration;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.path.ILocation;
import ru.hzerr.fx.engine.core.path.RelativeDirectoryLocation;

@Registered
public class ResourceStructureConfiguration implements IResourceStructureConfiguration {

    @Override
    public ILocation getFXMLPackage() {
        return RelativeDirectoryLocation.of("fxml");
    }

    @Override
    public ILocation getThemePackage() {
        return RelativeDirectoryLocation.of("theme");
    }

    @Nullable
    @Override
    public ILocation getApplicationInternationalizationPackage() {
        return RelativeDirectoryLocation.of("language");
    }

    @Nullable
    @Override
    public ILocation getApplicationLoggingInternationalizationPackage() {
        return null;
    }
}
