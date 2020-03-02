package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A mock server that just returns JSON data under the json-logs/sample directory.
 */
public class MockSlackApiServer {

    private final int port;
    private final Server server;

    public MockSlackApiServer() {
        this(PortProvider.getPort(MockSlackApiServer.class.getName()));
    }

    public MockSlackApiServer(int port) {
        this.port = port;
        server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(MockSlackApi.class, "/*");
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
