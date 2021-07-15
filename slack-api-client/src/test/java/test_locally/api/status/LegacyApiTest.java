package test_locally.api.status;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.status.v1.model.LegacyCurrentStatus;
import com.slack.api.status.v1.model.LegacySlackIssue;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.FileReader;
import util.PortProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class LegacyApiTest {

    private static final FileReader reader = new FileReader("../json-logs/samples/status/api/v1.0.0/");

    @Slf4j
    @WebServlet
    public static class StatusMockApi extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            handle(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            handle(req, resp);
        }

        private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    int port = PortProvider.getPort(LegacyApiTest.class.getName());
    Server server = new Server(port);

    {
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(StatusMockApi.class, "/*");
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
    public void current() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setLegacyStatusEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        LegacyCurrentStatus response = Slack.getInstance(config).statusLegacy().current();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void history() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setLegacyStatusEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        List<LegacySlackIssue> response = Slack.getInstance(config).statusLegacy().history();
        assertThat(response, is(notNullValue()));
    }
}
