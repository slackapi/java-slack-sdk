package util.socket_mode;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.server.NativeWebSocketServletContainerInitializer;
import org.eclipse.jetty.websocket.server.WebSocketUpgradeFilter;
import util.PortProvider;

import javax.servlet.ServletException;
import java.net.SocketException;

public class MockWebSocketServer {

    public static final String WEB_SOCKET_SERVER_PORT = "WEB_SOCKET_SERVER_PORT";

    private Server server;

    public MockWebSocketServer() {
        setup();
    }

    public void setup() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        int port = PortProvider.getPort(MockWebSocketServer.class.getName());
        System.setProperty(WEB_SOCKET_SERVER_PORT, String.valueOf(port));
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        NativeWebSocketServletContainerInitializer initializer = new NativeWebSocketServletContainerInitializer();
        initializer.getDefaultFrom(context.getServletContext()).addMapping("/*", MockSocketMode.class);

        try {
            WebSocketUpgradeFilter upgradeFilter = WebSocketUpgradeFilter.configureContext(context);
            context.setAttribute(WebSocketUpgradeFilter.class.getName() + ".SCI", upgradeFilter);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
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
                setup();
                retryCount++;
            }
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

}
