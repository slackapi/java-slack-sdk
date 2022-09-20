package test_locally;

import com.agorapulse.gru.Content;
import com.agorapulse.gru.Gru;
import com.agorapulse.gru.RequestDefinitionBuilder;
import com.agorapulse.gru.micronaut.Micronaut;
import com.slack.api.app_backend.SlackSignature;
import org.junit.After;
import org.junit.Test;

import java.util.Collections;

import static com.slack.api.app_backend.SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP;
import static com.slack.api.app_backend.SlackSignature.HeaderNames.X_SLACK_SIGNATURE;

public class IdiomaticTest {

    public static final String TEST_SIGNING_SECRET = "s3cr3t";

    private static final SlackSignature.Generator SIGNATURE_GENERATOR = new SlackSignature.Generator(TEST_SIGNING_SECRET);

    private final Gru gru = Gru.create(
            Micronaut.build(this)
                    .doWithContextBuilder(builder -> builder
                            .environments("idiomatic")
                            .properties(Collections.singletonMap("slack.signing-secret", IdiomaticTest.TEST_SIGNING_SECRET))
                    )
                    .start()
    );

    @After
    public void close() {
        gru.close();
    }

    @Test
    public void testHelloCommand() throws Throwable {
        gru.verify(test -> test
                .post("/slack/events", req -> slackEventRequest(req, "command=/hello"))
                .expect(resp -> resp.json("commandHelloResponse.json"))
        );
    }

    private static void slackEventRequest(RequestDefinitionBuilder builder, String requestBody) {
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = SIGNATURE_GENERATOR.generate(timestamp, requestBody);

        builder.header(X_SLACK_REQUEST_TIMESTAMP, timestamp)
                .header(X_SLACK_SIGNATURE, signature)
                .header("Content-Type", "application/json")
                .param("query", "queryValue")
                .json(Content.inline(requestBody));
    }

}
