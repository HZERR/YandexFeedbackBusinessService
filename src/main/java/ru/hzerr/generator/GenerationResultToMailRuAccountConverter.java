package ru.hzerr.generator;

import ru.hzerr.model.CreationStatus;
import ru.hzerr.model.MailRuAccount;

public class GenerationResultToMailRuAccountConverter implements Converter<GenerationResult, MailRuAccount> {

    @Override
    public MailRuAccount convert(GenerationResult generationResult) {
        MailRuAccount account = new MailRuAccount();
        account.setBlocked(false);
        account.setCreated(false);
        account.setCreationStatus(CreationStatus.DEFAULT);
        account.setGender(generationResult.getGender());
        account.setFirstName(generationResult.getFirstName());
        account.setLastName(generationResult.getLastName());
        account.setDateOfBirth(generationResult.getDateOfBirth());

        return account;
    }
}
