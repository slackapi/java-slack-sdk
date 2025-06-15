package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.response.SchemasResponse;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.rtm.RTMClient;
import com.slack.api.scim.SCIMApiException;
import com.slack.api.scim.response.ServiceProviderConfigsGetResponse;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.status.v2.model.CurrentStatus;
import com.slack.api.util.http.SlackHttpClient;
import config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import util.PortProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.fail;

@Slf4j
public class ProxyTest {

    static SlackConfig config = new SlackConfig();
    static Server server = new Server();
    static ServerConnector connector = new ServerConnector(server);

    static String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    static String rtmBotToken = System.getenv(Constants.SLACK_SDK_TEST_CLASSIC_APP_BOT_TOKEN);
    static String scimToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String auditLogsToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String appLevelToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);

    AtomicInteger callCount = new AtomicInteger(0);

    @Before
    public void setUp() throws Exception {
        // https://github.com/eclipse/jetty.project/blob/jetty-9.2.30.v20200428/examples/embedded/src/main/java/org/eclipse/jetty/embedded/ProxyServer.java
        int port = PortProvider.getPort(ProxyTest.class.getName());
        connector.setPort(port);
        server.addConnector(connector);
        ConnectHandler proxy = new ConnectHandler() {
            @Override
            public void handle(String target, Request br, HttpServletRequest request, HttpServletResponse res)
                    throws ServletException, IOException {
                log.info("Proxy server handles a new connection (target: {})", target);
                callCount.incrementAndGet();
                super.handle(target, br, request, res);
            }
        };
        server.setHandler(proxy);
        ServletContextHandler context = new ServletContextHandler(proxy, "/", ServletContextHandler.SESSIONS);
        ServletHolder proxyServlet = new ServletHolder(ProxyServlet.class);
        context.addServlet(proxyServlet, "/*");
        server.start();

        config.setProxyUrl("http://127.0.0.1:" + port);
    }

    @After
    public void tearDown() throws Exception {
        server.removeConnector(connector);
        server.stop();
    }

    @Test
    public void methods() throws Exception {
        Slack slack = Slack.getInstance(config);
        AuthTestResponse apiResponse = slack.methods().authTest(r -> r.token(botToken));
        assertThat(apiResponse.getError(), is(nullValue()));
        assertThat(callCount.get(), is(1));
    }

    @Test
    public void methods_system_properties() throws Exception {
        String originalHttpProxyHost = System.getProperty("http.proxyHost");
        String originalHttpProxyPort = System.getProperty("http.proxyPort");

        String proxyUrl = config.getProxyUrl();
        // verify if it works with the standard system properties
        String[] elements = proxyUrl.replaceFirst("http://", "").split(":");
        config.setProxyUrl(null);
        System.setProperty("http.proxyHost", elements[0]);
        System.setProperty("http.proxyPort", elements[1]);

        try {
            Slack slack = Slack.getInstance(new SlackConfig()); // with default config
            AuthTestResponse apiResponse = slack.methods().authTest(r -> r.token(botToken));
            assertThat(apiResponse.getError(), is(nullValue()));
            assertThat(callCount.get(), is(1));

            // verify if an invalid host given by system properties is reflected
            System.setProperty("http.proxyHost", "invalid-host");
            slack = Slack.getInstance(new SlackConfig()); // with default config again
            try {
                slack.methods().authTest(r -> r.token(botToken));
                fail("A connection failure is expected here");
            } catch (IOException e) {
            }

            // verify if the setProxyUrl is prioritized over the system properties
            config.setProxyUrl(proxyUrl);
            slack = Slack.getInstance(config);
            apiResponse = slack.methods().authTest(r -> r.token(botToken));
            assertThat(apiResponse.getError(), is(nullValue()));
            assertThat(callCount.get(), is(2));

        } finally {
            if (originalHttpProxyHost != null) {
                System.setProperty("http.proxyHost", originalHttpProxyHost);
            } else {
                System.clearProperty("http.proxyHost");
            }
            if (originalHttpProxyPort != null) {
                System.setProperty("http.proxyPort", originalHttpProxyPort);
            } else {
                System.clearProperty("http.proxyPort");
            }
        }
    }

    @Ignore
    @Test
    public void rtm() throws Exception {
        SlackHttpClient httpClient = new SlackHttpClient();
        Slack slack = Slack.getInstance(config, httpClient);
        final AtomicBoolean received = new AtomicBoolean(false);
        try (RTMClient rtm = slack.rtmConnect(rtmBotToken)) { // slack-msgs.com
            rtm.addMessageHandler((msg) -> {
                log.info("Got a message - {}", msg);
                received.set(true);
            });
            rtm.addErrorHandler((e) -> {
                log.error("Got an error - {}", e.getMessage(), e);
            });
            rtm.connect();
            Thread.sleep(1000L);
            rtm.sendMessage("foo");
        }
        assertThat(received.get(), is(true));
        assertThat(callCount.get(), is(1));
    }

    @Test
    public void audit() throws Exception {
        if (auditLogsToken != null) {
            Slack slack = Slack.getInstance(config);
            SchemasResponse response = slack.audit(auditLogsToken).getSchemas();
            assertThat(response, is(notNullValue()));
            assertThat(callCount.get(), is(1));
        }
    }

    @Test
    public void scim() throws IOException, SCIMApiException {
        if (scimToken != null) {
            Slack slack = Slack.getInstance(config);
            ServiceProviderConfigsGetResponse response = slack.scim(scimToken).getServiceProviderConfigs(req -> req);
            assertThat(response.getAuthenticationSchemes(), is(notNullValue()));
            // auth.test can be cached
            assertThat(callCount.get(), is(greaterThanOrEqualTo(1))); // auth.test & scim
        }
    }

    @Test
    public void status() throws Exception {
        Slack slack = Slack.getInstance(config);
        CurrentStatus current = slack.status().current();
        assertThat(current, is(notNullValue()));
        assertThat(callCount.get(), is(1));
    }

    @Test
    public void socketMode() throws Exception {
        Slack slack = Slack.getInstance(config);
        SocketModeClient socketModeClient = slack.socketMode(appLevelToken);
        socketModeClient.connect();
        try {
            assertThat(callCount.get(), is(2));
        } finally {
            socketModeClient.close();
        }
    }

    // FIXME: This does not pass
    @Ignore
    @Test
    public void socketMode_JavaWebSocket() throws Exception {
        Slack slack = Slack.getInstance(config);
        SocketModeClient socketModeClient = slack.socketMode(appLevelToken, SocketModeClient.Backend.JavaWebSocket);
        socketModeClient.setAutoReconnectEnabled(false);
        socketModeClient.connect();
        try {
            assertThat(callCount.get(), is(2));
        } finally {
            socketModeClient.close();
        }
    }
}
