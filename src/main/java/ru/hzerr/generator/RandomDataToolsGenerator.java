package ru.hzerr.generator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.RandomUtils;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.logging.factory.ILogProvider;
import ru.hzerr.model.Gender;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Registered
public class RandomDataToolsGenerator implements IGenerator<Object> {

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();
    private static final String HOST = "https://api.randomdatatools.ru";

    private String params;
    private final Gender gender;

    private ILogProvider logProvider;

    private RandomDataToolsGenerator() {
        gender = RandomUtils.nextInt(0, 1) == 0 ? Gender.FEMALE : Gender.MALE;
    }

    public RandomDataToolsGenerator addFirstName() {
        addParameter("FirstName");
        return this;
    }

    public RandomDataToolsGenerator addLastName() {
        addParameter("LastName");
        return this;
    }

    public RandomDataToolsGenerator addDateOfBirth() {
        addParameter("DateOfBirth");
        return this;
    }

    public RandomDataToolsGenerator addGender() {
        addParameter("Gender");
        return this;
    }

    public RandomDataToolsGenerator addPhone() {
        addParameter("Phone");
        return this;
    }

    public GenerationResult generate() throws GenerationException {
        if (params == null) {
            throw new IllegalArgumentException("params must not be null");
        }

        String request = STR."\{HOST}?gender=\{gender == Gender.FEMALE ? "woman" : "man"}&unescaped=false&params=\{params}";
        logProvider.getLogger().debug("Запрос на генерацию данных: {}", request);
        try {
            HttpResponse<String> response = HTTP_CLIENT.send(HttpRequest.newBuilder(new URI(request)).GET().build(), HttpResponse.BodyHandlers.ofString());
            logProvider.getLogger().debug("Статус ответа на генерацию данных: {}", response.statusCode());
            if (response.statusCode() >= 200 && response.statusCode() <= 299) {
                String responseAsString = response.body();
                logProvider.getLogger().debug("Ответ на генерацию данных: {}", responseAsString);
                JsonObject responseAsJsonObject = JsonParser.parseString(responseAsString).getAsJsonObject();
                switch (gender) {
                    case MALE -> responseAsJsonObject.addProperty("Gender", "Мужчина");
                    case FEMALE -> responseAsJsonObject.addProperty("Gender", "Женщина");
                }

                return new GenerationResult(responseAsJsonObject);
            } else
                throw new GenerationException("Invalid status code: " + response.statusCode());
        } catch (IOException | URISyntaxException e) {
            throw new GenerationException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GenerationException(e);
        }
    }

    public static RandomDataToolsGenerator newGenerator() {
        return new RandomDataToolsGenerator();
    }

    private void addParameter(String param) {
        if (params == null) {
            params = param;
        } else
            params = params + "," + param;
    }

    @Include
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }
}
