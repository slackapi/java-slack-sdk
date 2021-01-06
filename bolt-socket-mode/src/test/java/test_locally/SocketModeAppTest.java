package test_locally;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.socket_mode.SocketModeClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.socket_mode.MockWebApiServer;
import util.socket_mode.MockWebSocketServer;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

public class SocketModeAppTest {

    static final String VALID_BOT_TOKEN = "xoxb-valid-123123123123123123123123123123123123";
    static final String VALID_APP_TOKEN = "xapp-valid-123123123123123123123123123123123123";

    MockWebSocketServer wsServer = new MockWebSocketServer();
    MockWebApiServer webApiServer = new MockWebApiServer();
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
    public void commands() throws Exception {
        App app = new App(AppConfig.builder()
                .slack(slack)
                .singleTeamBotToken(VALID_BOT_TOKEN)
                .build());
        AtomicBoolean called = new AtomicBoolean(false);
        app.command("/hi-socket-mode", (req, ctx) -> {
            called.set(true);
            return ctx.ack("Hello!");
        });
        SocketModeApp socketModeApp = new SocketModeApp(VALID_APP_TOKEN, app);
        socketModeApp.startAsync();
        try {
            Thread.sleep(1000L);
            assertTrue(called.get());
        } finally {
            socketModeApp.stop();
        }
    }

    // -------------------------------------------------
    // JavaWebSocket implementation
    // -------------------------------------------------

    @Test
    public void commands_JavaWebSocket() throws Exception {
        App app = new App(AppConfig.builder()
                .slack(slack)
                .singleTeamBotToken(VALID_BOT_TOKEN)
                .build());
        AtomicBoolean called = new AtomicBoolean(false);
        app.command("/hi-socket-mode", (req, ctx) -> {
            called.set(true);
            return ctx.ack("Hello!");
        });
        SocketModeApp socketModeApp = new SocketModeApp(VALID_APP_TOKEN, SocketModeClient.Backend.JavaWebSocket, app);
        socketModeApp.startAsync();
        try {
            Thread.sleep(1000L);
            assertTrue(called.get());
        } finally {
            socketModeApp.stop();
        }
    }

}
