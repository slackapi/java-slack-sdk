package test_locally.api.methods_workspace_apps;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.slack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class WorkspaceAppApiTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.appsPermissionsInfo(r -> r).isOk(), is(true));
        assertThat(methods.appsPermissionsRequest(r -> r
                .scopes(Arrays.asList("foo", "bar"))
        ).isOk(), is(true));
        assertThat(methods.appsPermissionsResourcesList(AppsPermissionsResourcesListRequest.builder().build()).isOk(), is(true));
        assertThat(methods.appsPermissionsScopesList(AppsPermissionsScopesListRequest.builder().build()).isOk(), is(true));
        assertThat(methods.appsPermissionsUsersList(AppsPermissionsUsersListRequest.builder().build()).isOk(), is(true));
        assertThat(methods.appsPermissionsUsersRequest(AppsPermissionsUsersRequestRequest.builder()
                .scopes(Arrays.asList("foo", "bar")).build()
        ).isOk(), is(true));

    }

}
