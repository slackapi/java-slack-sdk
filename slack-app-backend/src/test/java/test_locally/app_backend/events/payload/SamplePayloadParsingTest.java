package test_locally.app_backend.events.payload;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SamplePayloadParsingTest {

    static String readWholeAsString(Path path) throws IOException {
        return Files.readAllLines(path, UTF_8).stream().collect(joining());
    }

    @Test
    public void readAll() throws Exception {
        SlackConfig testConfig = SlackTestConfig.get();
        Gson gson = GsonFactory.createSnakeCase(testConfig);
        List<Path> files = Files.list(Paths.get("../json-logs/samples/events")).collect(Collectors.toList());
        for (Path jsonFile : files) {
            String json = readWholeAsString(jsonFile);
            String className = jsonFile.getFileName().toString().replaceFirst("\\.json$", "");
            String fqdn = "com.slack.api.app_backend.events.payload." + className;
            Class<EventsApiPayload<?>> clazz = (Class<EventsApiPayload<?>>) Class.forName(fqdn);
            try {
                EventsApiPayload<?> payload = gson.fromJson(json, clazz);
                assertThat(payload, is(notNullValue()));
            } catch (JsonParseException e) {
                String message = "Check " + fqdn + " : " + e.getMessage();
                throw new RuntimeException(message, e);
            }
        }
    }


}
