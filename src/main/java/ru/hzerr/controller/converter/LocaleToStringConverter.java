package ru.hzerr.controller.converter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javafx.util.StringConverter;

import java.util.Locale;

public class LocaleToStringConverter extends StringConverter<Locale> {

    private final BiMap<Locale, String> localeToName = HashBiMap.create(2);

    public LocaleToStringConverter() {
        localeToName.put(Locale.ENGLISH, "English");
        localeToName.put(Locale.of("ru", "ru"), "Russian");
    }

    @Override
    public String toString(Locale locale) {
        return localeToName.get(locale);
    }

    @Override
    public Locale fromString(String localeAsString) {
        return localeToName.inverse().get(localeAsString);
    }
}
