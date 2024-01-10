package ru.hzerr.configuration.database.repository;

import ru.hzerr.collections.list.HList;

public interface IEmailRepository<T> {

    HList<T> getEmails();
    T getEmail(String login);
    void addEmail(T email);
    void changeEmail(T email);
    void removeEmail(String login);
}
