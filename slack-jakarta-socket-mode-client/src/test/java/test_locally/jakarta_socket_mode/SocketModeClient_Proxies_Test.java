package test_locally.jakarta_socket_mode;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.jakarta_socket_mode.JakartaSocketModeClientFactory;
import com.slack.api.socket_mode.SocketModeClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Credentials;
import org.eclipse.jetty.proxy.ConnectHandler;
import org.eclipse.jetty.proxy.ProxyServlet;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.mock_server.MockWebApiServer;
import util.mock_server.PortProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SocketModeClient_Proxies_Test {

    static final String VALID_APP_TOKEN = "xapp-valid-123123123123123123123123123123123123";

    MockWebApiServer webApiServer = new MockWebApiServer();
    SlackConfig config = new SlackConfig();

    static Server proxyServer = new Server();
    static ServerConnector proxyServerConnector = new ServerConnector(proxyServer);
    static Integer proxyServerPort;

    AtomicInteger proxyCallCount = new AtomicInteger(0);

    @Before
    public void setUp() throws Exception {
        webApiServer.start();
        config.setMethodsEndpointUrlPrefix(webApiServer.getMethodsEndpointPrefix());

        // https://github.com/eclipse/jetty.project/blob/jetty-9.2.30.v20200428/examples/embedded/src/main/java/org/eclipse/jetty/embedded/ProxyServer.java
        proxyServerPort = PortProvider.getPort(SocketModeClient_Proxies_Test.class.getName());
        proxyServerConnector.setPort(proxyServerPort);
        proxyServer.addConnector(proxyServerConnector);
        ConnectHandler proxy = new ConnectHandler() {
            @Override
            public void handle(String target, Request br, HttpServletRequest request, HttpServletResponse res)
                    throws ServletException, IOException {
                log.info("Proxy server handles a new connection (target: {})", target);
                super.handle(target, br, request, res);
                if (res.getStatus() != 407) {
                    proxyCallCount.incrementAndGet();
                }
            }

            @Override
            protected boolean handleAuthentication(HttpServletRequest request, HttpServletResponse response, String address) {
                for (String name : Collections.list(request.getHeaderNames())) {
                    log.info("{}: {}", name, request.getHeader(name));
                    if (name.toLowerCase(Locale.ENGLISH).equals("proxy-authorization")) {
                        return request.getHeader(name).equals("Basic bXktdXNlcm5hbWU6bXktcGFzc3dvcmQ=");
                    }
                }
                return false;
            }
        };
        proxyServer.setHandler(proxy);
        ServletContextHandler context = new ServletContextHandler(proxy, "/", ServletContextHandler.SESSIONS);
        ServletHolder proxyServlet = new ServletHolder(ProxyServlet.class);
        context.addServlet(proxyServlet, "/*");
        proxyServer.start();

        config.setProxyUrl("http://127.0.0.1:" + proxyServerPort);

        Map<String, String> proxyHeaders = new HashMap<>();
        String username = "my-username";
        String password = "my-password";
        proxyHeaders.put("Proxy-Authorization", Credentials.basic(username, password));
        config.setProxyHeaders(proxyHeaders);
    }

    @After
    public void tearDown() throws Exception {
        webApiServer.stop();

        proxyServer.removeConnector(proxyServerConnector);
        proxyServer.stop();
    }

    @Test
    public void proxyAuth() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix(webApiServer.getMethodsEndpointPrefix());
        config.setProxyUrl("http://my-username:my-password@localhost:" + proxyServerPort + "/");
        Slack slack = Slack.getInstance(config);
        SocketModeClient client = JakartaSocketModeClientFactory.create(slack, VALID_APP_TOKEN);
        client.close();
    }
}
