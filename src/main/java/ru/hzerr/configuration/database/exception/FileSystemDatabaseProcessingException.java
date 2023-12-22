package ru.hzerr.configuration.database.exception;

public sealed class FileSystemDatabaseProcessingException extends RuntimeException permits
        FileSystemDatabaseAddProcessingException,
        FileSystemDatabaseRemoveProcessingException,
        FileSystemDatabaseUpdateProcessingException,
        FileSystemDatabaseGetProcessingException {

    public FileSystemDatabaseProcessingException(String message) {
        super(message);
    }

    public FileSystemDatabaseProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemDatabaseProcessingException(Throwable cause) {
        super(cause);
    }
}
