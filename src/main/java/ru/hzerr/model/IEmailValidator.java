package ru.hzerr.model;

public interface IEmailValidator {
    EmailValidationStatus validate(MailRuAccount account) throws InterruptedException;
}
