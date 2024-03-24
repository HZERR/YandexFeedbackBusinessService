package ru.hzerr.configuration.database.repository;

import ru.hzerr.collections.list.HList;
import ru.hzerr.configuration.database.JsonFileSystemDatabase;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.model.MailRuRecord;

@Registered
public class MailRuRepository implements IEmailRepository<MailRuRecord> {

    private JsonFileSystemDatabase<MailRuRecord> database;

    @Include
    public MailRuRepository(JsonFileSystemDatabase<MailRuRecord> database) {
        this.database = database;
    }

    @Override
    public HList<MailRuRecord> getEmails() {
        return HList.of(database.get(MailRuRecord.class));
    }

    @Override
    public MailRuRecord getEmail(String login) {
        return database.get(login, MailRuRecord.class);
    }

    @Override
    public void addEmail(MailRuRecord email) {
        database.add(email.getLogin(), email);
    }

    @Override
    public void changeEmail(MailRuRecord email) {
        database.update(email.getLogin(), email);
    }

    @Override
    public void removeEmail(String login) {
        database.remove(login);
    }
}
