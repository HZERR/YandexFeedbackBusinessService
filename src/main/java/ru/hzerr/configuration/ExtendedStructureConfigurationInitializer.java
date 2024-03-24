package ru.hzerr.configuration;

import ru.hzerr.fx.engine.core.ApplicationContextInitializationException;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.core.context.initializer.IExtendedAnnotationConfigApplicationContextInitializer;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.io.IOException;

public class ExtendedStructureConfigurationInitializer implements IExtendedAnnotationConfigApplicationContextInitializer {

    private final IExtendedStructureConfiguration structureConfiguration;

    private ILogProvider logProvider;

    @Include
    public ExtendedStructureConfigurationInitializer(IExtendedStructureConfiguration structureConfiguration) {
        this.structureConfiguration = structureConfiguration;
    }

    @Override
    public void onInitialize() {
        logProvider.getLogger().debug("Инциализация приложения...");
        try {
            structureConfiguration.getDatabaseDirectory().create();
            logProvider.getLogger().debug("Директория файлов баз данных инициализирована");
        } catch (IOException e) {
            throw new ApplicationContextInitializationException("Database directory не создан", e);
        }

        try {
            structureConfiguration.getMailRuDatabaseFile().create();
            logProvider.getLogger().debug("Файл базы данных аккаунтов Mail.Ru инициализирован");
        } catch (IOException e) {
            throw new ApplicationContextInitializationException("Mail.Ru Database File не создан", e);
        }
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
