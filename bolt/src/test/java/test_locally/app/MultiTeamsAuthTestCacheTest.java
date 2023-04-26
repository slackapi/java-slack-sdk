package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.model.Bot;
import com.slack.api.bolt.model.Installer;
import com.slack.api.bolt.model.builtin.DefaultBot;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.GlobalShortcutRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.service.InstallationService;
import com.slack.api.methods.MethodsConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.PortProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class MultiTeamsAuthTestCacheTest {

    static SlackConfig config = new SlackConfig();

    static {
        MethodsConfig methodsConfig = new MethodsConfig();
        methodsConfig.setStatsEnabled(false); // To skip TeamIdCache requests
        config.setMethodsConfig(methodsConfig);
    }

    static Slack slack = Slack.getInstance(config);
    static CountdownAuthTestServer server = new CountdownAuthTestServer();

    final String secret = "foo-bar-baz";
    final SlackSignature.Generator generator = new SlackSignature.Generator(secret);

    String realPayload = "{\"type\":\"shortcut\",\"token\":\"legacy-fixed-value\",\"action_ts\":\"123.123\",\"team\":{\"id\":\"T123\",\"domain\":\"seratch-test\"},\"user\":{\"id\":\"U123\",\"username\":\"seratch\",\"team_id\":\"T123\"},\"callback_id\":\"test-global-shortcut\",\"trigger_id\":\"123.123.123\"}\n";

    @BeforeClass
    public static void setUp() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @AfterClass
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void cacheEnabled() throws Exception {
        App app = buildApp(true, null);
        app.globalShortcut("test-global-shortcut", (req, ctx) -> {
            assertNotNull(ctx.getAuthTestResponse());
            return ctx.ack();
        });

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        GlobalShortcutRequest req = new GlobalShortcutRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        assertEquals("seratch", req.getPayload().getUser().getUsername()); // a bit different from message_actions payload

        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(300L);
        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(300L);
        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(3000L);

        response = app.run(req);
//        assertEquals(503L, response.getStatusCode().longValue());
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void permanentCacheEnabled() throws Exception {
        App app = buildApp(true, -1L);
        app.globalShortcut("test-global-shortcut", (req, ctx) -> {
            assertNotNull(ctx.getAuthTestResponse());
            return ctx.ack();
        });

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        GlobalShortcutRequest req = new GlobalShortcutRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        assertEquals("seratch", req.getPayload().getUser().getUsername()); // a bit different from message_actions payload

        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(300L);
        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(300L);
        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        Thread.sleep(3000L);

        response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void cacheDisabled() throws Exception {
        App app = buildApp(false, null);
        app.globalShortcut("test-global-shortcut", (req, ctx) -> {
            assertNotNull(ctx.getAuthTestResponse());
            return ctx.ack();
        });

        String requestBody = "payload=" + URLEncoder.encode(realPayload, "UTF-8");

        Map<String, List<String>> rawHeaders = new HashMap<>();
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        setRequestHeaders(requestBody, rawHeaders, timestamp);

        GlobalShortcutRequest req = new GlobalShortcutRequest(requestBody, realPayload, new RequestHeaders(rawHeaders));
        assertEquals("seratch", req.getPayload().getUser().getUsername()); // a bit different from message_actions payload

        Response response = app.run(req);
        assertEquals(200L, response.getStatusCode().longValue());

        response = app.run(req);
        assertEquals(503L, response.getStatusCode().longValue());
    }

    App buildApp(boolean authTestCacheEnabled, Long ttlMillis) {
        AppConfig config = AppConfig.builder()
                .authTestCacheEnabled(authTestCacheEnabled)
                .signingSecret(secret)
                .clientId("test")
                .clientSecret("test-test")
                .slack(slack)
                .build();
        if (ttlMillis != null) {
            config.setAuthTestCacheExpirationMillis(ttlMillis);
        }
        App app = new App(config);
        app.service(new InstallationService() {
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
                DefaultBot bot = new DefaultBot();
                bot.setTeamId("T111");
                bot.setBotAccessToken("B111");
                bot.setBotUserId("U111");
                bot.setInstalledAt(System.currentTimeMillis());
                bot.setBotAccessToken("xoxb-1234567890-123456789012-12345678901234567890" + authTestCacheEnabled + ttlMillis);
                return bot;
            }

            @Override
            public Installer findInstaller(String enterpriseId, String teamId, String userId) {
                return null;
            }
        });
        return app;
    }

    void setRequestHeaders(String requestBody, Map<String, List<String>> rawHeaders, String timestamp) {
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP, Arrays.asList(timestamp));
        rawHeaders.put(SlackSignature.HeaderNames.X_SLACK_SIGNATURE, Arrays.asList(generator.generate(timestamp, requestBody)));
    }

    public static class CountdownAuthTestServer {

        static String ok = "{\n" +
                "  \"ok\": true,\n" +
                "  \"url\": \"https://java-slack-sdk-test.slack.com/\",\n" +
                "  \"team\": \"java-slack-sdk-test\",\n" +
                "  \"user\": \"test_user\",\n" +
                "  \"team_id\": \"T1234567\",\n" +
                "  \"user_id\": \"U1234567\",\n" +
                "  \"bot_id\": \"B12345678\",\n" +
                "  \"enterprise_id\": \"E12345678\"\n" +
                "}";

        @WebServlet
        public static class CountdownAuthTestMockEndpoint extends HttpServlet {

            private ConcurrentMap<String, AtomicInteger> remaining = new ConcurrentHashMap<>();

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                String token = req.getHeader("Authorization").replaceFirst("Bearer ", "");
                int count = remaining.computeIfAbsent(token, (t) -> new AtomicInteger(1)).getAndDecrement();
                if (count <= 0) {
                    resp.setStatus(500);
                    return;
                }
                resp.setStatus(200);
                resp.setContentType("application/json");
                resp.getWriter().write(ok);
            }
        }

        private int port;
        private Server server;

        public CountdownAuthTestServer() {
            this(PortProvider.getPort(CountdownAuthTestServer.class.getName()));
        }

        public CountdownAuthTestServer(int port) {
            setup(port);
        }

        private void setup(int port) {
            this.port = port;
            this.server = new Server(this.port);
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            handler.addServletWithMapping(CountdownAuthTestMockEndpoint.class, "/*");
        }

        public String getMethodsEndpointPrefix() {
            return "http://127.0.0.1:" + port + "/api/";
        }

        public void start() throws Exception {
            int retryCount = 0;
            while (retryCount < 5) {
                try {
                    server.start();
                    return;
                } catch (SocketException e) {
                    // java.net.SocketException: Permission denied may arise
                    // only on the GitHub Actions environment.
                    setup(PortProvider.getPort(CountdownAuthTestMockEndpoint.class.getName()));
                    retryCount++;
                }
            }
        }

        public void stop() throws Exception {
            server.stop();
        }
    }

}
