package test_locally.api.scim2;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.scim2.AsyncSCIM2Client;
import com.slack.api.scim2.model.Group;
import com.slack.api.scim2.model.PatchOperation;
import com.slack.api.scim2.model.User;
import com.slack.api.scim2.request.GroupsPatchOperation;
import com.slack.api.scim2.request.UsersPatchOperation;
import com.slack.api.scim2.response.*;
import com.slack.api.util.http.listener.HttpResponseListener;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.FileReader;
import util.PortProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class AsyncApiTest {

    public static final String ValidToken = "xoxb-this-is-valid-scim";

    private static final FileReader reader = new FileReader("../json-logs/samples/scim/v2/");

    private static final Logger logger = LoggerFactory.getLogger(AsyncApiTest.class);

    @Slf4j
    @WebServlet
    public static class SCIMMockApi extends HttpServlet {
        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
            if (endpoint.equals("auth.test")) {
                String body = "{\n" +
                        "  \"ok\": true,\n" +
                        "  \"url\": \"https://java-slack-sdk-test.slack.com/\",\n" +
                        "  \"team\": \"java-slack-sdk-test\",\n" +
                        "  \"user\": \"test_user\",\n" +
                        "  \"team_id\": \"E12345678\",\n" +
                        "  \"enterprise_id\": \"E12345678\",\n" +
                        "  \"is_enterprise_install\": true,\n" +
                        "  \"user_id\": \"U1234567\"\n" +
                        "}";
                resp.setStatus(200);
                resp.getWriter().write(body);
                resp.setContentType("application/json");
                return;
            }

            if (!req.getMethod().equals("GET") && !endpoint.endsWith("/00000000000")) {
                endpoint += "/00000000000";
            }
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

    int port = PortProvider.getPort(AsyncApiTest.class.getName());
    Server server = new Server(port);

    {
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SCIMMockApi.class, "/*");
    }

    SlackConfig config = new SlackConfig();

    @Before
    public void setup() throws Exception {
        server.start();
        config = new SlackConfig();
        config.setMethodsEndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.setScim2EndpointUrlPrefix("http://127.0.0.1:" + port + "/api/");
        config.getHttpClientResponseHandlers().add(new HttpResponseListener() {
            @Override
            public void accept(State state) {
                logger.debug("--- (SCIM Stats) ---\n" + GsonFactory.createSnakeCase(config).toJson(
                        config.getSCIM2Config().getMetricsDatastore().getAllStats()));
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void searchUsers() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        UsersSearchResponse response = scim.searchUsers(r -> r
                .filter("some filter").count(123).startIndex(1)).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void searchGroups() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        GroupsSearchResponse response = scim.searchGroups(r -> r
                .filter("some filter").count(123).startIndex(1)).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getServiceProviderConfigs() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        ServiceProviderConfigsGetResponse response = scim.getServiceProviderConfigs(r -> r).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void readUser() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        UsersReadResponse response = scim.readUser(r -> r.id("00000000000")).get();
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("W00000000"));
    }

    @Test
    public void createUser() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        User user = new User();
        user.setUserName("Kazuhiro Sera");
        UsersCreateResponse response = scim.createUser(r -> r.user(user)).get();
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("W00000000"));
    }

    @Test
    public void patchUser() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        UsersPatchResponse response = scim.patchUser(r -> r.id("00000000000").operations(Arrays.asList(
                UsersPatchOperation.builder().op(PatchOperation.Add).path("userName").stringValue("foo").build()
        ))).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void updateUser() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        User user = new User();
        user.setUserName("Kazuhiro Sera");
        UsersUpdateResponse response = scim.updateUser(r -> r.id("00000000000").user(user)).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteUser() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        UsersDeleteResponse response = scim.deleteUser(r -> r.id("00000000000")).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void readGroup() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        GroupsReadResponse response = scim.readGroup(r -> r.id("00000000000")).get();
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("S00000000"));
    }

    @Test
    public void createGroup() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        Group group = new Group();
        group.setDisplayName("Kazuhiro Sera");
        GroupsCreateResponse response = scim.createGroup(r -> r.group(group)).get();
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("S00000000"));
    }

    @Test
    public void patchGroup() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        GroupsPatchResponse response = scim.patchGroup(r -> r.id("00000000000").operations(Arrays.asList(
                GroupsPatchOperation.builder().op(PatchOperation.Remove).path("members").build()
        ))).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void updateGroup() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        Group group = new Group();
        group.setDisplayName("Kazuhiro Sera");
        GroupsUpdateResponse response = scim.updateGroup(r -> r.id("00000000000").group(group)).get();
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteGroup() throws Exception {
        AsyncSCIM2Client scim = Slack.getInstance(config).scim2Async(ValidToken);
        GroupsDeleteResponse response = scim.deleteGroup(r -> r.id("00000000000")).get();
        assertThat(response, is(notNullValue()));
    }
}
