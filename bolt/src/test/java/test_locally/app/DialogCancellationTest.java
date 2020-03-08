package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.DialogCancellationRequest;
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
public class DialogCancellationTest {

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

    String realPayload = "{\"type\":\"dialog_cancellation\",\"token\":\"legacy-fixed-value\",\"action_ts\":\"1583638205.385082\",\"team\":{\"id\":\"T123\",\"domain\":\"test-test\"},\"user\":{\"id\":\"U123\",\"name\":\"test-test\"},\"channel\":{\"id\":\"C123\",\"name\":\"dev\"},\"callback_id\":\"dialog-test\",\"response_url\":\"https:\\/\\/hooks.slack.com\\/app\\/T123\\/123\\/G1yvpfcWoJp0UDm7O3T2eQSE\",\"state\":\"Limo\"}";

    @Test
    public void test() throws Exception {
        App app = buildApp();
        app.dialogCancellation("dialog-test", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        DialogCancellationRequest req = new DialogCancellationRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void skipped() throws Exception {
        App app = buildApp();
        app.dialogCancellation("dialog-test-2", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        DialogCancellationRequest req = new DialogCancellationRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
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
