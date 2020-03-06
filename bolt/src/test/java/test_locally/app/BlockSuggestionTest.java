package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockSuggestionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.util.json.GsonFactory;
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
import java.util.regex.Pattern;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.junit.Assert.assertEquals;

@Slf4j
public class BlockSuggestionTest {

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

    final Gson gson = GsonFactory.createSnakeCase();
    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);

    @Test
    public void handled() throws Exception {
        App app = buildApp();
        app.blockSuggestion("action#@$+!", (req, ctx) -> {
            List<Option> options = Arrays.asList(Option.builder().text(plainText("label")).value("v").build());
            return ctx.ack(r -> r.options(options));
        });

        BlockSuggestionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockSuggestionRequest req = new BlockSuggestionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
        assertEquals("application/json", response.getContentType());
        assertEquals("{\"options\":[{\"text\":{\"type\":\"plain_text\",\"text\":\"label\"},\"value\":\"v\"}]}", response.getBody());
    }

    @Test
    public void regexp() throws Exception {
        App app = buildApp();
        app.blockSuggestion(Pattern.compile("^act.+$"), (req, ctx) -> {
            List<Option> options = Arrays.asList(Option.builder().text(plainText("label")).value("v").build());
            return ctx.ack(r -> r.options(options));
        });

        BlockSuggestionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockSuggestionRequest req = new BlockSuggestionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
        assertEquals("application/json", response.getContentType());
        assertEquals("{\"options\":[{\"text\":{\"type\":\"plain_text\",\"text\":\"label\"},\"value\":\"v\"}]}", response.getBody());
    }

    @Test
    public void unhandled() throws Exception {
        App app = buildApp();
        app.blockSuggestion("action#@$+!", (req, ctx) -> {
            List<Option> options = Arrays.asList(Option.builder().text(plainText("label")).value("v").build());
            return ctx.ack(r -> r.options(options));
        });

        BlockSuggestionPayload payload = buildPayload();
        payload.setActionId("unexpected-action#@$+!");

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockSuggestionRequest req = new BlockSuggestionRequest(requestBody, p, new RequestHeaders(rawHeaders));
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

    BlockSuggestionPayload buildPayload() {
        BlockSuggestionPayload.Team team = new BlockSuggestionPayload.Team();
        team.setId("T123");
        BlockSuggestionPayload.User user = new BlockSuggestionPayload.User();
        team.setId("U123");
        BlockSuggestionPayload payload = BlockSuggestionPayload.builder()
                .team(team)
                .user(user)
                .actionId("action#@$+!")
                .value("Slack")
                .build();
        return payload;
    }

}
