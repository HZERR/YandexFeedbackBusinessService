package ru.hzerr.configuration.database;

public interface IEmailDatabase<T> {

    void add(T t);
    T get(String login);
    void remove(String login);
    void update(T t);
}
