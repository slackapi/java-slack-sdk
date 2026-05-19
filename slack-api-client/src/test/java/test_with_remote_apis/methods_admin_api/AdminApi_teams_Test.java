package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.request.admin.users.AdminUsersAssignRequest;
import com.slack.api.methods.request.admin.users.AdminUsersInviteRequest;
import com.slack.api.methods.request.admin.users.AdminUsersRemoveRequest;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.users.*;
import com.slack.api.methods.response.users.UsersListResponse;
import com.slack.api.model.ConversationType;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_teams_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String email = System.getenv(Constants.SLACK_SDK_TEST_EMAIL_ADDRESS);
    static String teamAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);

    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void teams_list() throws Exception {
        if (orgAdminUserToken != null) {
            AdminTeamsListResponse teams = methodsAsync.adminTeamsList(r -> r.limit(100)).get();
            assertThat(teams.getError(), is(nullValue()));

            AdminTeamsAdminsListResponse admins = methodsAsync.adminTeamsAdminsList(r -> r
                    .limit(100)
                    .teamId(teamId))
                    .get();
            assertThat(admins.getError(), is(nullValue()));

            AdminTeamsOwnersListResponse owners = methodsAsync.adminTeamsOwnersList(r -> r
                    .limit(100)
                    .teamId(teamId))
                    .get();
            assertThat(owners.getError(), is(nullValue()));
        }
    }

    @Ignore // til the APIs stably work with these tests
    @Test
    public void teams_creation_and_users() throws Exception {
        if (orgAdminUserToken != null) {
            String teamUniqueName = "java-slack-sdk-" + System.currentTimeMillis();
            AdminTeamsCreateResponse creation = methodsAsync.adminTeamsCreate(r -> r
                    .teamDomain(teamUniqueName)
                    .teamName(teamUniqueName)
                    .teamDescription("Something great for you")
                    .teamDiscoverability("closed")
            ).get();
            assertThat(creation.getError(), is(nullValue())); // "bad_url"

            if (teamAdminUserToken != null && orgAdminUserToken != null) {
                String teamId = creation.getTeam();
                List<String> members = slack.methods(teamAdminUserToken).channelsList(r -> r.limit(1000)).getChannels().get(0).getMembers();
                String userId = findUserId(members);
                assertThat(userId, is(notNullValue()));

                AdminUsersAssignRequest assignReq = AdminUsersAssignRequest.builder().teamId(teamId).userId(userId).build();
                AdminUsersAssignResponse assignment = methodsAsync.adminUsersAssign(assignReq).get();
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
                AdminUsersInviteResponse invitation = methodsAsync.adminUsersInvite(inviteReq).get();
                // TODO: fix this
                //assertThat(invitation.getError(), is(nullValue()));

                AdminUsersRemoveRequest removeReq = AdminUsersRemoveRequest.builder().teamId(teamId).userId(userId).build();
                AdminUsersRemoveResponse removal = methodsAsync.adminUsersRemove(removeReq).get();
                // TODO: fix this
                //assertThat(removal.getError(), is(nullValue()));

                AdminUsersSetAdminResponse setAdmin = methodsAsync.adminUsersSetAdmin(r -> r.teamId(teamId).userId(userId)).get();
                // TODO: fix this
                //assertThat(setAdmin.getError(), is(nullValue()));
                AdminUsersSetOwnerResponse setOwner = methodsAsync
                        .adminUsersSetOwner(r -> r.teamId(teamId).userId(userId)).get();
                // TODO: fix this
                //assertThat(setOwner.getError(), is(nullValue()));
                AdminUsersSetRegularResponse setRegular = methodsAsync
                        .adminUsersSetRegular(r -> r.teamId(teamId).userId(userId)).get();
                // TODO: fix this
                //assertThat(setRegular.getError(), is(nullValue()));
            }
        }
    }

    @Test
    public void teams_settings() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String teamId = this.teamId;

            AdminTeamsSettingsInfoResponse info = methodsAsync.adminTeamsSettingsInfo(r -> r.teamId(teamId)).get();
            assertThat(info.getError(), is(nullValue()));

            List<String> channelIds = slack.methods(teamAdminUserToken).conversationsList(r -> r
                    .excludeArchived(true)
                    .types(Arrays.asList(ConversationType.PUBLIC_CHANNEL))
                    .limit(2)
            ).getChannels().stream().map(c -> c.getId()).collect(Collectors.toList());

            AdminTeamsSettingsSetDefaultChannelsResponse defaultChannelsFailure = methodsAsync
                    .adminTeamsSettingsSetDefaultChannels(r -> r.teamId(teamId).channelIds(Collections.emptyList())).get();
            assertThat(defaultChannelsFailure.getError(), is("invalid_arguments"));
            assertThat(defaultChannelsFailure.getResponseMetadata().getMessages().toString(),
                    is("[[ERROR] input must match regex pattern: ^[C][A-Z0-9]{2,}$ [json-pointer:/channel_ids/0]]"));

            AdminTeamsSettingsSetDefaultChannelsResponse defaultChannels = methodsAsync
                    .adminTeamsSettingsSetDefaultChannels(r -> r.teamId(teamId).channelIds(channelIds)).get();
            assertThat(defaultChannels.getError(), is(nullValue()));

            AdminTeamsSettingsSetDiscoverabilityResponse discoverabilityFailure = methodsAsync
                    .adminTeamsSettingsSetDiscoverability(r -> r.teamId(teamId).discoverability("invitation_only??")).get();
            assertThat(discoverabilityFailure.getError(), is("discoverability_setting_invalid"));

            AdminTeamsSettingsSetDiscoverabilityResponse discoverability = methodsAsync
                    .adminTeamsSettingsSetDiscoverability(r -> r.teamId(teamId).discoverability("invite_only")).get();
            assertThat(discoverability.getError(), is(nullValue()));

            AdminTeamsSettingsSetDescriptionResponse setDescription = methodsAsync
                    .adminTeamsSettingsSetDescription(r -> r.teamId(this.teamId).description("something great")).get();
            assertThat(setDescription.getError(), is(nullValue()));

            AdminTeamsSettingsSetNameResponse setName = methodsAsync
                    .adminTeamsSettingsSetName(r -> r.teamId(teamId).name("Slack Java SDK Testing Workspace")).get();
            assertThat(setName.getError(), is(nullValue()));

            try {
                AdminTeamsSettingsSetIconResponse setIcon = methodsAsync
                        .adminTeamsSettingsSetIcon(r -> r/*.teamId(teamId)*/.imageUrl("https://avatars.slack-edge.com/2019-05-24/634650041250_0c70b65cdfc88ac9ef96_192.jpg")).get();
                assertThat(setIcon.getError(), is("invalid_arguments"));
                assertThat(setIcon.getResponseMetadata().getMessages().toString(), is("[[ERROR] missing required field: team_id]"));
            } catch (CompletionException | ExecutionException e) {
                log.warn("timed out", e);
            }

            try {
                AdminTeamsSettingsSetIconResponse setIcon = methodsAsync
                        .adminTeamsSettingsSetIcon(r -> r.teamId(teamId).imageUrl("https://avatars.slack-edge.com/2019-05-24/634650041250_0c70b65cdfc88ac9ef96_192.jpg")).get();
                assertThat(setIcon.getError(), is(nullValue()));
            } catch (CompletionException | ExecutionException e) {
                log.warn("timed out", e);
            }
        }
    }

    private String findUserId(List<String> idsToSkip) throws Exception {
        String userId = null;
        UsersListResponse usersListResponse = slack.methodsAsync(teamAdminUserToken).usersList(req -> req).get();
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
