package ru.hzerr.model;

public interface IEmailValidator {
    EmailValidationStatus validate(MailRuRecord account) throws InterruptedException;
}
