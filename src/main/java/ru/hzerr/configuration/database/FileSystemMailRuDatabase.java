package ru.hzerr.configuration.database;

import org.apache.commons.configuration2.PropertiesConfiguration;
import ru.hzerr.fx.engine.core.annotation.IncludeAs;
import ru.hzerr.model.MailRuAccount;

public class FileSystemMailRuDatabase implements IEmailDatabase<MailRuAccount> {

    private PropertiesConfiguration configuration;

    @IncludeAs("mailRuDatabaseFile")
    public FileSystemMailRuDatabase(PropertiesConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void add(MailRuAccount mailRuAccount) {
        configuration.addProperty(mailRuAccount.getLogin(), mailRuAccount);
    }

    @Override
    public MailRuAccount get(String login) {
        return configuration.get(MailRuAccount.class, login);
    }

    @Override
    public void remove(String login) {
        configuration.clearProperty(login);
    }

    @Override
    public void update(MailRuAccount mailRuAccount) {
        configuration.setProperty(mailRuAccount.getLogin(), mailRuAccount);
    }
}
