package test_locally.app;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.events.payload.*;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.model.event.*;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

@Slf4j
public class EventTest {

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

    final Gson gson = GsonFactory.createSnakeCase();
    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);

    String appMentionPayload = "{\"token\":\"legacy-fixed-value\",\"team_id\":\"T123\",\"api_app_id\":\"A123\",\"event\":{\"client_msg_id\":\"3fd13273-5a6a-4b5c-bd6f-109fd697038c\",\"type\":\"app_mention\",\"text\":\"<@U123> test\",\"user\":\"U234\",\"ts\":\"1583636399.000700\",\"team\":\"T123\",\"blocks\":[{\"type\":\"rich_text\",\"block_id\":\"FMAzp\",\"elements\":[{\"type\":\"rich_text_section\",\"elements\":[{\"type\":\"user\",\"user_id\":\"U123\"},{\"type\":\"text\",\"text\":\" test\"}]}]}],\"channel\":\"C123\",\"event_ts\":\"1583636399.000700\"},\"type\":\"event_callback\",\"event_id\":\"EvV1KV8BM3\",\"event_time\":1583636399,\"authed_users\":[\"U123\"]}";

    @Test
    public void appMention() throws Exception {
        App app = buildApp();
        AtomicBoolean userMessageReceived = new AtomicBoolean(false);
        app.event(AppMentionEvent.class, (req, ctx) -> {
            userMessageReceived.set(req.getEvent().getUser().equals("U234"));
            return ctx.ack();
        });

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(appMentionPayload, rawHeaders, timestamp);

        EventRequest req = new EventRequest(appMentionPayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        assertTrue(userMessageReceived.get());
    }

    String messagePayload = "{\"token\":\"legacy-fixed-value\",\"team_id\":\"T123\",\"api_app_id\":\"A123\",\"event\":{\"client_msg_id\":\"3fd13273-5a6a-4b5c-bd6f-109fd697038c\",\"type\":\"message\",\"text\":\"<@U123> test\",\"user\":\"U234\",\"ts\":\"1583636399.000700\",\"team\":\"T123\",\"blocks\":[{\"type\":\"rich_text\",\"block_id\":\"FMAzp\",\"elements\":[{\"type\":\"rich_text_section\",\"elements\":[{\"type\":\"user\",\"user_id\":\"U123\"},{\"type\":\"text\",\"text\":\" test\"}]}]}],\"channel\":\"C123\",\"event_ts\":\"1583636399.000700\",\"channel_type\":\"channel\"},\"type\":\"event_callback\",\"event_id\":\"EvV1KA7U3A\",\"event_time\":1583636399,\"authed_users\":[\"U123\"]}";

    @Test
    public void message() throws Exception {
        App app = buildApp();
        AtomicBoolean userMessageReceived = new AtomicBoolean(false);
        app.event(MessageEvent.class, (req, ctx) -> {
            userMessageReceived.set(req.getEvent().getUser().equals("U234"));
            return ctx.ack();
        });
        AtomicBoolean botMessageReceived = new AtomicBoolean(false);
        app.event(MessageBotEvent.class, (req, ctx) -> {
            botMessageReceived.set(req.getEvent().getBotId().equals("B123"));
            return ctx.ack();
        });

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(messagePayload, rawHeaders, timestamp);

        EventRequest req = new EventRequest(messagePayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        assertTrue(userMessageReceived.get());
        assertFalse(botMessageReceived.get());
    }

    @Test
    public void user() throws Exception {
        App app = buildApp();
        AtomicBoolean userMessageReceived = new AtomicBoolean(false);
        app.event(MessageEvent.class, (req, ctx) -> {
            userMessageReceived.set(req.getEvent().getUser().equals("U123"));
            return ctx.ack();
        });
        AtomicBoolean botMessageReceived = new AtomicBoolean(false);
        app.event(MessageBotEvent.class, (req, ctx) -> {
            botMessageReceived.set(req.getEvent().getBotId().equals("B123"));
            return ctx.ack();
        });

        EventsApiPayload<MessageEvent> payload = buildUserMessagePayload();

        String requestBody = gson.toJson(payload);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(userMessageReceived.get());
        assertFalse(botMessageReceived.get());
    }

    @Test
    public void bot() throws Exception {
        App app = buildApp();
        AtomicBoolean userMessageReceived = new AtomicBoolean(false);
        app.event(MessageEvent.class, (req, ctx) -> {
            userMessageReceived.set(req.getEvent().getUser().equals("U123"));
            return ctx.ack();
        });
        AtomicBoolean botMessageReceived = new AtomicBoolean(false);
        app.event(MessageBotEvent.class, (req, ctx) -> {
            botMessageReceived.set(req.getEvent().getBotId().equals("B123"));
            return ctx.ack();
        });

        EventsApiPayload<MessageBotEvent> payload = buildUserBotMessagePayload();

        String requestBody = gson.toJson(payload);
        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        assertFalse(userMessageReceived.get());
        assertTrue(botMessageReceived.get());
    }

    @Test
    public void allEventsAutoAck() throws Exception {
        App app = buildApp();
        app.config().setAllEventsApiAutoAckEnabled(true);

        {
            // human message
            EventsApiPayload<MessageEvent> payload = buildUserMessagePayload();

            String requestBody = gson.toJson(payload);
            Map<String, List<String>> rawHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            setRequestHeaders(requestBody, rawHeaders, timestamp);

            EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
            Response response = app.run(req);
            // Although no listener is registered in App, this request must be acknowledged.
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            // bot
            EventsApiPayload<MessageBotEvent> payload = buildUserBotMessagePayload();
            String requestBody = gson.toJson(payload);
            Map<String, List<String>> rawHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            setRequestHeaders(requestBody, rawHeaders, timestamp);

            EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
            Response response = app.run(req);

            // Although no listener is registered in App, this request must be acknowledged.
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            // bot handled by listener
            AtomicBoolean called = new AtomicBoolean(false);
            app.event(MessageBotEvent.class, (req, ctx) -> {
                called.set(true);
                return ctx.ack();
            });

            EventsApiPayload<MessageBotEvent> payload = buildUserBotMessagePayload();
            String requestBody = gson.toJson(payload);
            Map<String, List<String>> rawHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            setRequestHeaders(requestBody, rawHeaders, timestamp);

            EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
            Response response = app.run(req);

            // Although no listener is registered in App, this request must be acknowledged.
            assertEquals(200L, response.getStatusCode().longValue());
            assertTrue(called.get());
        }
    }

    @Test
    public void subtypedMessageEventsAutoAck() throws Exception {
        App app = buildApp();
        app.config().setSubtypedMessageEventsAutoAckEnabled(true);

        {
            // human message
            EventsApiPayload<MessageEvent> payload = buildUserMessagePayload();

            String requestBody = gson.toJson(payload);
            Map<String, List<String>> rawHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            setRequestHeaders(requestBody, rawHeaders, timestamp);

            EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
            Response response = app.run(req);
            // non-subtyped one should be handled by listeners.
            assertEquals(404L, response.getStatusCode().longValue());
        }
        {
            // bot
            EventsApiPayload<MessageBotEvent> payload = buildUserBotMessagePayload();
            String requestBody = gson.toJson(payload);
            Map<String, List<String>> rawHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            setRequestHeaders(requestBody, rawHeaders, timestamp);

            EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
            Response response = app.run(req);

            // Although no listener is registered in App, this request must be acknowledged.
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            // bot handled by listener
            AtomicBoolean called = new AtomicBoolean(false);
            app.event(MessageBotEvent.class, (req, ctx) -> {
                called.set(true);
                return ctx.ack();
            });

            EventsApiPayload<MessageBotEvent> payload = buildUserBotMessagePayload();
            String requestBody = gson.toJson(payload);
            Map<String, List<String>> rawHeaders = new HashMap<>();
            String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
            setRequestHeaders(requestBody, rawHeaders, timestamp);

            EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
            Response response = app.run(req);

            // Although no listener is registered in App, this request must be acknowledged.
            assertEquals(200L, response.getStatusCode().longValue());
            assertTrue(called.get());
        }
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

    EventsApiPayload<MessageEvent> buildUserMessagePayload() {
        EventsApiPayload<MessageEvent> payload = new MessagePayload();
        MessageEvent event = new MessageEvent();
        event.setUser("U123");
        event.setText("This is a message sent by a user.");
        payload.setEvent(event);
        payload.setTeamId("T123");
        return payload;
    }

    EventsApiPayload<MessageBotEvent> buildUserBotMessagePayload() {
        EventsApiPayload<MessageBotEvent> payload = new MessageBotPayload();
        MessageBotEvent event = new MessageBotEvent();
        event.setBotId("B123");
        event.setText("This is a message sent by a bot user.");
        payload.setEvent(event);
        payload.setTeamId("T123");
        return payload;
    }

    class ErrorInstallationService implements InstallationService {

        @Override
        public boolean isHistoricalDataEnabled() {
            return false;
        }

        @Override
        public void setHistoricalDataEnabled(boolean isHistoricalDataEnabled) {
        }

        @Override
        public void saveInstallerAndBot(Installer installer) {
        }

        @Override
        public void deleteBot(Bot bot) {
        }

        @Override
        public void deleteInstaller(Installer installer) {
        }

        @Override
        public Bot findBot(String enterpriseId, String teamId) {
            throw new RuntimeException("This method should not be called");
        }

        @Override
        public Installer findInstaller(String enterpriseId, String teamId, String userId) {
            throw new RuntimeException("This method should not be called");
        }
    }

    @Test
    public void uninstallation() throws Exception {
        AppConfig config = AppConfig.builder()
                .signingSecret(secret)
                .clientId("111.222")
                .clientSecret("secret-secret")
                .build();
        App app = new App(config).asOAuthApp(true);
        app.service(new ErrorInstallationService());

        AtomicBoolean uninstalled = new AtomicBoolean(false);
        app.event(AppUninstalledEvent.class, (req, ctx) -> {
            uninstalled.set(true);
            return ctx.ack();
        });
        AtomicBoolean tokensRevoked = new AtomicBoolean(false);
        app.event(TokensRevokedEvent.class, (req, ctx) -> {
            tokensRevoked.set(true);
            return ctx.ack();
        });

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);

        // Make sure if the auth middleware doesn't work
        EventsApiPayload<MessageEvent> payload = buildUserMessagePayload();
        String requestBody = gson.toJson(payload);
        setRequestHeaders(requestBody, rawHeaders, timestamp);
        EventRequest req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
        try {
            app.run(req);
            fail();
        } catch (RuntimeException e) {
            assertEquals("This method should not be called", e.getMessage());
        }

        // app_uninstalled events
        EventsApiPayload<AppUninstalledEvent> payload1 = new AppUninstalledPayload();
        payload1.setTeamId("T111");
        payload1.setEvent(new AppUninstalledEvent());
        requestBody = gson.toJson(payload1);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
        Response response = app.run(req);

        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(uninstalled.get());

        // tokens_revoked events
        EventsApiPayload<TokensRevokedEvent> payload2 = new TokensRevokedPayload();
        payload2.setTeamId("T111");
        payload2.setEvent(new TokensRevokedEvent());
        requestBody = gson.toJson(payload2);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        req = new EventRequest(requestBody, new RequestHeaders(rawHeaders));
        response = app.run(req);

        assertEquals(200L, response.getStatusCode().longValue());
        assertTrue(tokensRevoked.get());
    }

    @Test
    public void retryHeaders() throws Exception {
        App app = buildApp();
        AtomicBoolean numReceived = new AtomicBoolean(false);
        AtomicBoolean reasonReceived = new AtomicBoolean(false);
        app.event(MessageEvent.class, (req, ctx) -> {
            numReceived.set(ctx.getRetryNum() == 1);
            reasonReceived.set(ctx.getRetryReason().equals("timeout"));
            return ctx.ack();
        });

        Map<String, List<String>> rawHeaders = new HashMap<>();
        rawHeaders.put("X-Slack-Retry-Num", Arrays.asList("1"));
        rawHeaders.put("X-Slack-Retry-Reason", Arrays.asList("timeout"));
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(messagePayload, rawHeaders, timestamp);

        EventRequest req = new EventRequest(messagePayload, new RequestHeaders(rawHeaders));
        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        assertTrue(numReceived.get());
        assertTrue(reasonReceived.get());
    }
}
