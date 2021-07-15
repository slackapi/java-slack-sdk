package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MockWebhookServer {

    @WebServlet
    public static class WebhookMockApi extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setStatus(200);
            resp.getWriter().write("OK");
        }
    }

    private final int port;
    private final Server server;

    public MockWebhookServer() {
        this(PortProvider.getPort(MockWebhookServer.class.getName()));
    }

    public MockWebhookServer(int port) {
        this.port = port;
        server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(WebhookMockApi.class, "/*");
    }

    public String getWebhookUrl() {
        return "http://127.0.0.1:" + port;
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
