package util.socket_mode;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import util.MockSlackApi;
import util.PortProvider;

import java.net.SocketException;

public class MockWebApiServer {

    private int port;
    private Server server;

    public MockWebApiServer() {
        this(PortProvider.getPort(MockWebApiServer.class.getName()));
    }

    public MockWebApiServer(int port) {
        setup(port);
    }

    private void setup(int port) {
        this.port = port;
        this.server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(MockWebApi.class, "/*");
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
                setup(PortProvider.getPort(MockWebApiServer.class.getName()));
                retryCount++;
            }
        }
    }

    public void stop() throws Exception {
        server.stop();
    }
}
