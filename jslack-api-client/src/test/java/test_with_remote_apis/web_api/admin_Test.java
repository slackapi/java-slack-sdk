package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersSessionResetRequest;
import com.github.seratch.jslack.api.methods.response.admin.apps.AdminAppsApproveResponse;
import com.github.seratch.jslack.api.methods.response.admin.apps.AdminAppsRequestsListResponse;
import com.github.seratch.jslack.api.methods.response.admin.apps.AdminAppsRestrictResponse;
import com.github.seratch.jslack.api.methods.response.admin.users.AdminUsersSessionResetResponse;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import com.github.seratch.jslack.api.model.admin.AppRequest;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Slf4j
public class admin_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String userToken = System.getenv(Constants.SLACK_TEST_ADMIN_WORKSPACE_USER_OAUTH_ACCESS_TOKEN);
    String orgAdminToken = System.getenv(Constants.SLACK_TEST_ADMIN_OAUTH_ACCESS_TOKEN);
    String adminAppsTeamId = System.getenv(Constants.SLACK_TEST_ADMIN_APPS_TEAM_ID);

    @Test
    public void usersSessionReset() throws IOException, SlackApiException {
        if (userToken != null && orgAdminToken != null) {
            String userId = null;
            UsersListResponse usersListResponse = slack.methods(userToken).usersList(req -> req);
            assertThat(usersListResponse.getError(), is(nullValue()));

            List<User> members = usersListResponse.getMembers();
            for (User member : members) {
                if (member.isBot() || member.isDeleted() || member.isAppUser() || member.isOwner() || member.isStranger()) {
                    continue;
                } else {
                    userId = member.getId();
                    break;
                }
            }
            assertThat(userId, is(notNullValue()));

            AdminUsersSessionResetResponse response = slack.methods(orgAdminToken)
                    .adminUsersSessionReset(AdminUsersSessionResetRequest.builder().userId(userId).build());
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void apps() throws Exception {
        if (orgAdminToken != null) {
            AdminAppsRequestsListResponse list = slack.methods(orgAdminToken).adminAppsRequestsList(r -> r
                    .limit(1000)
                    .teamId(adminAppsTeamId));
            assertThat(list.getError(), is(nullValue()));

            if (list.getAppRequests().size() >= 2) {
                AppRequest toBeApproved = list.getAppRequests().get(0);

                AdminAppsApproveResponse approvalError = slack.methods(orgAdminToken).adminAppsApprove(req -> req
                        .appId(toBeApproved.getId())
                        .teamId(adminAppsTeamId));
                assertThat(approvalError.getError(), is(notNullValue()));

                AdminAppsApproveResponse approval = slack.methods(orgAdminToken).adminAppsApprove(req -> req
                        .appId(toBeApproved.getApp().getId())
                        .teamId(adminAppsTeamId));
                assertThat(approval.getError(), is(nullValue()));

                AppRequest toBeRestricted = list.getAppRequests().get(1);

                AdminAppsRestrictResponse restrictionError = slack.methods(orgAdminToken).adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getId())
                        .teamId(adminAppsTeamId));
                assertThat(restrictionError.getError(), is(notNullValue()));

                AdminAppsRestrictResponse restriction = slack.methods(orgAdminToken).adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getApp().getId())
                        .teamId(adminAppsTeamId));
                assertThat(restriction.getError(), is(nullValue()));
            }
        }
    }
}