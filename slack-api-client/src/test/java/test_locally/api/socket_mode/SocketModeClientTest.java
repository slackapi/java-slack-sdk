package test_locally.api.socket_mode;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.listener.WebSocketMessageListener;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.socket_mode.response.MessagePayload;
import com.slack.api.socket_mode.response.MessageResponse;
import com.slack.api.util.json.GsonFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.socket_mode.MockWebApiServer;
import util.socket_mode.MockWebSocketServer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SocketModeClientTest {

    static final Gson GSON = GsonFactory.createSnakeCase();
    static final String VALID_APP_TOKEN = "xapp-valid-123123123123123123123123123123123123";

    MockWebApiServer webApiServer = new MockWebApiServer();
    MockWebSocketServer wsServer = new MockWebSocketServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        webApiServer.start();
        wsServer.start();
        config.setMethodsEndpointUrlPrefix(webApiServer.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        webApiServer.stop();
        wsServer.stop();
    }

    // -------------------------------------------------
    // Default implementation
    // -------------------------------------------------

    @Test
    public void attributes() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN)) {
            assertNotNull(client.getAppToken());
            assertNotNull(client.getMessageQueue());
            assertNotNull(client.getGson());
            assertNotNull(client.getLogger());
            assertNotNull(client.getSlack());
            assertNotNull(client.getWssUri());
        }
    }

    @Test
    public void connect() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN)) {
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(received));
            client.addWebSocketErrorListener(error -> {});
            client.addWebSocketCloseListener((code, reason) -> {});
            client.addEventsApiEnvelopeListener(envelope -> {});
            client.addInteractiveEnvelopeListener(envelope -> {});
            client.addSlashCommandsEnvelopeListener(envelope -> {});

            client.connect();
            Thread.sleep(500L);
            assertTrue(received.get());

            client.disconnect();
            client.runCloseListenersAndAutoReconnectAsNecessary(1000, null);

            client.removeWebSocketMessageListener(client.getWebSocketMessageListeners().get(0));
            client.removeWebSocketErrorListener(client.getWebSocketErrorListeners().get(0));
            client.removeWebSocketCloseListener(client.getWebSocketCloseListeners().get(0));

            client.removeEventsApiEnvelopeListener(client.getEventsApiEnvelopeListeners().get(0));
            client.removeInteractiveEnvelopeListener(client.getInteractiveEnvelopeListeners().get(0));
            client.removeSlashCommandsEnvelopeListener(client.getSlashCommandsEnvelopeListeners().get(0));
        }
    }

    @Test
    public void maintainCurrentSession() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN)) {
            client.connect();
            client.maintainCurrentSession();
        }
    }

    @Test
    public void connectToNewEndpoint() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN)) {
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(received));
            client.connect();
            client.disconnect();
            client.connectToNewEndpoint();
            Thread.sleep(500L);
            assertTrue(received.get());
        }
    }

    @Test
    public void sendSocketModeResponse() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN)) {
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(received));
            client.connect();
            Thread.sleep(300L);
            assertTrue(received.get());
            client.sendSocketModeResponse(AckResponse.builder().envelopeId("xxx").build());
            client.sendSocketModeResponse(MessageResponse.builder()
                    .envelopeId("xxx")
                    .payload(MessagePayload.builder().text("Hi there!").build())
                    .build());
        }
    }

    @Test
    public void messageReceiver() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN)) {
            AtomicBoolean helloReceived = new AtomicBoolean(false);
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(helloReceived));
            client.addWebSocketMessageListener(envelopeListener(received));
            client.connect();
            Thread.sleep(1500L);
            assertTrue(helloReceived.get());
            assertTrue(received.get());
        }
    }

    // -------------------------------------------------
    // JavaWebSocket implementation
    // -------------------------------------------------

    @Test(expected = UnsupportedOperationException.class)
    public void proxyAuth() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(webApiServer.getMethodsEndpointPrefix());
        config.setProxyUrl("http://user:pass@localhost:9000/");
        Slack slack = Slack.getInstance(config);
        slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket);
    }

    @Test
    public void attributes_JavaWebSocket() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket)) {
            assertNotNull(client.getAppToken());
            assertNotNull(client.getMessageQueue());
            assertNotNull(client.getGson());
            assertNotNull(client.getLogger());
            assertNotNull(client.getSlack());
            assertNotNull(client.getWssUri());
        }
    }

    @Test
    public void connect_JavaWebSocket() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket)) {
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(received));
            client.addWebSocketErrorListener(error -> {});
            client.addWebSocketCloseListener((code, reason) -> {});
            client.addEventsApiEnvelopeListener(envelope -> {});
            client.addInteractiveEnvelopeListener(envelope -> {});
            client.addSlashCommandsEnvelopeListener(envelope -> {});

            client.connect();
            Thread.sleep(500L);
            assertTrue(received.get());

            client.disconnect();
            client.runCloseListenersAndAutoReconnectAsNecessary(1000, null);

            client.removeWebSocketMessageListener(client.getWebSocketMessageListeners().get(0));
            client.removeWebSocketErrorListener(client.getWebSocketErrorListeners().get(0));
            client.removeWebSocketCloseListener(client.getWebSocketCloseListeners().get(0));

            client.removeEventsApiEnvelopeListener(client.getEventsApiEnvelopeListeners().get(0));
            client.removeInteractiveEnvelopeListener(client.getInteractiveEnvelopeListeners().get(0));
            client.removeSlashCommandsEnvelopeListener(client.getSlashCommandsEnvelopeListeners().get(0));
        }
    }

    @Test
    public void connectToNewEndpoint_JavaWebSocket() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket)) {
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(received));
            client.connect();
            client.disconnect();
            client.connectToNewEndpoint();
            Thread.sleep(500L);
            assertTrue(received.get());
        }
    }

    @Test
    public void maintainCurrentSession_JavaWebSocket() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket)) {
            client.connect();
            client.maintainCurrentSession();
        }
    }

    @Test
    public void sendSocketModeResponse_JavaWebSocket() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket)) {
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(received));
            client.connect();
            Thread.sleep(300L);
            assertTrue(received.get());
            client.sendSocketModeResponse(AckResponse.builder().envelopeId("xxx").build());
            client.sendSocketModeResponse(MessageResponse.builder()
                    .envelopeId("xxx")
                    .payload(MessagePayload.builder().text("Hi there!").build())
                    .build());
        }
    }

    @Test
    public void messageReceiver_JavaWebSocket() throws Exception {
        try (SocketModeClient client = slack.socketMode(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket)) {
            AtomicBoolean helloReceived = new AtomicBoolean(false);
            AtomicBoolean received = new AtomicBoolean(false);
            client.addWebSocketMessageListener(helloListener(helloReceived));
            client.addWebSocketMessageListener(envelopeListener(received));
            client.connect();
            Thread.sleep(1500L);
            assertTrue(helloReceived.get());
            assertTrue(received.get());
        }
    }

    // -------------------------------------------------

    private static Optional<String> getEnvelopeType(String message) {
        JsonElement msg = GSON.fromJson(message, JsonElement.class);
        if (msg != null && msg.isJsonObject()) {
            JsonElement typeElem = msg.getAsJsonObject().get("type");
            if (typeElem != null && typeElem.isJsonPrimitive()) {
                return Optional.of(typeElem.getAsString());
            }
        }
        return Optional.empty();
    }

    private static WebSocketMessageListener helloListener(AtomicBoolean received) {
        return message -> {
            Optional<String> type = getEnvelopeType(message);
            if (type.isPresent()) {
                if (type.get().equals("hello")) {
                    received.set(true);
                }
            }
        };
    }

    private static List<String> MESSAGE_TYPES = Arrays.asList(
            "events",
            "interactive",
            "slash_commands"
    );

    private static WebSocketMessageListener envelopeListener(AtomicBoolean received) {
        return message -> {
            Optional<String> type = getEnvelopeType(message);
            if (type.isPresent()) {
                if (MESSAGE_TYPES.contains(type.get())) {
                    received.set(true);
                }
            }
        };
    }
}
