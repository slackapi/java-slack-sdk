package util;

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

/**
 * A mock API that just returns JSON data under the json-logs/sample directory.
 */
@WebServlet
@Slf4j
public class MockSlackApi extends HttpServlet {

    public static final String ValidToken = "xoxb-this-is-valid";
    public static final String ValidFunctionToken = "xwfp-this-is-valid";
    public static final String InvalidToken = "xoxb-this-is-INVALID";

    private final FileReader reader = new FileReader("../json-logs/samples/api/");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String requestBody = null;
        try (InputStream is = req.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            requestBody = br.lines().collect(Collectors.joining());
            log.info("request body: {}", requestBody);
        }
        String methodName = req.getRequestURI().replaceFirst("^/api/", "");
        if (!methodName.equals("api.test") && !methodName.startsWith("oauth.")) {
            String authorizationHeader = req.getHeader("Authorization");
            if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
                resp.setStatus(200);
                resp.getWriter().write("{\"ok\":false,\"error\":\"not_authed\"}");
                resp.setContentType("application/json");
                return;
            } else if (!authorizationHeader.equals("Bearer " + ValidToken)
                    && !authorizationHeader.equals("Bearer " + ValidFunctionToken)) {
                resp.setStatus(200);
                resp.getWriter().write("{\"ok\":false,\"error\":\"invalid_auth\"}");
                resp.setContentType("application/json");
                return;
            }
        }

        String body = reader.readWholeAsString(methodName + ".json");
        if (methodName.startsWith("oauth.")) {
            // oauth.v2.access / oauth.access
            if (requestBody.contains("code=valid")) {
                body = body.replaceFirst("\"ok\": false,", "\"ok\": true,");
                body = body.replaceFirst("\"access_token\": \"\",", "\"access_token\": \"" + ValidToken + "\",");
                body = body.replaceFirst("\"bot_access_token\": \"\",", "\"bot_access_token\": \"" + ValidToken + "\",");
            } else {
                body = body.replaceFirst("\"error\": \"\"", "\"error\": \"something-wrong\"");
            }
        } else if (methodName.startsWith("functions.") && !requestBody.contains("function_execution_id=Fx")) {
            body = body.replaceFirst("\"error\": \"\"", "\"error\": \"invalid function_execution_id\"");
        } else {
            body = body.replaceFirst("\"ok\": false,", "\"ok\": true,");
        }

        if (methodName.equals("auth.test")) {
            body = "{\n" +
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
        }
        if (body == null || body.trim().isEmpty()) {
            resp.setStatus(400);
            return;
        }
        resp.setStatus(200);
        resp.getWriter().write(body);
        resp.setContentType("application/json");
    }

}
