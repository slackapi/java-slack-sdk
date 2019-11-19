package test_with_remote_apis.methods_admin_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.admin.invite_requests.AdminInviteRequestsApprovedListRequest;
import com.github.seratch.jslack.api.methods.request.admin.invite_requests.AdminInviteRequestsDeniedListRequest;
import com.github.seratch.jslack.api.methods.request.admin.invite_requests.AdminInviteRequestsDenyRequest;
import com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersAssignRequest;
import com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersInviteRequest;
import com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersRemoveRequest;
import com.github.seratch.jslack.api.methods.request.admin.users.AdminUsersSessionResetRequest;
import com.github.seratch.jslack.api.methods.response.admin.apps.AdminAppsApproveResponse;
import com.github.seratch.jslack.api.methods.response.admin.apps.AdminAppsRequestsListResponse;
import com.github.seratch.jslack.api.methods.response.admin.apps.AdminAppsRestrictResponse;
import com.github.seratch.jslack.api.methods.response.admin.invite_requests.*;
import com.github.seratch.jslack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.github.seratch.jslack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.github.seratch.jslack.api.methods.response.admin.teams.AdminTeamsOwnersListResponse;
import com.github.seratch.jslack.api.methods.response.admin.users.*;
import com.github.seratch.jslack.api.methods.response.users.UsersListResponse;
import com.github.seratch.jslack.api.model.User;
import com.github.seratch.jslack.api.model.admin.AppRequest;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApiTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String email = System.getenv(Constants.SLACK_TEST_EMAIL);
    String userToken = System.getenv(Constants.SLACK_TEST_ADMIN_WORKSPACE_USER_OAUTH_ACCESS_TOKEN);
    String orgAdminToken = System.getenv(Constants.SLACK_TEST_ADMIN_OAUTH_ACCESS_TOKEN);
    String adminAppsTeamId = System.getenv(Constants.SLACK_TEST_ADMIN_APPS_TEAM_ID);

    @Test
    public void usersSessionReset() throws IOException, SlackApiException {
        if (userToken != null && orgAdminToken != null) {
            String userId = findUserId(Collections.emptyList());
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

    @Test
    public void inviteRequests() throws Exception {
        if (orgAdminToken != null) {
            AdminInviteRequestsApproveResponse approval = slack.methods(orgAdminToken).adminInviteRequestsApprove(r -> r
                    .teamId(adminAppsTeamId)
                    .inviteRequestId("I12345678"));
            assertThat(approval.getError(), is("invalid_request"));

            AdminInviteRequestsDenyResponse denial = slack.methods(orgAdminToken).adminInviteRequestsDeny(r -> r
                    .teamId(adminAppsTeamId)
                    .inviteRequestId("I12345678"));
            assertThat(denial.getError(), is("invalid_request"));

            AdminInviteRequestsListResponse list = slack.methods(orgAdminToken).adminInviteRequestsList(r -> r
                    .limit(1000)
                    .teamId(adminAppsTeamId));
            assertThat(list.getError(), is(nullValue()));

            AdminInviteRequestsApprovedListResponse approvedList = slack.methods(orgAdminToken).adminInviteRequestsApprovedList(r -> r
                    .limit(1000)
                    .teamId(adminAppsTeamId));
            assertThat(approvedList.getError(), is(nullValue()));

            AdminInviteRequestsDeniedListResponse deniedList = slack.methods(orgAdminToken).adminInviteRequestsDeniedList(r -> r
                    .limit(1000)
                    .teamId(adminAppsTeamId));
            assertThat(deniedList.getError(), is(nullValue()));
        }
    }

    @Ignore // til the APIs stably work with these tests
    @Test
    public void teams_users() throws Exception {
        if (orgAdminToken != null) {
            AdminTeamsAdminsListResponse admins = slack.methods(orgAdminToken).adminTeamsAdminsList(r -> r
                    .limit(100)
                    .teamId(adminAppsTeamId));
            assertThat(admins.getError(), is(nullValue()));

            AdminTeamsOwnersListResponse owners = slack.methods(orgAdminToken).adminTeamsOwnersList(r -> r
                    .limit(100)
                    .teamId(adminAppsTeamId));
            assertThat(owners.getError(), is(nullValue()));

            String teamUniqueName = "jslack-" + System.currentTimeMillis();
            AdminTeamsCreateResponse creation = slack.methods(orgAdminToken).adminTeamsCreate(r -> r
                    .teamDomain(teamUniqueName)
                    .teamName(teamUniqueName)
                    .teamDescription("Something great for you")
                    .teamDiscoverability("closed")
            );
            assertThat(creation.getError(), is(nullValue()));

            if (userToken != null && orgAdminToken != null) {
                String teamId = creation.getTeam();
                List<String> members = slack.methods(userToken).channelsList(r -> r.limit(1000)).getChannels().get(0).getMembers();
                String userId = findUserId(members);
                assertThat(userId, is(notNullValue()));

                AdminUsersAssignRequest assignReq = AdminUsersAssignRequest.builder().teamId(teamId).userId(userId).build();
                AdminUsersAssignResponse assignment = slack.methods(orgAdminToken).adminUsersAssign(assignReq);
                assertThat(assignment.getError(), is("user_already_team_member")); // TODO: fix this

                List<String> channelIds = slack.methods(userToken)
                        .conversationsList(r -> r.limit(10))
                        .getChannels()
                        .stream().map(e -> e.getId())
                        .collect(Collectors.toList());

                AdminUsersInviteRequest inviteReq = AdminUsersInviteRequest.builder()
                        .teamId(teamId)
                        .email(email)
                        .channelIds(channelIds)
                        .realName("Kazuhiro Sera")
                        .build();
                AdminUsersInviteResponse invitation = slack.methods(orgAdminToken).adminUsersInvite(inviteReq);
                // TODO: fix this
                //assertThat(invitation.getError(), is(nullValue()));

                AdminUsersRemoveRequest removeReq = AdminUsersRemoveRequest.builder().teamId(teamId).userId(userId).build();
                AdminUsersRemoveResponse removal = slack.methods(orgAdminToken).adminUsersRemove(removeReq);
                // TODO: fix this
                //assertThat(removal.getError(), is(nullValue()));

                AdminUsersSetAdminResponse setAdmin = slack.methods(orgAdminToken)
                        .adminUsersSetAdmin(r -> r.teamId(teamId).userId(userId));
                // TODO: fix this
                //assertThat(setAdmin.getError(), is(nullValue()));
                AdminUsersSetOwnerResponse setOwner = slack.methods(orgAdminToken)
                        .adminUsersSetOwner(r -> r.teamId(teamId).userId(userId));
                // TODO: fix this
                //assertThat(setOwner.getError(), is(nullValue()));
                AdminUsersSetRegularResponse setRegular = slack.methods(orgAdminToken)
                        .adminUsersSetRegular(r -> r.teamId(teamId).userId(userId));
                // TODO: fix this
                //assertThat(setRegular.getError(), is(nullValue()));
            }
        }
    }

    private String findUserId(List<String> idsToSkip) throws IOException, SlackApiException {
        String userId = null;
        UsersListResponse usersListResponse = slack.methods(userToken).usersList(req -> req);
        assertThat(usersListResponse.getError(), is(nullValue()));
        List<User> members = usersListResponse.getMembers();
        for (User member : members) {
            if (member.isBot() || member.isDeleted() || member.isAppUser() || member.isOwner() || member.isStranger()) {
                continue;
            } else {
                if (!idsToSkip.contains(member.getId())) {
                    userId = member.getId();
                    break;
                }
            }
        }
        return userId;
    }

}
