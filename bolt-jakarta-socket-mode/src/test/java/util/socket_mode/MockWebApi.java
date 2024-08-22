package util.socket_mode;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static util.socket_mode.MockWebSocketServer.WEB_SOCKET_SERVER_PORT;

@WebServlet
@Slf4j
public class MockWebApi extends HttpServlet {

    public static final String VALID_APP_TOKEN_PREFIX = "xapp-valid";
    public static final String VALID_BOT_TOKEN_PREFIX = "xoxb-valid";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream is = req.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            String requestBody = br.lines().collect(Collectors.joining());
            log.info("request body: {}", requestBody);
        }
        String authorizationHeader = req.getHeader("Authorization");
        if (authorizationHeader != null
                && !authorizationHeader.trim().isEmpty()) {
            String methodName = req.getRequestURI().replaceFirst("^/api/", "");
            if (methodName.equals("apps.connections.open")) {
                if (authorizationHeader.startsWith("Bearer " + VALID_APP_TOKEN_PREFIX)) {
                    resp.setStatus(200);
                    String port = System.getProperty(WEB_SOCKET_SERVER_PORT);
                    resp.getWriter().write("{\"ok\":true,\"url\":\"ws:\\/\\/127.0.0.1:" + port + "\\/\"}");
                    resp.setContentType("application/json");
                    return;
                }
            }
            if (methodName.equals("auth.test")) {
                if (authorizationHeader.startsWith("Bearer " + VALID_BOT_TOKEN_PREFIX)) {
                    String body = "{\n" +
                            "  \"ok\": true,\n" +
                            "  \"url\": \"https://java-slack-sdk-test.slack.com/\",\n" +
                            "  \"team\": \"java-slack-sdk-test\",\n" +
                            "  \"user\": \"test_user\",\n" +
                            "  \"team_id\": \"T1234567\",\n" +
                            "  \"user_id\": \"U1234567\",\n" +
                            "  \"bot_id\": \"B12345678\",\n" +
                            "  \"enterprise_id\": \"E12345678\",\n" +
                            "  \"error\": \"\"\n" +
                            "}";
                    resp.setStatus(200);
                    resp.getWriter().write(body);
                    resp.setContentType("application/json");
                    return;
                }
            }
        } else if (!authorizationHeader.startsWith("Bearer " + VALID_APP_TOKEN_PREFIX)) {
            resp.setStatus(200);
            resp.getWriter().write("{\"ok\":false,\"error\":\"invalid_auth\"}");
            resp.setContentType("application/json");
            return;
        }
        resp.setStatus(404);
        resp.getWriter().write("Not Found");
    }
}
