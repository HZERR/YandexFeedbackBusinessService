package ru.hzerr.service.mail.exception;

public class MailRuUnknownFooterTextException extends MailRuCreationException {

    public MailRuUnknownFooterTextException(String message) {
        super(message);
    }

    public MailRuUnknownFooterTextException(String message, Exception cause) {
        super(message, cause);
    }
}
