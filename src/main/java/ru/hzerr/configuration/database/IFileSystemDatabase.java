package ru.hzerr.configuration.database;

public interface IFileSystemDatabase<T> {

    void add(String id, T object);

    T get(String id, Class<T> type);
    T[] get(Class<T> type);

    void remove(String id);
    void update(String id, T object);
}
