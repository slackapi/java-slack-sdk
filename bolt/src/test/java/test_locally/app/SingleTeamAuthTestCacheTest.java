package test_locally.app;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.GlobalShortcutRequest;
import com.slack.api.bolt.response.Response;
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
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

@Slf4j
public class SingleTeamAuthTestCacheTest {

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
    public void permanentCacheEnabled() throws Exception {
        App app = new App(AppConfig.builder()
                .authTestCacheEnabled(true)
                .authTestCacheExpirationMillis(-1L)
                .signingSecret(secret)
                .singleTeamBotToken("xoxb-1234567890-123456789012-12345678901234567890-permanent")
                .slack(slack)
                .build());
        app.globalShortcut("test-global-shortcut", (req, ctx) -> ctx.ack());

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

        private final int port;
        private final Server server;

        public CountdownAuthTestServer() {
            this(PortProvider.getPort(CountdownAuthTestServer.class.getName()));
        }

        public CountdownAuthTestServer(int port) {
            this.port = port;
            server = new Server(this.port);
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            handler.addServletWithMapping(CountdownAuthTestMockEndpoint.class, "/*");
        }

        public String getMethodsEndpointPrefix() {
            return "http://localhost:" + port + "/api/";
        }

        public void start() throws Exception {
            server.start();
        }

        public void stop() throws Exception {
            server.stop();
        }
    }

}
