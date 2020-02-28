package test_locally.api.scim;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.scim.SCIMClient;
import com.slack.api.scim.response.GroupsSearchResponse;
import com.slack.api.scim.response.ServiceProviderConfigsGetResponse;
import com.slack.api.scim.response.UsersSearchResponse;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiTest {

    public static final String ValidToken = "xoxb-this-is-valid";
    public static final String InvalidToken = "xoxb-this-is-INVALID";

    private static final FileReader reader = new FileReader("../json-logs/samples/scim/v1/");

    @Slf4j
    @WebServlet
    public static class SCIMMockApi extends HttpServlet {
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

    int port = PortProvider.getPort(ApiTest.class.getName());
    Server server = new Server(port);

    {
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SCIMMockApi.class, "/*");
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
    public void searchUsers() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setScimEndpointUrlPrefix("http://localhost:" + port + "/api/");

        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        UsersSearchResponse response = scim.searchUsers(r -> r.filter("some filter").count(123).startIndex(1));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void searchGroups() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setScimEndpointUrlPrefix("http://localhost:" + port + "/api/");

        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        GroupsSearchResponse response = scim.searchGroups(r -> r.filter("some filter").count(123).startIndex(1));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getServiceProviderConfigs() throws Exception {
        SlackConfig config = new SlackConfig();
        config.setScimEndpointUrlPrefix("http://localhost:" + port + "/api/");

        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        ServiceProviderConfigsGetResponse response = scim.getServiceProviderConfigs(r -> r);
        assertThat(response, is(notNullValue()));
    }
}
