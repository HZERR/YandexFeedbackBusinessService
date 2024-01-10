package ru.hzerr.configuration.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration2.FileBasedConfiguration;
import ru.hzerr.configuration.database.exception.FileSystemDatabaseAddProcessingException;
import ru.hzerr.configuration.database.exception.FileSystemDatabaseGetProcessingException;
import ru.hzerr.configuration.database.exception.FileSystemDatabaseRemoveProcessingException;
import ru.hzerr.configuration.database.exception.FileSystemDatabaseUpdateProcessingException;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class JsonFileSystemDatabase<T> implements IFileSystemDatabase<T>, JsonDatabase {

    private FileBasedConfiguration configuration;
    private ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    public JsonFileSystemDatabase(FileBasedConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void add(String id, T object) {
        if (configuration.containsKey(id)) {
            throw new FileSystemDatabaseAddProcessingException(STR."\{id} already exists!");
        }
        try {
            configuration.addProperty(id, mapper.writeValueAsString(object));
        } catch (Exception e) {
            throw new FileSystemDatabaseAddProcessingException(e);
        }
    }

    @Override
    public T get(String id, Class<T> type) {
        try {
            return mapper.readValue(configuration.getString(id), type);
        } catch (Exception e) {
            throw new FileSystemDatabaseGetProcessingException(e);
        }
    }

    @Override
    public T[] get(Class<T> type) {
        Iterator<String> keyIterator = configuration.getKeys();
        T[] values = (T[]) Array.newInstance(type, configuration.size());
        for (int i = 0; keyIterator.hasNext(); i++) {
            try {
                values[i] = mapper.readValue(configuration.getString(keyIterator.next()), type);
            } catch (JsonProcessingException e) {
                throw new FileSystemDatabaseGetProcessingException(e);
            }
        }

        return values;
    }

    @Override
    public void remove(String id) {
        try {
            configuration.clearProperty(id);
        } catch (Exception e) {
            throw new FileSystemDatabaseRemoveProcessingException(e);
        }
    }

    @Override
    public void update(String id, T object) {
        try {
            configuration.setProperty(id, mapper.writeValueAsString(object));
        } catch (Exception e) {
            throw new FileSystemDatabaseUpdateProcessingException(e);
        }
    }

    @Override
    public void setObjectMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void modifyMapper(Consumer<ObjectMapper> action) {
        action.accept(mapper);
    }
}
