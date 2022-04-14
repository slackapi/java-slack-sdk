package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketException;

public class AuthTestMockServer {

    public static final String ValidToken = "xoxb-this-is-valid";

    static String ok = "{\n" +
            "  \"ok\": true,\n" +
            "  \"url\": \"https://java-slack-sdk-test.slack.com/\",\n" +
            "  \"team\": \"java-slack-sdk-test\",\n" +
            "  \"user\": \"test_user\",\n" +
            "  \"team_id\": \"T1234567\",\n" +
            "  \"user_id\": \"U1234567\",\n" +
            "  \"bot_id\": \"B12345678\",\n" +
            "  \"enterprise_id\": \"E12345678\"\n" +
            "}";
    static String ng = "{\n" +
            "  \"ok\": false,\n" +
            "  \"error\": \"invalid_auth_local\"\n" +
            "}";

    @WebServlet
    public static class AuthTestMockEndpoint extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setStatus(200);
            resp.setContentType("application/json");
            if (req.getHeader("Authorization") == null || !req.getHeader("Authorization").equals("Bearer " + ValidToken)) {
                resp.getWriter().write(ng);
            } else {
                resp.getWriter().write(ok);
            }
        }
    }

    private int port;
    private Server server;

    public AuthTestMockServer() {
        this(PortProvider.getPort(AuthTestMockServer.class.getName()));
    }

    public AuthTestMockServer(int port) {
        setup(port);
    }

    private void setup(int port) {
        this.port = port;
        this.server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(AuthTestMockEndpoint.class, "/*");
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
                setup(PortProvider.getPort(AuthTestMockServer.class.getName()));
                retryCount++;
            }
        }
    }

    public void stop() throws Exception {
        server.stop();
    }
}
