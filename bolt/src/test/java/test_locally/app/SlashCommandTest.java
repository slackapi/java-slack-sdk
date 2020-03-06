package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

@Slf4j
public class SlashCommandTest {

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

    String slashCommandPayload = "token=gIkuvaNzQIHg97ATvDxqgjtO" +
            "&team_id=T0001" +
            "&team_domain=example" +
            "&enterprise_id=E0001" +
            "&enterprise_name=Globular%20Construct%20Inc" +
            "&channel_id=C2147483705" +
            "&channel_name=test" +
            "&user_id=U2147483697" +
            "&user_name=Steve" +
            "&command=/weather-123" +
            "&text=94070" +
            "&response_url=https://hooks.slack.com/commands/1234/5678" +
            "&trigger_id=13345224609.738474920.8088930838d88f008e0";

    @Test
    public void handled() throws Exception {
        App app = buildApp();
        app.command("/weather-123", (req, ctx) -> ctx.ack());

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(slashCommandPayload, rawHeaders, timestamp);

        SlashCommandRequest req = new SlashCommandRequest(slashCommandPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void regexp() throws Exception {
        App app = buildApp();
        app.command(Pattern.compile("/weather-\\d+$"), (req, ctx) -> ctx.ack());

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(slashCommandPayload, rawHeaders, timestamp);

        SlashCommandRequest req = new SlashCommandRequest(slashCommandPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void unhandled() throws Exception {
        App app = buildApp();
        app.command("/weather", (req, ctx) -> ctx.ack());

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(slashCommandPayload, rawHeaders, timestamp);

        SlashCommandRequest req = new SlashCommandRequest(slashCommandPayload, new RequestHeaders(rawHeaders));
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
