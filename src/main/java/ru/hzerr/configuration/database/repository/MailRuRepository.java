package ru.hzerr.configuration.database.repository;

import ru.hzerr.collections.list.HList;
import ru.hzerr.configuration.database.JsonFileSystemDatabase;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.model.MailRuAccount;

@Registered
public class MailRuRepository implements IEmailRepository<MailRuAccount> {

    private JsonFileSystemDatabase<MailRuAccount> database;

    @Include
    public MailRuRepository(JsonFileSystemDatabase<MailRuAccount> database) {
        this.database = database;
    }

    @Override
    public HList<MailRuAccount> getEmails() {
        return HList.of(database.get(MailRuAccount.class));
    }

    @Override
    public MailRuAccount getEmail(String login) {
        return database.get(login, MailRuAccount.class);
    }

    @Override
    public void addEmail(MailRuAccount email) {
        database.add(email.getLogin(), email);
    }

    @Override
    public void changeEmail(MailRuAccount email) {
        database.update(email.getLogin(), email);
    }

    @Override
    public void removeEmail(String login) {
        database.remove(login);
    }
}
