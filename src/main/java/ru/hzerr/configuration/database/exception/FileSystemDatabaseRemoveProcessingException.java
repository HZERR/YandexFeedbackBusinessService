package ru.hzerr.configuration.database.exception;

public final class FileSystemDatabaseRemoveProcessingException extends FileSystemDatabaseProcessingException {
    public FileSystemDatabaseRemoveProcessingException(String message) {
        super(message);
    }

    public FileSystemDatabaseRemoveProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemDatabaseRemoveProcessingException(Throwable cause) {
        super(cause);
    }
}
