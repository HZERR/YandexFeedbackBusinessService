package ru.hzerr.service.mail.exception;

public class MailRuCreationEntityLoadException extends MailRuCreationException {

    public MailRuCreationEntityLoadException(String message, Exception e) {
        super(message, e);
    }
}
