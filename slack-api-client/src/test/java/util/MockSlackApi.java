package util;

import lombok.extern.slf4j.Slf4j;
import test_locally.api.util.FileReader;

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
    public static final String InvalidToken = "xoxb-this-is-INVALID";

    private final FileReader reader = new FileReader("../json-logs/samples/api/");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream is = req.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr)) {
            String requestBody = br.lines().collect(Collectors.joining());
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
            } else if (!authorizationHeader.equals("Bearer " + ValidToken)) {
                resp.setStatus(200);
                resp.getWriter().write("{\"ok\":false,\"error\":\"invalid_auth\"}");
                resp.setContentType("application/json");
                return;
            }
        }

        String body = reader.readWholeAsString(methodName + ".json");
        body = body.replaceFirst("\"ok\": false,", "\"ok\": true,");
        if (body == null || body.trim().isEmpty()) {
            resp.setStatus(400);
            return;
        }
        resp.setStatus(200);
        resp.getWriter().write(body);
        resp.setContentType("application/json");
    }

}
