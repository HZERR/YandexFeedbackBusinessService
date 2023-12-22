package ru.hzerr.configuration.database;

import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.model.MailRuAccount;

@Registered
public class MailRuRepository implements IEmailRepository<MailRuAccount> {

    private IEmailDatabase<MailRuAccount> database;

    @Include
    public MailRuRepository(IEmailDatabase<MailRuAccount> database) {
        this.database = database;
    }

    @Override
    public MailRuAccount getEmail(String login) {
        return database.get(login);
    }

    @Override
    public void addEmail(MailRuAccount email) {
        database.add(email);
    }

    @Override
    public void changeEmail(MailRuAccount email) {
        database.update(email);
    }

    @Override
    public void removeEmail(String login) {
        database.remove(login);
    }
}
