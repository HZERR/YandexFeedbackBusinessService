package ru.hzerr.configuration.database.exception;

public final class FileSystemDatabaseUpdateProcessingException extends FileSystemDatabaseProcessingException {
    public FileSystemDatabaseUpdateProcessingException(String message) {
        super(message);
    }

    public FileSystemDatabaseUpdateProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemDatabaseUpdateProcessingException(Throwable cause) {
        super(cause);
    }
}
