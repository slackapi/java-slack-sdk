package util.socket_mode;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import util.PortProvider;

public class MockWebApiServer {

    private final int port;
    private final Server server;

    public MockWebApiServer() {
        this(PortProvider.getPort(MockWebApiServer.class.getName()));
    }

    public MockWebApiServer(int port) {
        this.port = port;
        server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(MockWebApi.class, "/*");
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
