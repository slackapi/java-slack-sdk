package util;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            "  \"error\": \"invalid\"\n" +
            "}";

    static String oauthV2Access = "{\n" +
            "    \"ok\": true,\n" +
            "    \"access_token\": \"" + ValidToken + "\",\n" +
            "    \"token_type\": \"bot\",\n" +
            "    \"scope\": \"commands,incoming-webhook\",\n" +
            "    \"bot_user_id\": \"U0KRQLJ9H\",\n" +
            "    \"app_id\": \"A0KRD7HC3\",\n" +
            "    \"team\": {\n" +
            "        \"name\": \"Slack Softball Team\",\n" +
            "        \"id\": \"T9TK3CUKW\"\n" +
            "    },\n" +
            "    \"enterprise\": {\n" +
            "        \"name\": \"slack-sports\",\n" +
            "        \"id\": \"E12345678\"\n" +
            "    },\n" +
            "    \"authed_user\": {\n" +
            "        \"id\": \"U1234\",\n" +
            "        \"scope\": \"chat:write\",\n" +
            "        \"access_token\": \"xoxp-1234\",\n" +
            "        \"token_type\": \"user\"\n" +
            "    }\n" +
            "}";

    @WebServlet
    public static class AuthTestMockEndpoint extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setStatus(200);
            resp.setContentType("application/json");
            if (req.getRequestURI().equals("/api/oauth.v2.access")) {
                resp.getWriter().write(oauthV2Access);
                return;
            }
            if (req.getHeader("Authorization") == null || !req.getHeader("Authorization").equals("Bearer " + ValidToken)) {
                resp.getWriter().write(ng);
            } else {
                resp.getWriter().write(ok);
            }
        }
    }

    private final int port;
    private final Server server;

    public AuthTestMockServer() {
        this(PortProvider.getPort(AuthTestMockServer.class.getName()));
    }

    public AuthTestMockServer(int port) {
        this.port = port;
        server = new Server(this.port);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(AuthTestMockEndpoint.class, "/*");
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
