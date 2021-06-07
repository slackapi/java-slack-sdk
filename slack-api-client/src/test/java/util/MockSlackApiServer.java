package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.net.SocketException;

/**
 * A mock server that just returns JSON data under the json-logs/sample directory.
 */
public class MockSlackApiServer {

    private int port;
    private Server server;

    public MockSlackApiServer() {
        this(PortProvider.getPort(MockSlackApiServer.class.getName()));
    }

    public MockSlackApiServer(int port) {
        setup(port);
    }

    private void setup(int port) {
        this.port = port;
        this.server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(MockSlackApi.class, "/*");
    }

    public String getMethodsEndpointPrefix() {
        return "http://localhost:" + port + "/api/";
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
