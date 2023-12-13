package ru.hzerr.generator;

public interface IGenerator<T> {

    T generate() throws GenerationException;
}
