package ru.hzerr.service.mail.exception;

public class MailRuCreationException extends Exception {

    public MailRuCreationException(Exception cause) {
        super(cause);
    }

    public MailRuCreationException(String message) {
        super(message);
    }

    public MailRuCreationException(String message, Exception cause) {
        super(message, cause);
    }
}
