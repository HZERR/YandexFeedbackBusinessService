package ru.hzerr.generator;

public class GenerationException extends Exception {

    public GenerationException(Throwable cause) {
        super(cause);
    }

    public GenerationException(String message) {
        super(message);
    }
}
