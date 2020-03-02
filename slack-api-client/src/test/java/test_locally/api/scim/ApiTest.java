package test_locally.api.scim;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.scim.SCIMClient;
import com.slack.api.scim.model.Group;
import com.slack.api.scim.model.User;
import com.slack.api.scim.request.GroupsPatchRequest;
import com.slack.api.scim.response.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

public class ApiTest {

    public static final String ValidToken = "xoxb-this-is-valid";
    public static final String InvalidToken = "xoxb-this-is-INVALID";

    private static final FileReader reader = new FileReader("../json-logs/samples/scim/v1/");

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
            if (req.getMethod().equals("POST")) {
                endpoint += "/000000000";
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

    int port = PortProvider.getPort(ApiTest.class.getName());
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
        config.setScimEndpointUrlPrefix("http://localhost:" + port + "/api/");
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void searchUsers() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        UsersSearchResponse response = scim.searchUsers(r -> r.filter("some filter").count(123).startIndex(1));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void searchGroups() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        GroupsSearchResponse response = scim.searchGroups(r -> r.filter("some filter").count(123).startIndex(1));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getServiceProviderConfigs() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        ServiceProviderConfigsGetResponse response = scim.getServiceProviderConfigs(r -> r);
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void readUser() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        UsersReadResponse response = scim.readUser(r -> r.id("000000000"));
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("W00000000"));
    }

    @Test
    public void createUser() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        User user = new User();
        user.setUserName("Kazuhiro Sera");
        UsersCreateResponse response = scim.createUser(r -> r.user(user));
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("W00000000"));
    }

    @Test
    public void patchUser() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        User user = new User();
        user.setUserName("Kazuhiro Sera");
        UsersPatchResponse response = scim.patchUser(r -> r.id("000000000").user(user));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void updateUser() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        User user = new User();
        user.setUserName("Kazuhiro Sera");
        UsersUpdateResponse response = scim.updateUser(r -> r.id("000000000").user(user));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteUser() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        UsersDeleteResponse response = scim.deleteUser(r -> r.id("000000000"));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void readGroup() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        GroupsReadResponse response = scim.readGroup(r -> r.id("000000000"));
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("S00000000"));
    }

    @Test
    public void createGroup() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        Group group = new Group();
        group.setDisplayName("Kazuhiro Sera");
        GroupsCreateResponse response = scim.createGroup(r -> r.group(group));
        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("S00000000"));
    }

    @Test
    public void patchGroup() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        GroupsPatchRequest.GroupOperation group = new GroupsPatchRequest.GroupOperation();
        group.setMembers(Arrays.asList());
        GroupsPatchResponse response = scim.patchGroup(r -> r.id("000000000").group(group));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void updateGroup() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        Group group = new Group();
        group.setDisplayName("Kazuhiro Sera");
        GroupsUpdateResponse response = scim.updateGroup(r -> r.id("000000000").group(group));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteGroup() throws Exception {
        SCIMClient scim = Slack.getInstance(config).scim(ValidToken);
        GroupsDeleteResponse response = scim.deleteGroup(r -> r.id("000000000"));
        assertThat(response, is(notNullValue()));
    }
}
