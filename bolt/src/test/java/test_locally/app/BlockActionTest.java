package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

@Slf4j
public class BlockActionTest {

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

    String realPayload = "{\"type\":\"block_actions\",\"team\":{\"id\":\"T123\",\"domain\":\"test-test\"},\"user\":{\"id\":\"U123\",\"username\":\"test-test\",\"name\":\"test-test\",\"team_id\":\"T123\"},\"api_app_id\":\"A123\",\"token\":\"legacy-fixed-value\",\"container\":{\"type\":\"view\",\"view_id\":\"V123\"},\"trigger_id\":\"987196394372.3485157640.efe5ca926abc9dadb0371534fff4326e\",\"view\":{\"id\":\"V123\",\"team_id\":\"T123\",\"type\":\"home\",\"blocks\":[{\"type\":\"section\",\"block_id\":\"hltsY\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"*Here's what you can do with Project Tracker:*\",\"verbatim\":false}},{\"type\":\"actions\",\"block_id\":\"home_actions\",\"elements\":[{\"type\":\"button\",\"action_id\":\"home_button_1\",\"text\":{\"type\":\"plain_text\",\"text\":\"Create New Task\",\"emoji\":true},\"style\":\"primary\",\"value\":\"create_task\"},{\"type\":\"button\",\"action_id\":\"home_button_2\",\"text\":{\"type\":\"plain_text\",\"text\":\"Create New Project\",\"emoji\":true},\"value\":\"create_project\"},{\"type\":\"button\",\"action_id\":\"home_button_3\",\"text\":{\"type\":\"plain_text\",\"text\":\"Help\",\"emoji\":true},\"value\":\"help\"}]},{\"type\":\"context\",\"block_id\":\"BNV\",\"elements\":[{\"fallback\":\"20x20px image\",\"image_url\":\"https:\\/\\/docs.slack.dev\\/img\\/blocks\\/bkb_template_images\\/placeholder.png\",\"image_width\":20,\"image_height\":20,\"image_bytes\":96,\"type\":\"image\",\"alt_text\":\"placeholder\"}]},{\"type\":\"section\",\"block_id\":\"z61\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"*Your Configurations*\",\"verbatim\":false}},{\"type\":\"divider\",\"block_id\":\"F6ZOo\"},{\"type\":\"section\",\"block_id\":\"NmR\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"*#public-relations*\\n<fakelink.toUrl.com|PR Strategy 2019> posts new tasks, comments, and project updates to <fakelink.toChannel.com|#public-relations>\",\"verbatim\":false},\"accessory\":{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\"Edit\",\"emoji\":true},\"value\":\"public-relations\",\"action_id\":\"czws\"}},{\"type\":\"divider\",\"block_id\":\"QAaXg\"},{\"type\":\"section\",\"block_id\":\"LqY\",\"text\":{\"type\":\"mrkdwn\",\"text\":\"*#team-updates*\\n<fakelink.toUrl.com|Q4 Team Projects> posts project updates to <fakelink.toChannel.com|#team-updates>\",\"verbatim\":false},\"accessory\":{\"type\":\"button\",\"text\":{\"type\":\"plain_text\",\"text\":\"Edit\",\"emoji\":true},\"value\":\"public-relations\",\"action_id\":\"BOp7O\"}},{\"type\":\"divider\",\"block_id\":\"NGZ\"},{\"type\":\"actions\",\"block_id\":\"QFv\",\"elements\":[{\"type\":\"button\",\"action_id\":\"home_button_4\",\"text\":{\"type\":\"plain_text\",\"text\":\"New Configuration\",\"emoji\":true},\"value\":\"new_configuration\"}]}],\"private_metadata\":\"\",\"callback_id\":\"\",\"state\":{\"values\":{}},\"hash\":\"1583634748.7562ffd9\",\"title\":{\"type\":\"plain_text\",\"text\":\"View Title\",\"emoji\":true},\"clear_on_close\":false,\"notify_on_close\":false,\"close\":null,\"submit\":null,\"previous_view_id\":null,\"root_view_id\":\"V123\",\"app_id\":\"A123\",\"external_id\":\"\",\"app_installed_team_id\":\"T123\",\"bot_id\":\"B123\"},\"actions\":[{\"action_id\":\"home_button_1\",\"block_id\":\"home_actions\",\"text\":{\"type\":\"plain_text\",\"text\":\"Create New Task\",\"emoji\":true},\"value\":\"create_task\",\"style\":\"primary\",\"type\":\"button\",\"action_ts\":\"1583635577.758298\"}]}";

    @Test
    public void test_with_real_payload() throws Exception {
        App app = buildApp();
        app.blockAction("home_button_1", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockActionRequest req = new BlockActionRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void handled() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.blockAction("action?foo$^", (req, ctx) -> ctx.ack());

        BlockActionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockActionRequest req = new BlockActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void executorService() throws Exception {
        App app = buildApp();
        AtomicBoolean called = new AtomicBoolean(false);
        app.blockAction("action?foo$^", (req, ctx) -> {
            app.executorService().execute(() -> {
                try {
                    Thread.sleep(500L);
                    called.set(true);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            return ctx.ack();
        });

        BlockActionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockActionRequest req = new BlockActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
        assertFalse(called.get());
        Thread.sleep(1000L);
        assertTrue(called.get());
    }

    @Test
    public void regexp() throws Exception {
        App app = buildApp();
        app.blockAction(Pattern.compile("^ac.+$"), (req, ctx) -> ctx.ack());

        BlockActionPayload payload = buildPayload();

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockActionRequest req = new BlockActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void unhandled() throws Exception {
        App app = buildApp();
        app.blockAction("action?foo$^", (req, ctx) -> ctx.ack());

        BlockActionPayload payload = buildPayload();
        payload.getActions().get(0).setActionId("unexpected-action?foo$^");

        String p = gson.toJson(payload);
        String requestBody = "payload=" + URLEncoder.encode(p, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        BlockActionRequest req = new BlockActionRequest(requestBody, p, new RequestHeaders(rawHeaders));
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

    BlockActionPayload buildPayload() {
        BlockActionPayload.Team team = new BlockActionPayload.Team();
        team.setId("T123");
        BlockActionPayload.User user = new BlockActionPayload.User();
        team.setId("U123");
        BlockActionPayload.Action action = new BlockActionPayload.Action();
        action.setBlockId("block");
        action.setActionId("action?foo$^");
        action.setValue("value");
        BlockActionPayload payload = BlockActionPayload.builder()
                .team(team)
                .user(user)
                .actions(Arrays.asList(action))
                .build();
        return payload;
    }

}
