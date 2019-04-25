package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.apps.AppsUninstallRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsInfoRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.AppsPermissionsRequestRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.resources.AppsPermissionsResourcesListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.scopes.AppsPermissionsScopesListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.users.AppsPermissionsUsersListRequest;
import com.github.seratch.jslack.api.methods.request.apps.permissions.users.AppsPermissionsUsersRequestRequest;
import com.github.seratch.jslack.api.methods.response.apps.AppsUninstallResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsInfoResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.AppsPermissionsRequestResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.resources.AppsPermissionsResourcesListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.scopes.AppsPermissionsScopesListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.users.AppsPermissionsUsersListResponse;
import com.github.seratch.jslack.api.methods.response.apps.permissions.users.AppsPermissionsUsersRequestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import testing.Constants;
import testing.SlackTestConfig;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

// TODO: valid test
@Slf4j
public class Slack_apps_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void appsPermissionsRequest() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsRequestResponse response = slack.methods().appsPermissionsRequest(AppsPermissionsRequestRequest.builder()
                .token(token)
                .triggerId("dummy")
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsPermissionsInfo() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsPermissionsInfoResponse response = slack.methods().appsPermissionsInfo(AppsPermissionsInfoRequest.builder()
                .token(token)
                .build());
        assertThat(response.getError(), is("not_allowed_token_type"));
        assertThat(response.isOk(), is(false));
    }

    @Test
    public void appsUninstall() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        AppsUninstallResponse response = slack.methods().appsUninstall(AppsUninstallRequest.builder()
                .token(token)
                .build());
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