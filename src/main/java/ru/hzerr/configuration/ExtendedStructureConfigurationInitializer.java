package ru.hzerr.configuration;

import ru.hzerr.fx.engine.configuration.application.Initializer;
import ru.hzerr.fx.engine.core.InitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;

import java.io.IOException;

@Registered
public class ExtendedStructureConfigurationInitializer implements Initializer {

    private final IExtendedStructureConfiguration structureConfiguration;

    @Include
    public ExtendedStructureConfigurationInitializer(IExtendedStructureConfiguration structureConfiguration) {
        this.structureConfiguration = structureConfiguration;
    }

    @Override
    public void initialize() throws InitializationException {
        try {
            structureConfiguration.getDatabaseDirectory().create();
        } catch (IOException e) {
            throw new InitializationException("Database directory не создан", e);
        }

        try {
            structureConfiguration.getMailRuDatabaseFile().create();
        } catch (IOException e) {
            throw new InitializationException("Mail.Ru Database File не создан", e);
        }
    }
}
