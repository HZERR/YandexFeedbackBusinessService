package ru.hzerr.configuration;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.file.HDirectory;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.util.SystemInfo;

import java.io.File;

@Registered
public class ExtendedStructureConfiguration implements IExtendedStructureConfiguration {


    @Override
    public BaseDirectory getDatabaseDirectory() {
        return getProgramDirectory().getSubDirectory("database");
    }

    @Override
    public BaseFile getMailRuDatabaseFile() {
        return getDatabaseDirectory().getSubFile("Mail.Ru Accounts Database.database");
    }

    @Override
    public BaseDirectory getProgramDirectory() {
        return new HDirectory(SystemInfo.getUserHome() + File.separator + "YandexFeedbackBusinessService");
    }

    @Override
    public BaseDirectory getLogDirectory() {
        return getProgramDirectory().getSubDirectory("log");
    }

    @Override
    public BaseDirectory getConfigDirectory() {
        return getProgramDirectory().getSubDirectory("config");
    }

    @Override
    public BaseFile getSoftwareConfigurationFile() {
        return getConfigDirectory().getSubFile("application.json");
    }
}
