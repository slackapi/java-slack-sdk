package test_locally.api.model;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.common.json.GsonFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public interface Verifier {

    default void verifyParsing(String api, Class<? extends SlackApiResponse> clazz) throws IOException {
        String json = Files.readAllLines(
                new File("../json-logs/samples/api/" + api + ".json").toPath())
                .stream()
                .collect(joining());
        Object resp = GsonFactory.createSnakeCase().fromJson(json, clazz);
        assertThat(resp, is(notNullValue()));
    }

}
