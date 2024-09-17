package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.builtin.Assistant;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;
import util.MockSlackApiServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Slf4j
public class EventAssistantTest {

    MockSlackApiServer server = new MockSlackApiServer();
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

    @Test
    public void test() throws Exception {
        App app = buildApp();
        AtomicBoolean threadStartedReceived = new AtomicBoolean(false);
        AtomicBoolean threadContextChangedReceived = new AtomicBoolean(false);
        AtomicBoolean userMessageReceived = new AtomicBoolean(false);
        Assistant assistant = new Assistant(app.executorService());

        assistant.threadStarted((req, ctx) -> {
            threadStartedReceived.set(true);
        });
        assistant.threadContextChanged((req, ctx) -> {
            threadContextChangedReceived.set(true);
        });
        assistant.userMessage((req, ctx) -> {
            userMessageReceived.set(true);
        });

        app.assistant(assistant);

        String threadStarted = buildPayload("{\n" +
                "  \"type\": \"assistant_thread_started\",\n" +
                "  \"assistant_thread\": {\n" +
                "    \"user_id\": \"W222\",\n" +
                "    \"context\": {\"channel_id\": \"C222\", \"team_id\": \"T111\", \"enterprise_id\": \"E111\"},\n" +
                "    \"channel_id\": \"D111\",\n" +
                "    \"thread_ts\": \"1726133698.626339\"\n" +
                "  },\n" +
                "  \"event_ts\": \"1726133698.665188\"\n" +
                "}");
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(threadStarted, rawHeaders, timestamp);
        Response response = app.run(new EventRequest(threadStarted, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());

        String threadContextChanged = buildPayload("{\n" +
                "  \"type\": \"assistant_thread_context_changed\",\n" +
                "  \"assistant_thread\": {\n" +
                "    \"user_id\": \"W222\",\n" +
                "    \"context\": {\"channel_id\": \"C333\", \"team_id\": \"T111\", \"enterprise_id\": \"E111\"},\n" +
                "    \"channel_id\": \"D111\",\n" +
                "    \"thread_ts\": \"1726133698.626339\"\n" +
                "  },\n" +
                "  \"event_ts\": \"1726133698.665188\"\n" +
                "}");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(threadContextChanged, rawHeaders, timestamp);
        response = app.run(new EventRequest(threadContextChanged, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());

        String userMessage = buildPayload("{\n" +
                "  \"user\": \"W222\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"ts\": \"1726133700.887259\",\n" +
                "  \"text\": \"When Slack was released?\",\n" +
                "  \"team\": \"T111\",\n" +
                "  \"user_team\": \"T111\",\n" +
                "  \"source_team\": \"T222\",\n" +
                "  \"user_profile\": {},\n" +
                "  \"thread_ts\": \"1726133698.626339\",\n" +
                "  \"parent_user_id\": \"W222\",\n" +
                "  \"channel\": \"D111\",\n" +
                "  \"event_ts\": \"1726133700.887259\",\n" +
                "  \"channel_type\": \"im\"\n" +
                "}");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(userMessage, rawHeaders, timestamp);
        response = app.run(new EventRequest(userMessage, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());

        String assistantMessageChanged = buildPayload("{\n" +
                "  \"type\": \"message\",\n" +
                "  \"subtype\": \"message_changed\",\n" +
                "  \"message\": {\n" +
                "    \"text\": \"New chat\",\n" +
                "    \"subtype\": \"assistant_app_thread\",\n" +
                "    \"user\": \"U222\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"edited\": {},\n" +
                "    \"thread_ts\": \"1726133698.626339\",\n" +
                "    \"reply_count\": 2,\n" +
                "    \"reply_users_count\": 2,\n" +
                "    \"latest_reply\": \"1726133700.887259\",\n" +
                "    \"reply_users\": [\"U222\", \"W111\"],\n" +
                "    \"is_locked\": false,\n" +
                "    \"assistant_app_thread\": {\"title\": \"When Slack was released?\", \"title_blocks\": [], \"artifacts\": []},\n" +
                "    \"ts\": \"1726133698.626339\"\n" +
                "  },\n" +
                "  \"previous_message\": {\n" +
                "    \"text\": \"New chat\",\n" +
                "    \"subtype\": \"assistant_app_thread\",\n" +
                "    \"user\": \"U222\",\n" +
                "    \"type\": \"message\",\n" +
                "    \"edited\": {},\n" +
                "    \"thread_ts\": \"1726133698.626339\",\n" +
                "    \"reply_count\": 2,\n" +
                "    \"reply_users_count\": 2,\n" +
                "    \"latest_reply\": \"1726133700.887259\",\n" +
                "    \"reply_users\": [\"U222\", \"W111\"],\n" +
                "    \"is_locked\": false\n" +
                "  },\n" +
                "  \"channel\": \"D111\",\n" +
                "  \"hidden\": true,\n" +
                "  \"ts\": \"1726133701.028300\",\n" +
                "  \"event_ts\": \"1726133701.028300\",\n" +
                "  \"channel_type\": \"im\"\n" +
                "}");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(assistantMessageChanged, rawHeaders, timestamp);
        response = app.run(new EventRequest(assistantMessageChanged, new RequestHeaders(rawHeaders)));
        assertEquals(200L, response.getStatusCode().longValue());

        String channelMessage = buildPayload("{\n" +
                "  \"user\": \"W222\",\n" +
                "  \"type\": \"message\",\n" +
                "  \"ts\": \"1726133700.887259\",\n" +
                "  \"text\": \"When Slack was released?\",\n" +
                "  \"team\": \"T111\",\n" +
                "  \"user_team\": \"T111\",\n" +
                "  \"source_team\": \"T222\",\n" +
                "  \"user_profile\": {},\n" +
                "  \"thread_ts\": \"1726133698.626339\",\n" +
                "  \"parent_user_id\": \"W222\",\n" +
                "  \"channel\": \"D111\",\n" +
                "  \"event_ts\": \"1726133700.887259\",\n" +
                "  \"channel_type\": \"channel\"\n" +
                "}");
        rawHeaders = new HashMap<>();
        timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(channelMessage, rawHeaders, timestamp);
        response = app.run(new EventRequest(channelMessage, new RequestHeaders(rawHeaders)));
        assertEquals(404L, response.getStatusCode().longValue());

        int count = 0;
        while (count < 100 && (!threadStartedReceived.get() || !threadContextChangedReceived.get() || !userMessageReceived.get())) {
            Thread.sleep(10L);
            count++;
        }
        assertTrue(threadStartedReceived.get());
        assertTrue(threadContextChangedReceived.get());
        assertTrue(userMessageReceived.get());
    }

    App buildApp() {
        return new App(AppConfig.builder()
                .signingSecret(secret)
                .singleTeamBotToken(AuthTestMockServer.ValidToken)
                .slack(slack)
                .build());
    }

    String buildPayload(String event) {
        return "{\n" +
                "    \"token\": \"verification_token\",\n" +
                "    \"team_id\": \"T111\",\n" +
                "    \"enterprise_id\": \"E111\",\n" +
                "    \"api_app_id\": \"A111\",\n" +
                "    \"event\": " + event + ",\n" +
                "    \"type\": \"event_callback\",\n" +
                "    \"event_id\": \"Ev111\",\n" +
                "    \"event_time\": 1599616881,\n" +
                "    \"authorizations\": [\n" +
                "        {\n" +
                "            \"enterprise_id\": \"E111\",\n" +
                "            \"team_id\": \"T111\",\n" +
                "            \"user_id\": \"W111\",\n" +
                "            \"is_bot\": true,\n" +
                "            \"is_enterprise_install\": false\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }

    void setRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Collections.singletonList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Collections.singletonList(generator.generate(timestamp, requestBody)));
    }
}
