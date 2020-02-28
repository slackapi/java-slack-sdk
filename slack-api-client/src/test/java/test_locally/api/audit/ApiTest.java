package test_locally.api.audit;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.audit.AuditClient;
import com.slack.api.audit.response.LogsResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test_locally.api.util.FileReader;
import test_locally.api.util.PortProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class ApiTest {

    public static final String ValidToken = "xoxb-this-is-valid";
    public static final String InvalidToken = "xoxb-this-is-INVALID";

    private static final FileReader reader = new FileReader("../json-logs/samples/audit/v1/");

    @WebServlet
    public static class AuditLogsMockApi extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            try (InputStream is = req.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader br = new BufferedReader(isr)) {
                String requestBody = br.lines().collect(Collectors.joining());
                log.info("request body: {}", requestBody);
            }
            String endpoint = req.getRequestURI().replaceFirst("^/api/", "");
            String body = reader.readWholeAsString(endpoint + ".json");
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

    int port = PortProvider.getPort(ApiTest.class.getName());
    Server server = new Server(port);

    {
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(AuditLogsMockApi.class, "/*");
    }

    @Before
    public void setup() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void getLogs() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setAuditEndpointUrlPrefix("http://localhost:" + port + "/api/");

        AuditClient audit = Slack.getInstance(config).audit("token");
        LogsResponse response = audit.getLogs(r -> r.action("something"));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void getSchemas() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setAuditEndpointUrlPrefix("http://localhost:" + port + "/api/");

        AuditClient audit = Slack.getInstance(config).audit("token");
        assertThat(audit.getSchemas().isOk(), is(true));
    }

    @Test
    public void getActions() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setAuditEndpointUrlPrefix("http://localhost:" + port + "/api/");

        AuditClient audit = Slack.getInstance(config).audit("token");
        assertThat(audit.getActions().isOk(), is(true));
    }

}
