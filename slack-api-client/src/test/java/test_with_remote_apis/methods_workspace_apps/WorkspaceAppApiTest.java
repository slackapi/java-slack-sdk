package test_with_remote_apis.methods_workspace_apps;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.slack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.slack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import com.slack.api.methods.response.apps.permissions.AppsPermissionsInfoResponse;
import com.slack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import com.slack.api.methods.response.apps.permissions.resources.AppsPermissionsResourcesListResponse;
import com.slack.api.methods.response.apps.permissions.scopes.AppsPermissionsScopesListResponse;
import com.slack.api.methods.response.apps.permissions.users.AppsPermissionsUsersListResponse;
import com.slack.api.methods.response.apps.permissions.users.AppsPermissionsUsersRequestResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

// TODO: valid test
@Slf4j
public class WorkspaceAppApiTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void appsPermissionsRequest() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        AppsPermissionsRequestResponse response = slack.methods().appsPermissionsRequest(req -> req
                .token(userToken)
                .triggerId("dummy"));
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsInfo() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        AppsPermissionsInfoResponse response = slack.methods().appsPermissionsInfo(req -> req
                .token(userToken));
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsResourcesList() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        AppsPermissionsResourcesListResponse response = slack.methods().appsPermissionsResourcesList(AppsPermissionsResourcesListRequest.builder()
                .token(userToken)
                .limit(10)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsScopesList() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        AppsPermissionsScopesListResponse response = slack.methods().appsPermissionsScopesList(AppsPermissionsScopesListRequest.builder()
                .token(userToken)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    // the endpoint is no longer available
    @Ignore
    @Test
    public void appsPermissionsUsersList() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        AppsPermissionsUsersListResponse response = slack.methods().appsPermissionsUsersList(AppsPermissionsUsersListRequest.builder()
                .token(userToken)
                .limit(10)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    // the endpoint is no longer available
    @Ignore
    @Test
    public void appsPermissionsUsersRequest() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        AppsPermissionsUsersRequestResponse response = slack.methods().appsPermissionsUsersRequest(AppsPermissionsUsersRequestRequest.builder()
                .token(userToken)
                .triggerId("abc")
                .user("U0000000")
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

}
