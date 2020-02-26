package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.interactive_components.payload.MessageActionPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.MessageActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test_locally.util.AuthTestMockServer;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class MiddlewareTest {

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
        AtomicBoolean called = new AtomicBoolean(false);
        App app = buildApp();
        app.use((req, resp, chain) -> {
            req.getContext().logger.debug("request body: {}", req.getRequestBodyAsString());
            called.set(true);
            return chain.next(req);
        });
        app.messageAction("callback", (req, ctx) -> ctx.ack());

        MessageActionPayload payload = buildPayload();
        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        MessageActionRequest req = new MessageActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(called.get());
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

    MessageActionPayload buildPayload() {
        MessageActionPayload.Channel channel = new MessageActionPayload.Channel();
        channel.setId("C123");
        channel.setName("general");
        MessageActionPayload.Team team = new MessageActionPayload.Team();
        team.setId("T123");
        MessageActionPayload.User user = new MessageActionPayload.User();
        team.setId("U123");
        MessageActionPayload payload = MessageActionPayload.builder()
                .callbackId("callback")
                .triggerId("xxxx")
                .channel(channel)
                .team(team)
                .user(user)
                .build();
        return payload;
    }

}
