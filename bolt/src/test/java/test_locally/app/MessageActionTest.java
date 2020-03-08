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
import util.AuthTestMockServer;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

@Slf4j
public class MessageActionTest {

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

    String realPayload = "{\"type\":\"message_action\",\"token\":\"legacy-fixed-value\",\"action_ts\":\"1583637157.207593\",\"team\":{\"id\":\"T123\",\"domain\":\"test-test\"},\"user\":{\"id\":\"U123\",\"name\":\"test-test\"},\"channel\":{\"id\":\"C123\",\"name\":\"dev\"},\"callback_id\":\"test-message-action\",\"trigger_id\":\"123.123.xxx\",\"message_ts\":\"1583636382.000300\",\"message\":{\"client_msg_id\":\"b64abe86-8607-4317-bd45-cb6cfacdbfd8\",\"type\":\"message\",\"text\":\"<@U234> test\",\"user\":\"U123\",\"ts\":\"1583636382.000300\",\"team\":\"T123\",\"blocks\":[{\"type\":\"rich_text\",\"block_id\":\"d7eJ\",\"elements\":[{\"type\":\"rich_text_section\",\"elements\":[{\"type\":\"user\",\"user_id\":\"U234\"},{\"type\":\"text\",\"text\":\" test\"}]}]}]},\"response_url\":\"https:\\/\\/hooks.slack.com\\/app\\/T123\\/123\\/yYHNzRxpHc2xHjezSVw9e4zB\"}";

    @Test
    public void withPayload() throws Exception {
        App app = buildApp();
        app.messageAction("test-message-action", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        MessageActionRequest req = new MessageActionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void handled() throws Exception {
        App app = buildApp();
        app.messageAction("callback$@+*", (req, ctx) -> ctx.ack());

        MessageActionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        MessageActionRequest req = new MessageActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void regexp() throws Exception {
        App app = buildApp();
        app.messageAction(Pattern.compile("callback.+$"), (req, ctx) -> ctx.ack());

        MessageActionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        MessageActionRequest req = new MessageActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void unhandled() throws Exception {
        App app = buildApp();
        app.messageAction("callback$@+*", (req, ctx) -> ctx.ack());

        MessageActionPayload payload = buildPayload();
        payload.setCallbackId("unexpected-callback$@+*");

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        MessageActionRequest req = new MessageActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
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

    MessageActionPayload buildPayload() {
        MessageActionPayload.Channel channel = new MessageActionPayload.Channel();
        channel.setId("C123");
        channel.setName("general");
        MessageActionPayload.Team team = new MessageActionPayload.Team();
        team.setId("T123");
        MessageActionPayload.User user = new MessageActionPayload.User();
        team.setId("U123");
        MessageActionPayload payload = MessageActionPayload.builder()
                .callbackId("callback$@+*")
                .triggerId("xxxx")
                .channel(channel)
                .team(team)
                .user(user)
                .build();
        return payload;
    }

}
