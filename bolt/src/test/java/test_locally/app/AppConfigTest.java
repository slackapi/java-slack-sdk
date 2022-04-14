package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.request.builtin.SSLCheckRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.model.event.ReactionAddedEvent;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class AppConfigTest {

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

    String blockActionsPayload = "{\n" +
            "  \"type\": \"block_actions\",\n" +
            "  \"user\": {\n" +
            "    \"id\": \"W111\",\n" +
            "    \"username\": \"primary-owner\",\n" +
            "    \"name\": \"primary-owner\",\n" +
            "    \"team_id\": \"T111\"\n" +
            "  },\n" +
            "  \"api_app_id\": \"A111\",\n" +
            "  \"token\": \"verification_token\",\n" +
            "  \"container\": {\n" +
            "    \"type\": \"message\",\n" +
            "    \"message_ts\": \"111.222\",\n" +
            "    \"channel_id\": \"C111\",\n" +
            "    \"is_ephemeral\": true\n" +
            "  },\n" +
            "  \"trigger_id\": \"111.222.valid\",\n" +
            "  \"team\": {\n" +
            "    \"id\": \"T111\",\n" +
            "    \"domain\": \"workspace-domain\",\n" +
            "    \"enterprise_id\": \"E111\",\n" +
            "    \"enterprise_name\": \"Sandbox Org\"\n" +
            "  },\n" +
            "  \"channel\": {\"id\": \"C111\", \"name\": \"test-channel\"},\n" +
            "  \"response_url\": \"https://hooks.slack.com/actions/T111/111/random-value\",\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"action_id\": \"a\",\n" +
            "      \"block_id\": \"b\",\n" +
            "      \"text\": {\"type\": \"plain_text\", \"text\": \"Button\", \"emoji\": true},\n" +
            "      \"value\": \"click_me_123\",\n" +
            "      \"type\": \"button\",\n" +
            "      \"action_ts\": \"1596530385.194939\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void disableRequestVerification() throws Exception {
        App app = new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .requestVerificationEnabled(false)
                .build());
        app.blockAction("a", (req, ctx) -> ctx.ack());

        String requestBody = "payload=" + URLEncoder.encode(blockActionsPayload, "UTF-8");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        BlockActionRequest req = new BlockActionRequest(requestBody, blockActionsPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void disableSSLCheck() throws Exception {
        String requestBody = "token=random&ssl_check=1";
        Map<String, List<String>> rawHeaders = new HashMap<>();
        SSLCheckRequest req = new SSLCheckRequest(requestBody, new RequestHeaders(rawHeaders));
        {
            App app = new App(AppConfig.builder()
                    .signingSecret(secret)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .slack(slack)
                    .build());
            Response response = app.run(req);
            assertEquals(200, response.getStatusCode().longValue());
        }
        {
            App app = new App(AppConfig.builder()
                    .signingSecret(secret)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .slack(slack)
                    .sslCheckEnabled(false) // modified
                    .build());
            Response response = app.run(req);
            assertEquals(404L, response.getStatusCode().longValue());
        }
    }

    void setRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
    }

    @Test
    public void disableIgnoringSelfEvents() throws Exception {
        String json = "{\n" +
                "  \"token\": \"verification_token\",\n" +
                "  \"team_id\": \"T111\",\n" +
                "  \"enterprise_id\": \"E111\",\n" +
                "  \"api_app_id\": \"A111\",\n" +
                "  \"event\": {\n" +
                "    \"type\": \"reaction_added\",\n" +
                "    \"user\": \"U1234567\",\n" +  // This value needs to be the same with auth.test API response
                "    \"item\": {\n" +
                "      \"type\": \"message\",\n" +
                "      \"channel\": \"C111\",\n" +
                "      \"ts\": \"1599529504.000400\"\n" +
                "    },\n" +
                "    \"reaction\": \"heart_eyes\",\n" +
                "    \"item_user\": \"W111\",\n" +
                "    \"event_ts\": \"1599616881.000800\"\n" +
                "  },\n" +
                "  \"type\": \"event_callback\",\n" +
                "  \"event_id\": \"Ev111\",\n" +
                "  \"event_time\": 1599616881\n" +
                "}";
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(json, rawHeaders, timestamp);

        {
            App app = new App(AppConfig.builder()
                    .signingSecret(secret)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .slack(slack)
                    .build());
            final AtomicBoolean called = new AtomicBoolean(false);
            app.event(ReactionAddedEvent.class, (req, ctx) -> {
                called.set(true);
                return ctx.ack();
            });
            EventRequest req = new EventRequest(json, new RequestHeaders(rawHeaders));
            Response response = app.run(req);
            assertEquals(200L, response.getStatusCode().longValue());
            assertFalse(called.get());
        }
        {
            App app = new App(AppConfig.builder()
                    .signingSecret(secret)
                    .singleTeamBotToken(AuthTestMockServer.ValidToken)
                    .slack(slack)
                    .ignoringSelfEventsEnabled(false) // modified
                    .build());
            final AtomicBoolean called = new AtomicBoolean(false);
            app.event(ReactionAddedEvent.class, (req, ctx) -> {
                called.set(true);
                return ctx.ack();
            });
            EventRequest req = new EventRequest(json, new RequestHeaders(rawHeaders));
            Response response = app.run(req);
            assertEquals(200L, response.getStatusCode().longValue());
            assertTrue(called.get());
        }
    }

    @Test
    public void unmatchedHandler() throws Exception {
        App app = new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .requestVerificationEnabled(false)
                .unmatchedRequestHandler((req) -> Response.builder().statusCode(400).build())
                .build());

        String requestBody = "payload=" + URLEncoder.encode(blockActionsPayload, "UTF-8");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        BlockActionRequest req = new BlockActionRequest(requestBody, blockActionsPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(400L, response.getStatusCode().longValue());
    }

    @Test
    public void singleTeamBotTokenClient() throws Exception {
        App app = new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());

        AuthTestResponse authTest = app.client().authTest(r -> r);
        assertThat(authTest.getError(), is(nullValue()));
    }

    @Test
    public void authorizeClient() throws Exception {
        App app = new App(AppConfig.builder()
                .signingSecret(secret)
                .clientId("111.222")
                .clientSecret("secret")
                .slack(slack)
                .build());

        AuthTestResponse authTest = app.client().authTest(r -> r);
        assertThat(authTest.getError(), is("invalid_auth_local"));

        authTest = app.client().authTest(r -> r.token(AuthTestMockServer.ValidToken));
        assertThat(authTest.getError(), is(nullValue()));
    }

}
