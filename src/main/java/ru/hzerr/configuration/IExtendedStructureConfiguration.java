package ru.hzerr.configuration;

import ru.hzerr.file.BaseDirectory;
import ru.hzerr.file.BaseFile;
import ru.hzerr.fx.engine.configuration.application.IStructureConfiguration;

public interface IExtendedStructureConfiguration extends IStructureConfiguration {

    BaseDirectory getDatabaseDirectory();
    BaseFile getMailRuDatabaseFile();
}
