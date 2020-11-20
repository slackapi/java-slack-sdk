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
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.bolt.service.builtin.FileInstallationService;
import com.slack.api.model.event.*;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.AuthTestMockServer;
import util.MockSlackApi;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

@Slf4j
public class EventInSharedChannelsTest {

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

    String appMentionPayload = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T_INSTALLED\",\n" +
            "    \"enterprise_id\": \"E_INSTALLED\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"client_msg_id\": \"ae6d418a-ff0e-433f-ba10-915cebb1bb94\",\n" +
            "        \"type\": \"app_mention\",\n" +
            "        \"text\": \"<@U123> hey\",\n" +
            "        \"user\": \"U234\",\n" +
            "        \"ts\": \"1605835902.008000\",\n" +
            "        \"team\": \"T_INSTALLED\",\n" +
            "        \"channel\": \"C111\",\n" +
            "        \"event_ts\": \"1605835902.008000\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev01FC50TXB6\",\n" +
            "    \"event_time\": 1605835902,\n" +
            "    \"authorizations\": [\n" +
            "        {\n" +
            "            \"enterprise_id\": \"E_INSTALLED\",\n" +
            "            \"team_id\": \"T_INSTALLED\",\n" +
            "            \"user_id\": \"U234\",\n" +
            "            \"is_bot\": true,\n" +
            "            \"is_enterprise_install\": false\n" +
            "        }\n" +
            "    ],\n" +
            "    \"is_ext_shared_channel\": true,\n" +
            "    \"event_context\": \"1-app_mention-T_INSTALLED-C111\"\n" +
            "}";

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

    String messagePayload = "{\n" +
            "    \"token\": \"fixed-value\",\n" +
            "    \"team_id\": \"T_SOURCE\",\n" +
            "    \"api_app_id\": \"A111\",\n" +
            "    \"event\": {\n" +
            "        \"bot_id\": \"B123\",\n" +
            "        \"type\": \"message\",\n" +
            "        \"text\": \"Hi there!\",\n" +
            "        \"user\": \"U234\",\n" +
            "        \"ts\": \"1605835904.008100\",\n" +
            "        \"team\": \"T_INSTALLED\",\n" +
            "        \"bot_profile\": {\n" +
            "            \"id\": \"B123\",\n" +
            "            \"deleted\": false,\n" +
            "            \"name\": \"events-api-oauth-app\",\n" +
            "            \"updated\": 1605835851,\n" +
            "            \"app_id\": \"A111\",\n" +
            "            \"icons\": {\n" +
            "                \"image_36\": \"https://a.slack-edge.com/80588/img/plugins/app/bot_36.png\",\n" +
            "                \"image_48\": \"https://a.slack-edge.com/80588/img/plugins/app/bot_48.png\",\n" +
            "                \"image_72\": \"https://a.slack-edge.com/80588/img/plugins/app/service_72.png\"\n" +
            "            },\n" +
            "            \"team_id\": \"T_INSTALLED\"\n" +
            "        },\n" +
            "        \"channel\": \"C111\",\n" +
            "        \"event_ts\": \"1605835904.008100\",\n" +
            "        \"channel_type\": \"channel\"\n" +
            "    },\n" +
            "    \"type\": \"event_callback\",\n" +
            "    \"event_id\": \"Ev01F66ATNKV\",\n" +
            "    \"event_time\": 1605835904,\n" +
            "    \"authorizations\": [\n" +
            "        {\n" +
            "            \"enterprise_id\": \"E_INSTALLED\",\n" +
            "            \"team_id\": \"T_INSTALLED\",\n" +
            "            \"user_id\": \"U234\",\n" +
            "            \"is_bot\": true,\n" +
            "            \"is_enterprise_install\": false\n" +
            "        }\n" +
            "    ],\n" +
            "    \"is_ext_shared_channel\": true,\n" +
            "    \"event_context\": \"1-message-T_SOURCE-C111\"\n" +
            "}";

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

    App buildApp() {
        App app = new App(AppConfig.builder()
                .signingSecret(secret)
                .clientId("111.222")
                .clientSecret("secret")
                .slack(slack)
                .build());
        app.service(new FileInstallationService(app.config()) {

            @Override
            public Bot findBot(String enterpriseId, String teamId) {
                if ((enterpriseId == null || enterpriseId.equals("E_INSTALLED"))
                        && teamId.equals("T_INSTALLED")) {
                    Bot bot = new DefaultBot();
                    bot.setBotAccessToken(MockSlackApi.ValidToken);
                    return bot;
                }
                return null;
            }
        });
        return app;
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
        payload.setEnterpriseId("E_INSTALLED");
        payload.setTeamId("T_INSTALLED");
        return payload;
    }

    EventsApiPayload<MessageBotEvent> buildUserBotMessagePayload() {
        EventsApiPayload<MessageBotEvent> payload = new MessageBotPayload();
        MessageBotEvent event = new MessageBotEvent();
        event.setBotId("B123");
        event.setText("This is a message sent by a bot user.");
        payload.setEvent(event);
        payload.setEnterpriseId("E_INSTALLED");
        payload.setTeamId("T_INSTALLED");
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
}
