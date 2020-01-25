package test_locally.api.model;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.util.json.GsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public interface Verifier {

    default Logger logger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    default <T extends SlackApiResponse> T verifyParsing(String api, Class<T> clazz) throws IOException {
        String json = Files.readAllLines(
                new File("../json-logs/samples/api/" + api + ".json").toPath())
                .stream()
                .collect(joining());
        T resp = GsonFactory.createSnakeCase().fromJson(json, clazz);
        logger().info("parsed object: {}", resp);
        assertThat(resp, is(notNullValue()));
        return resp;
    }

}
