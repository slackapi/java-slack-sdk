package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import java.net.SocketException;

/**
 * A mock server that just returns JSON data under the json-logs/sample directory.
 */
public class MockSlackApiServer {

    private int port;
    private Server server;
    private final HttpServlet servlet;

    public MockSlackApiServer() {
        this(PortProvider.getPort(MockSlackApiServer.class.getName()));
    }

    public MockSlackApiServer(int port) {
        this(port, new MockSlackApi());
    }

    public MockSlackApiServer(HttpServlet servlet) {
        this(PortProvider.getPort(MockSlackApiServer.class.getName()), servlet);
    }

    private MockSlackApiServer(int port, HttpServlet servlet) {
        this.servlet = servlet;
        setup(port);
    }

    private void setup(int port) {
        this.port = port;
        this.server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(new ServletHolder(servlet), "/*");
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
                setup(PortProvider.getPort(MockSlackApiServer.class.getName()));
                retryCount++;
            }
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

}
