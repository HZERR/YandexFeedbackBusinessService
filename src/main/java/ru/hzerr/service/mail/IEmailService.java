package ru.hzerr.service.mail;

import ru.hzerr.generator.GenerationException;
import ru.hzerr.generator.MailRuCreationInterruptedException;
import ru.hzerr.generator.RandomData;
import ru.hzerr.model.MailRuRecord;

public interface IEmailService {

    Response<MailRuRecord> create(RandomData data) throws GenerationException, MailRuCreationInterruptedException;
}
