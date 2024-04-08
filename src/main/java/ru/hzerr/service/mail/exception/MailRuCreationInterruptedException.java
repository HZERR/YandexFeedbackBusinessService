package ru.hzerr.service.mail.exception;

public class MailRuCreationInterruptedException extends MailRuCreationException {
    public MailRuCreationInterruptedException(String message, Exception cause) {
        super(message, cause);
    }
}
