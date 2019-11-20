package test_with_remote_apis.methods_workspace_apps;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsInfoResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.resources.AppsPermissionsResourcesListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.scopes.AppsPermissionsScopesListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.users.AppsPermissionsUsersListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.users.AppsPermissionsUsersRequestResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// TODO: valid test
@Slf4j
public class WorkspaceAppApiTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void appsPermissionsRequest() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsRequestResponse response = slack.methods().appsPermissionsRequest(req -> req
                .token(token)
                .triggerId("dummy"));
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsInfo() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsInfoResponse response = slack.methods().appsPermissionsInfo(req -> req
                .token(token));
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsResourcesList() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsResourcesListResponse response = slack.methods().appsPermissionsResourcesList(AppsPermissionsResourcesListRequest.builder()
                .token(token)
                .limit(10)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsScopesList() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsScopesListResponse response = slack.methods().appsPermissionsScopesList(AppsPermissionsScopesListRequest.builder()
                .token(token)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsUsersList() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsUsersListResponse response = slack.methods().appsPermissionsUsersList(AppsPermissionsUsersListRequest.builder()
                .token(token)
                .limit(10)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsUsersRequest() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsUsersRequestResponse response = slack.methods().appsPermissionsUsersRequest(AppsPermissionsUsersRequestRequest.builder()
                .token(token)
                .triggerId("abc")
                .user("U0000000")
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

}
