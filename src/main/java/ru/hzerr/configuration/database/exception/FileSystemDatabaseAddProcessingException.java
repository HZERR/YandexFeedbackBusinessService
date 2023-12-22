package ru.hzerr.configuration.database.exception;

public final class FileSystemDatabaseAddProcessingException extends FileSystemDatabaseProcessingException {
    public FileSystemDatabaseAddProcessingException(String message) {
        super(message);
    }

    public FileSystemDatabaseAddProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemDatabaseAddProcessingException(Throwable cause) {
        super(cause);
    }
}
