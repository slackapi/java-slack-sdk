package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
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

import static com.slack.api.model.view.Views.view;
import static com.slack.api.model.view.Views.viewTitle;
import static org.junit.Assert.assertEquals;

@Slf4j
public class ViewSubmissionTest {

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

    String realPayload = "{\"type\":\"view_submission\",\"team\":{\"id\":\"T123\",\"domain\":\"test-test\"},\"user\":{\"id\":\"U123\",\"username\":\"test-test\",\"name\":\"test-test\",\"team_id\":\"T123\"},\"api_app_id\":\"A123\",\"token\":\"legacy-fixed-value\",\"trigger_id\":\"123.123.07ecad50d3e077e08742bafff5f7ab0c\",\"view\":{\"id\":\"V123\",\"team_id\":\"T123\",\"type\":\"modal\",\"blocks\":[{\"type\":\"input\",\"block_id\":\"request-block\",\"label\":{\"type\":\"plain_text\",\"text\":\"Detailed Request\",\"emoji\":true},\"optional\":false,\"element\":{\"type\":\"plain_text_input\",\"action_id\":\"request-action\",\"multiline\":true}}],\"private_metadata\":\"https:\\/\\/hooks.slack.com\\/commands\\/T123\\/977578904577\\/NfI6RdDqrYDC8Pjzo01xgLQ3\",\"callback_id\":\"request-modal\",\"state\":{\"values\":{\"request-block\":{\"request-action\":{\"type\":\"plain_text_input\",\"value\":\"test test test\"}}}},\"hash\":\"1583638467.cd05659d\",\"title\":{\"type\":\"plain_text\",\"text\":\"Request Form\",\"emoji\":false},\"clear_on_close\":false,\"notify_on_close\":false,\"close\":{\"type\":\"plain_text\",\"text\":\"Cancel\",\"emoji\":false},\"submit\":{\"type\":\"plain_text\",\"text\":\"Submit\",\"emoji\":false},\"previous_view_id\":null,\"root_view_id\":\"V123\",\"app_id\":\"A123\",\"external_id\":\"\",\"app_installed_team_id\":\"T123\",\"bot_id\":\"B123\"},\"response_urls\":[]}";

    @Test
    public void withPayload() throws Exception {
        App app = buildApp();
        app.viewSubmission("request-modal", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        ViewSubmissionRequest req = new ViewSubmissionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void withPayload_skipped() throws Exception {
        App app = buildApp();
        app.viewSubmission("request-modal-2", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        ViewSubmissionRequest req = new ViewSubmissionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(404L, response.getStatusCode().longValue());
    }

    @Test
    public void handled() throws Exception {
        App app = buildApp();
        app.viewSubmission("callback+@!", (req, ctx) -> ctx.ack());

        ViewSubmissionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        ViewSubmissionRequest req = new ViewSubmissionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void regexp() throws Exception {
        App app = buildApp();
        app.viewSubmission(Pattern.compile("^.*allback.*$"), (req, ctx) -> ctx.ack());

        ViewSubmissionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        ViewSubmissionRequest req = new ViewSubmissionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void unhandled() throws Exception {
        App app = buildApp();
        app.viewSubmission("callback+@!", (req, ctx) -> ctx.ack());

        ViewSubmissionPayload payload = buildPayload();
        payload.getView().setCallbackId("unexpected-callback+@!");

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        ViewSubmissionRequest req = new ViewSubmissionRequest(requestBody, p, new RequestHeaders(rawHeaders));
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

    ViewSubmissionPayload buildPayload() {
        ViewSubmissionPayload.Team team = new ViewSubmissionPayload.Team();
        team.setId("T123");
        ViewSubmissionPayload.User user = new ViewSubmissionPayload.User();
        team.setId("U123");
        ViewSubmissionPayload payload = ViewSubmissionPayload.builder()
                .team(team)
                .user(user)
                .view(view(r -> r.callbackId("callback+@!").title(viewTitle(t -> t.text("Title")))))
                .build();
        return payload;
    }

}
