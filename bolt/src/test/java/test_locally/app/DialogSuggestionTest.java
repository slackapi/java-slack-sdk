package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.dialogs.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.DialogSuggestionRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@Slf4j
public class DialogSuggestionTest {

    AuthTestMockServer server = new AuthTestMockServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);

    String realPayload = "{\n" +
            "  \"type\": \"dialog_suggestion\",\n" +
            "  \"token\": \"W3VDvuzi2nRLsiaDOsmJranO\",\n" +
            "  \"action_ts\": \"1528203589.238335\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T24BK35ML\",\n" +
            "    \"domain\": \"hooli-hq\"\n" +
            "  },\n" +
            "  \"user\": {\n" +
            "    \"id\": \"U900MV5U7\",\n" +
            "    \"name\": \"gbelson\"\n" +
            "  },\n" +
            "  \"channel\": {\n" +
            "    \"id\": \"C012AB3CD\",\n" +
            "    \"name\": \"triage-platform\"\n" +
            "  },\n" +
            "  \"name\": \"external_data\",\n" +
            "  \"value\": \"\",\n" +
            "  \"callback_id\": \"bugs\"\n" +
            "}";

    List<Option> options = Arrays.asList(
            Option.builder().label("label").value("value").build()
    );

    @Test
    public void test() throws Exception {
        App app = buildApp();
        app.dialogSuggestion("bugs", (req, ctx) -> ctx.ack(r -> r.options(options)));

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        DialogSuggestionRequest req = new DialogSuggestionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void skipped() throws Exception {
        App app = buildApp();
        app.dialogSuggestion("bugs-life", (req, ctx) -> ctx.ack(r -> r.options(options)));

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        DialogSuggestionRequest req = new DialogSuggestionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(404L, response.getStatusCode().longValue());
    }

    App buildApp() {
        return new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
    }

    void setRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
    }

}
