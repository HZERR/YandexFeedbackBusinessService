package ru.hzerr.configuration.database;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Consumer;

public interface JsonDatabase {

    void setObjectMapper(ObjectMapper mapper);
    void modifyMapper(Consumer<ObjectMapper> action);
}
