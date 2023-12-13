package ru.hzerr.generator;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomAlphanumericLoginGenerator implements ILoginGenerator {

    private final int count;

    public RandomAlphanumericLoginGenerator(int count) {
        this.count = count;
    }

    @Override
    public String generate() {
        return RandomStringUtils.randomAlphanumeric(count);
    }
}
