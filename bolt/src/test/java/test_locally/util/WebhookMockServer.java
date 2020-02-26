package test_locally.util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebhookMockServer {

    @WebServlet
    public static class WebhookMockEndpoint extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            if (req.getRequestURI().contains("INVALID")) {
                resp.setStatus(400);
                resp.getWriter().write("no_service");
            } else {
                resp.setStatus(200);
                resp.getWriter().write("ok");
            }
        }
    }

    private final int port;
    private final Server server;

    public WebhookMockServer() {
        this(PortProvider.getPort(WebhookMockServer.class.getName()));
    }

    public WebhookMockServer(int port) {
        this.port = port;
        server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(WebhookMockEndpoint.class, "/*");
    }

    public String getWebhookURL() {
        return "http://localhost:" + port;
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
