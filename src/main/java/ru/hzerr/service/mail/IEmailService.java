package ru.hzerr.service.mail;

import ru.hzerr.generator.RandomData;
import ru.hzerr.model.MailRuRecord;
import ru.hzerr.service.mail.exception.MailRuCreationException;

public interface IEmailService {

    /**
     * @throws MailRuCreationCancelledException если процесс был отменен пользователем
     * @throws MailRuCreationEntityLoadException в случае ошибки загрузки entity
     * @throws MailRuCreationInterruptedException в случае прерывания процесса создания
     * @throws MailRuUnknownFooterTextException в случае невозможности дальнейшего парсинга из-за обнаружения неизвестного текста
     */
    MailRuRecord create(RandomData data) throws MailRuCreationException;
}
