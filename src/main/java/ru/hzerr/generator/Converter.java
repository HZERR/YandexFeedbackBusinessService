package ru.hzerr.generator;

public interface Converter<FROM, TO> {

    TO convert(FROM from);
}
