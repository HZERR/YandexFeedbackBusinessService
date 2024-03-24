package ru.hzerr.generator;

import ru.hzerr.model.CreationStatus;
import ru.hzerr.model.MailRuRecord;

public class GenerationResultToMailRuAccountConverter implements Converter<RandomData, MailRuRecord> {

    @Override
    public MailRuRecord convert(RandomData randomData) {
        MailRuRecord account = new MailRuRecord();
        account.setBlocked(false);
        account.setCreated(false);
        account.setCreationStatus(CreationStatus.DEFAULT);
        account.setGender(randomData.getGender());
        account.setFirstName(randomData.getFirstName());
        account.setLastName(randomData.getLastName());
        account.setDateOfBirth(randomData.getDateOfBirth());

        return account;
    }
}
