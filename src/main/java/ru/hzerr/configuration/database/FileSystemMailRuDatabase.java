package ru.hzerr.configuration.database;

import org.apache.commons.configuration2.FileBasedConfiguration;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.model.MailRuRecord;

@Registered
public class FileSystemMailRuDatabase extends JsonFileSystemDatabase<MailRuRecord> {

    @IncludeAs("mailRuDatabaseFile")
    public FileSystemMailRuDatabase(FileBasedConfiguration configuration) {
        super(configuration);
    }
}
