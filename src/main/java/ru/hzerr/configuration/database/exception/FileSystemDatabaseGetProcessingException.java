package ru.hzerr.configuration.database.exception;

public final class FileSystemDatabaseGetProcessingException extends FileSystemDatabaseProcessingException {
    public FileSystemDatabaseGetProcessingException(String message) {
        super(message);
    }

    public FileSystemDatabaseGetProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemDatabaseGetProcessingException(Throwable cause) {
        super(cause);
    }
}
