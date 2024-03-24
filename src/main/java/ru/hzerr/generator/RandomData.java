package ru.hzerr.generator;

import com.google.gson.JsonObject;
import ru.hzerr.model.Gender;

public class RandomData {

    private final JsonObject result;
    private final String FIRST_NAME_FIELD = "FirstName";
    private final String LAST_NAME_FIELD = "LastName";
    private final String DATE_OF_BIRTH_FIELD = "DateOfBirth";
    private final String GENDER_FIELD = "Gender";
    private final String PHONE_FIELD = "Phone";

    public RandomData(JsonObject result) {
        this.result = result;
    }

    public String getFirstName() {
        if (result.has(FIRST_NAME_FIELD)) {
            return result.get(FIRST_NAME_FIELD).getAsString();
        }

        throw parameterNotFoundException(FIRST_NAME_FIELD);
    }

    public String getLastName() {
        if (result.has(LAST_NAME_FIELD)) {
            return result.get(LAST_NAME_FIELD).getAsString();
        }

        throw parameterNotFoundException(LAST_NAME_FIELD);
    }

    public String getDateOfBirth() {
        if (result.has(DATE_OF_BIRTH_FIELD)) {
            return result.get(DATE_OF_BIRTH_FIELD).getAsString();
        }

        throw parameterNotFoundException(DATE_OF_BIRTH_FIELD);
    }

    public Gender getGender() {
        if (result.has(GENDER_FIELD)) {
            return result.get(GENDER_FIELD).getAsString().equalsIgnoreCase("Женщина") ? Gender.FEMALE : Gender.MALE;
        }

        throw parameterNotFoundException(GENDER_FIELD);
    }

    public String getPhone() {
        if (result.has(PHONE_FIELD)) {
            return result.get(PHONE_FIELD).getAsString();
        }

        throw parameterNotFoundException(PHONE_FIELD);
    }

    private IllegalArgumentException parameterNotFoundException(String param) {
        return new IllegalArgumentException("The '" + param + "' parameter was not found. Check the addition of the '" + param + "' parameter in the generator. The server may have changed the name of the parameter");
    }
}
