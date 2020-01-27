package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.admin.users.AdminUsersAssignRequest;
import com.slack.api.methods.request.admin.users.AdminUsersInviteRequest;
import com.slack.api.methods.request.admin.users.AdminUsersRemoveRequest;
import com.slack.api.methods.request.admin.users.AdminUsersSessionResetRequest;
import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.admin.conversations.AdminConversationsSetTeamsResponse;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.conversations.ConversationsCreateResponse;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ConversationType;
import com.slack.api.model.User;
import com.slack.api.model.admin.AppRequest;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApiTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String email = System.getenv(Constants.SLACK_SDK_TEST_EMAIL_ADDRESS);
    String teamAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
    String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);
    String sharedChannelId = System.getenv(Constants.SLACK_SDK_TEST_GRID_SHARED_CHANNEL_ID);

    @Test
    public void usersSessionReset() throws IOException, SlackApiException {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String userId = findUserId(Collections.emptyList());
            assertThat(userId, is(notNullValue()));
            AdminUsersSessionResetResponse response = slack.methods(orgAdminUserToken)
                    .adminUsersSessionReset(AdminUsersSessionResetRequest.builder().userId(userId).build());
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void makeOrgChannel() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            ConversationsCreateResponse creation = slack.methods(teamAdminUserToken).conversationsCreate(r ->
                    r.name("test-" + System.currentTimeMillis()));
            Conversation channel = creation.getChannel();
            String originalTeamId = channel.getSharedTeamIds().get(0);
            String channelId = channel.getId();

            // org-channel
            AdminConversationsSetTeamsResponse orgChannelResp = slack.methods(orgAdminUserToken).adminConversationsSetTeams(r -> r
                    .teamId(originalTeamId)
                    .channelId(channelId)
                    .orgChannel(true));
            assertThat(orgChannelResp.getError(), is(nullValue()));
        }
    }

    @Ignore // Doesn't work as of Jan 3, 2020
    @Test
    public void changeSharedChannels() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null && sharedChannelId != null) {
            ConversationsInfoResponse channelInfo = slack.methods(teamAdminUserToken).conversationsInfo(r -> r.channel(sharedChannelId));
            assertThat(channelInfo.getError(), is(nullValue()));
            Conversation channel = channelInfo.getChannel();
            String originalTeamId = channel.getSharedTeamIds().get(0);
            String channelId = channel.getId();

            List<String> newTeams = channel.getSharedTeamIds().stream()
                    .limit(channel.getSharedTeamIds().size() - 1)
                    .collect(Collectors.toList());

            AdminConversationsSetTeamsResponse shareResp = slack.methods(orgAdminUserToken).adminConversationsSetTeams(r -> r
                    .teamId(originalTeamId)
                    .channelId(channelId)
                    .targetTeamIds(newTeams));
            assertThat(shareResp.getError(), is(nullValue()));

            AdminConversationsSetTeamsResponse revertResp = slack.methods(orgAdminUserToken).adminConversationsSetTeams(r -> r
                    .teamId(originalTeamId)
                    .channelId(channelId)
                    .targetTeamIds(channel.getSharedTeamIds()));
            assertThat(revertResp.getError(), is(nullValue()));
        }
    }

    @Test
    public void apps_approvedList() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsApprovedListResponse errorResponse = slack.methods(orgAdminUserToken).adminAppsApprovedList(r -> r
                            .limit(1000)
                    /*.teamId(adminAppsTeamId)*/);
            assertThat(errorResponse.getError(), is("team_not_found"));

            AdminAppsApprovedListResponse response = slack.methods(orgAdminUserToken).adminAppsApprovedList(r -> r
                    .limit(1000)
                    .teamId(teamId));
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void apps_restrictedList() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsRestrictedListResponse errorResponse = slack.methods(orgAdminUserToken).adminAppsRestrictedList(r -> r
                            .limit(1000)
                    /*.teamId(adminAppsTeamId)*/);
            assertThat(errorResponse.getError(), is("team_not_found"));

            AdminAppsRestrictedListResponse response = slack.methods(orgAdminUserToken).adminAppsRestrictedList(r -> r
                    .limit(1000)
                    .teamId(teamId));
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void appsRequests() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsRequestsListResponse list = slack.methods(orgAdminUserToken).adminAppsRequestsList(r -> r
                    .limit(1000)
                    .teamId(teamId));
            assertThat(list.getError(), is(nullValue()));

            if (list.getAppRequests().size() >= 2) {
                AppRequest toBeApproved = list.getAppRequests().get(0);

                AdminAppsApproveResponse approvalError = slack.methods(orgAdminUserToken).adminAppsApprove(req -> req
                        .appId(toBeApproved.getId())
                        .teamId(teamId));
                assertThat(approvalError.getError(), is(notNullValue()));

                AdminAppsApproveResponse approval = slack.methods(orgAdminUserToken).adminAppsApprove(req -> req
                        .appId(toBeApproved.getApp().getId())
                        .teamId(teamId));
                assertThat(approval.getError(), is(nullValue()));

                AppRequest toBeRestricted = list.getAppRequests().get(1);

                AdminAppsRestrictResponse restrictionError = slack.methods(orgAdminUserToken).adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getId())
                        .teamId(teamId));
                assertThat(restrictionError.getError(), is(notNullValue()));

                AdminAppsRestrictResponse restriction = slack.methods(orgAdminUserToken).adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getApp().getId())
                        .teamId(teamId));
                assertThat(restriction.getError(), is(nullValue()));
            }
        }
    }

    @Test
    public void inviteRequests() throws Exception {
        if (orgAdminUserToken != null) {
            AdminInviteRequestsApproveResponse approval = slack.methods(orgAdminUserToken).adminInviteRequestsApprove(r -> r
                    .teamId(teamId)
                    .inviteRequestId("I12345678"));
            assertThat(approval.getError(), is("invalid_request"));

            AdminInviteRequestsDenyResponse denial = slack.methods(orgAdminUserToken).adminInviteRequestsDeny(r -> r
                    .teamId(teamId)
                    .inviteRequestId("I12345678"));
            assertThat(denial.getError(), is("invalid_request"));

            AdminInviteRequestsListResponse list = slack.methods(orgAdminUserToken).adminInviteRequestsList(r -> r
                    .limit(1000)
                    .teamId(teamId));
            assertThat(list.getError(), is(nullValue()));

            AdminInviteRequestsApprovedListResponse approvedList = slack.methods(orgAdminUserToken).adminInviteRequestsApprovedList(r -> r
                    .limit(1000)
                    .teamId(teamId));
            assertThat(approvedList.getError(), is(nullValue()));

            AdminInviteRequestsDeniedListResponse deniedList = slack.methods(orgAdminUserToken).adminInviteRequestsDeniedList(r -> r
                    .limit(1000)
                    .teamId(teamId));
            assertThat(deniedList.getError(), is(nullValue()));
        }
    }

    @Test
    public void teams_list() throws Exception {
        if (orgAdminUserToken != null) {
            AdminTeamsListResponse teams = slack.methods(orgAdminUserToken).adminTeamsList(r -> r.limit(100));
            assertThat(teams.getError(), is(nullValue()));

            AdminTeamsAdminsListResponse admins = slack.methods(orgAdminUserToken).adminTeamsAdminsList(r -> r
                    .limit(100)
                    .teamId(teamId));
            assertThat(admins.getError(), is(nullValue()));

            AdminTeamsOwnersListResponse owners = slack.methods(orgAdminUserToken).adminTeamsOwnersList(r -> r
                    .limit(100)
                    .teamId(teamId));
            assertThat(owners.getError(), is(nullValue()));
        }
    }


    @Ignore // til the APIs stably work with these tests
    @Test
    public void teams_creation_and_users() throws Exception {
        if (orgAdminUserToken != null) {
            String teamUniqueName = "jslack-" + System.currentTimeMillis();
            AdminTeamsCreateResponse creation = slack.methods(orgAdminUserToken).adminTeamsCreate(r -> r
                    .teamDomain(teamUniqueName)
                    .teamName(teamUniqueName)
                    .teamDescription("Something great for you")
                    .teamDiscoverability("closed")
            );
            assertThat(creation.getError(), is(nullValue()));

            if (teamAdminUserToken != null && orgAdminUserToken != null) {
                String teamId = creation.getTeam();
                List<String> members = slack.methods(teamAdminUserToken).channelsList(r -> r.limit(1000)).getChannels().get(0).getMembers();
                String userId = findUserId(members);
                assertThat(userId, is(notNullValue()));

                AdminUsersAssignRequest assignReq = AdminUsersAssignRequest.builder().teamId(teamId).userId(userId).build();
                AdminUsersAssignResponse assignment = slack.methods(orgAdminUserToken).adminUsersAssign(assignReq);
                assertThat(assignment.getError(), is("user_already_team_member")); // TODO: fix this

                List<String> channelIds = slack.methods(teamAdminUserToken)
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
                AdminUsersInviteResponse invitation = slack.methods(orgAdminUserToken).adminUsersInvite(inviteReq);
                // TODO: fix this
                //assertThat(invitation.getError(), is(nullValue()));

                AdminUsersRemoveRequest removeReq = AdminUsersRemoveRequest.builder().teamId(teamId).userId(userId).build();
                AdminUsersRemoveResponse removal = slack.methods(orgAdminUserToken).adminUsersRemove(removeReq);
                // TODO: fix this
                //assertThat(removal.getError(), is(nullValue()));

                AdminUsersSetAdminResponse setAdmin = slack.methods(orgAdminUserToken)
                        .adminUsersSetAdmin(r -> r.teamId(teamId).userId(userId));
                // TODO: fix this
                //assertThat(setAdmin.getError(), is(nullValue()));
                AdminUsersSetOwnerResponse setOwner = slack.methods(orgAdminUserToken)
                        .adminUsersSetOwner(r -> r.teamId(teamId).userId(userId));
                // TODO: fix this
                //assertThat(setOwner.getError(), is(nullValue()));
                AdminUsersSetRegularResponse setRegular = slack.methods(orgAdminUserToken)
                        .adminUsersSetRegular(r -> r.teamId(teamId).userId(userId));
                // TODO: fix this
                //assertThat(setRegular.getError(), is(nullValue()));
            }
        }
    }

    @Test
    public void teams_settings() throws IOException, SlackApiException {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String teamId = this.teamId;

            AdminTeamsSettingsInfoResponse info = slack.methods(orgAdminUserToken).adminTeamsSettingsInfo(r -> r.teamId(teamId));
            assertThat(info.getError(), is(nullValue()));

            List<String> channelIds = slack.methods(teamAdminUserToken).conversationsList(r -> r
                    .excludeArchived(true)
                    .types(Arrays.asList(ConversationType.PUBLIC_CHANNEL))
                    .limit(2)
            ).getChannels().stream().map(c -> c.getId()).collect(Collectors.toList());

            AdminTeamsSettingsSetDefaultChannelsResponse defaultChannelsFailure = slack.methods(orgAdminUserToken)
                    .adminTeamsSettingsSetDefaultChannels(r -> r.teamId(teamId).channelIds(Collections.emptyList()));
            assertThat(defaultChannelsFailure.getError(), is("invalid_arguments"));
            assertThat(defaultChannelsFailure.getResponseMetadata().getMessages().toString(),
                    is("[[ERROR] input must match regex pattern: ^[C][A-Z0-9]{2,}$ [json-pointer:/channel_ids/0]]"));

            AdminTeamsSettingsSetDefaultChannelsResponse defaultChannels = slack.methods(orgAdminUserToken)
                    .adminTeamsSettingsSetDefaultChannels(r -> r.teamId(teamId).channelIds(channelIds));
            assertThat(defaultChannels.getError(), is(nullValue()));

            AdminTeamsSettingsSetDiscoverabilityResponse discoverabilityFailure = slack.methods(orgAdminUserToken)
                    .adminTeamsSettingsSetDiscoverability(r -> r.teamId(teamId).discoverability("invitation_only??"));
            assertThat(discoverabilityFailure.getError(), is("discoverability_setting_invalid"));

            AdminTeamsSettingsSetDiscoverabilityResponse discoverability = slack.methods(orgAdminUserToken)
                    .adminTeamsSettingsSetDiscoverability(r -> r.teamId(teamId).discoverability("invite_only"));
            assertThat(discoverability.getError(), is(nullValue()));

            AdminTeamsSettingsSetDescriptionResponse setDescription = slack.methods(orgAdminUserToken)
                    .adminTeamsSettingsSetDescription(r -> r.teamId(this.teamId).description("something great"));
            assertThat(setDescription.getError(), is(nullValue()));

            AdminTeamsSettingsSetNameResponse setName = slack.methods(orgAdminUserToken)
                    .adminTeamsSettingsSetName(r -> r.teamId(teamId).name("Kaz's Awesome Engineering Team"));
            assertThat(setName.getError(), is(nullValue()));

            try {
                AdminTeamsSettingsSetIconResponse setIcon = slack.methods(orgAdminUserToken)
                        .adminTeamsSettingsSetIcon(r -> r/*.teamId(teamId)*/.imageUrl("https://avatars.slack-edge.com/2019-05-24/634650041250_0c70b65cdfc88ac9ef96_192.jpg"));
                assertThat(setIcon.getError(), is("invalid_arguments"));
                assertThat(setIcon.getResponseMetadata().getMessages().toString(), is("[[ERROR] missing required field: team_id]"));
            } catch (SocketTimeoutException e) {
                log.warn("timed out", e);
            }

            try {
                AdminTeamsSettingsSetIconResponse setIcon = slack.methods(orgAdminUserToken)
                        .adminTeamsSettingsSetIcon(r -> r.teamId(teamId).imageUrl("https://avatars.slack-edge.com/2019-05-24/634650041250_0c70b65cdfc88ac9ef96_192.jpg"));
                assertThat(setIcon.getError(), is(nullValue()));
            } catch (SocketTimeoutException e) {
                log.warn("timed out", e);
            }
        }
    }

    @Test
    public void users() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String teamId = this.teamId;

            AdminUsersListResponse.User user = null;
            String nextCursor = "dummy";
            while (!nextCursor.equals("")) {
                final String cursor = nextCursor.equals("dummy") ? null : nextCursor;
                AdminUsersListResponse users = slack.methods(orgAdminUserToken).adminUsersList(r -> r
                        .limit(100)
                        .cursor(cursor)
                        .teamId(teamId));
                assertThat(users.getError(), is(nullValue()));

                Optional<AdminUsersListResponse.User> maybeUser = users.getUsers().stream()
                        .filter(u -> u.isRestricted() || u.isUltraRestricted()).findFirst();
                if (maybeUser.isPresent()) {
                    user = maybeUser.get();
                    break;
                }
                nextCursor = users.getResponseMetadata().getNextCursor();
            }
            assertThat(user, is(notNullValue()));
            final String guestUserId = user.getId();
            long defaultExpirationTs = ZonedDateTime.parse("2037-12-31T00:00:00+09:00").toEpochSecond() / 1000;
            // same timestamp results in "failed_to_validate_expiration" error
            final Long expirationTs = user.getExpirationTs() != null ? user.getExpirationTs() + 1 : defaultExpirationTs;

            AdminUsersSetExpirationResponse response = slack.methods(orgAdminUserToken).adminUsersSetExpiration(r -> r
                    .teamId(teamId)
                    .userId(guestUserId)
                    .expirationTs(expirationTs)
            );
            assertThat(response.getError(), is(nullValue()));
        }
    }


    private String findUserId(List<String> idsToSkip) throws IOException, SlackApiException {
        String userId = null;
        UsersListResponse usersListResponse = slack.methods(teamAdminUserToken).usersList(req -> req);
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
