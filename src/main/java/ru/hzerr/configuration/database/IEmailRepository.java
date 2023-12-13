package ru.hzerr.configuration.database;

public interface IEmailRepository<T> {

    T getEmail(String login);
    void addEmail(T email);
    void changeEmail(T email);
    void removeEmail(String login);
}
