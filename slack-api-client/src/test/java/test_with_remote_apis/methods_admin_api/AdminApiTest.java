package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.admin.users.AdminUsersAssignRequest;
import com.slack.api.methods.request.admin.users.AdminUsersInviteRequest;
import com.slack.api.methods.request.admin.users.AdminUsersRemoveRequest;
import com.slack.api.methods.request.admin.users.AdminUsersSessionResetRequest;
import com.slack.api.methods.response.admin.apps.*;
import com.slack.api.methods.response.admin.conversations.AdminConversationsSetTeamsResponse;
import com.slack.api.methods.response.admin.emoji.*;
import com.slack.api.methods.response.admin.invite_requests.*;
import com.slack.api.methods.response.admin.teams.AdminTeamsAdminsListResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsCreateResponse;
import com.slack.api.methods.response.admin.teams.AdminTeamsListResponse;
import com.slack.api.methods.response.admin.teams.owners.AdminTeamsOwnersListResponse;
import com.slack.api.methods.response.admin.teams.settings.*;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsListChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsRemoveChannelsResponse;
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
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApiTest {

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
    static String idpUsergroupId = System.getenv(Constants.SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID);
    static String sharedChannelId = System.getenv(Constants.SLACK_SDK_TEST_GRID_SHARED_CHANNEL_ID);

    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void usersSessionReset() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String userId = findUserId(Collections.emptyList());
            assertThat(userId, is(notNullValue()));
            AdminUsersSessionResetResponse response = methodsAsync
                    .adminUsersSessionReset(AdminUsersSessionResetRequest.builder().userId(userId).build())
                    .get();
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
            AdminConversationsSetTeamsResponse orgChannelResp = methodsAsync.adminConversationsSetTeams(r -> r
                    .teamId(originalTeamId)
                    .channelId(channelId)
                    .orgChannel(true))
                    .get();
            assertThat(orgChannelResp.getError(), is(nullValue()));
        }
    }

    @Ignore // TODO: Fix this test to be more stable
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

            AdminConversationsSetTeamsResponse shareResp = methodsAsync.adminConversationsSetTeams(r -> r
                    .teamId(originalTeamId)
                    .channelId(channelId)
                    .targetTeamIds(newTeams))
                    .get();
            assertThat(shareResp.getError(), is(nullValue()));

            AdminConversationsSetTeamsResponse revertResp = methodsAsync.adminConversationsSetTeams(r -> r
                    .teamId(originalTeamId)
                    .channelId(channelId)
                    .targetTeamIds(channel.getSharedTeamIds()))
                    .get();
            assertThat(revertResp.getError(), is(nullValue()));
        }
    }

    @Ignore
    @Test
    public void apps_approvedList() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsApprovedListResponse errorResponse = methodsAsync.adminAppsApprovedList(r -> r
                            .limit(1000)
                    /*.teamId(adminAppsTeamId)*/)
                    .get();
            assertThat(errorResponse.getError(), is("team_not_found"));

            AdminAppsApprovedListResponse response = methodsAsync.adminAppsApprovedList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Ignore
    @Test
    public void apps_restrictedList() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsRestrictedListResponse errorResponse = methodsAsync.adminAppsRestrictedList(r -> r
                            .limit(1000)
                    /*.teamId(adminAppsTeamId)*/)
                    .get();
            assertThat(errorResponse.getError(), is("team_not_found"));

            AdminAppsRestrictedListResponse response = methodsAsync.adminAppsRestrictedList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Ignore
    @Test
    public void appsRequests() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAppsRequestsListResponse list = methodsAsync.adminAppsRequestsList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(list.getError(), is(nullValue()));

            if (list.getAppRequests().size() >= 2) {
                AppRequest toBeApproved = list.getAppRequests().get(0);

                AdminAppsApproveResponse approvalError = methodsAsync.adminAppsApprove(req -> req
                        .appId(toBeApproved.getId())
                        .teamId(teamId))
                        .get();
                assertThat(approvalError.getError(), is(notNullValue()));

                AdminAppsApproveResponse approval = methodsAsync.adminAppsApprove(req -> req
                        .appId(toBeApproved.getApp().getId())
                        .teamId(teamId))
                        .get();
                assertThat(approval.getError(), is(nullValue()));

                AppRequest toBeRestricted = list.getAppRequests().get(1);

                AdminAppsRestrictResponse restrictionError = methodsAsync.adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getId())
                        .teamId(teamId))
                        .get();
                assertThat(restrictionError.getError(), is(notNullValue()));

                AdminAppsRestrictResponse restriction = methodsAsync.adminAppsRestrict(req -> req
                        .appId(toBeRestricted.getApp().getId())
                        .teamId(teamId))
                        .get();
                assertThat(restriction.getError(), is(nullValue()));
            }
        }
    }

    @Test
    public void inviteRequests() throws Exception {
        if (orgAdminUserToken != null) {
            AdminInviteRequestsApproveResponse approval = methodsAsync.adminInviteRequestsApprove(r -> r
                    .teamId(teamId)
                    .inviteRequestId("I12345678"))
                    .get();
            assertThat(approval.getError(), is("invalid_request"));

            AdminInviteRequestsDenyResponse denial = methodsAsync.adminInviteRequestsDeny(r -> r
                    .teamId(teamId)
                    .inviteRequestId("I12345678"))
                    .get();
            assertThat(denial.getError(), is("invalid_request"));

            AdminInviteRequestsListResponse list = methodsAsync.adminInviteRequestsList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(list.getError(), is(nullValue()));

            AdminInviteRequestsApprovedListResponse approvedList = methodsAsync.adminInviteRequestsApprovedList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(approvedList.getError(), is(nullValue()));

            AdminInviteRequestsDeniedListResponse deniedList = methodsAsync.adminInviteRequestsDeniedList(r -> r
                    .limit(1000)
                    .teamId(teamId))
                    .get();
            assertThat(deniedList.getError(), is(nullValue()));
        }
    }

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
            assertThat(creation.getError(), is(nullValue()));

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

    @Test
    public void usergroups() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null && idpUsergroupId != null) {
            List<String> channelIds = slack.methods(teamAdminUserToken).conversationsList(r -> r
                    .excludeArchived(true)
                    .limit(100)
            ).getChannels().stream()
                    .filter(c -> c.getName().equals("general")).map(c -> c.getId())
                    .collect(Collectors.toList());

            MethodsClient orgAdminClient = slack.methods(orgAdminUserToken);
            AdminUsergroupsListChannelsResponse listChannels = orgAdminClient.adminUsergroupsListChannels(r -> r
                    .teamId(teamId).usergroupId(idpUsergroupId));
            assertThat(listChannels.getError(), is(nullValue()));

            AdminUsergroupsAddChannelsResponse addChannels = orgAdminClient.adminUsergroupsAddChannels(r -> r
                    .teamId(teamId).usergroupId(idpUsergroupId).channelIds(channelIds));
            assertThat(addChannels.getError(), is(nullValue()));

            AdminUsergroupsRemoveChannelsResponse removeChannels = orgAdminClient.adminUsergroupsRemoveChannels(r -> r
                    .usergroupId(idpUsergroupId).channelIds(channelIds));
            assertThat(removeChannels.getError(), is(nullValue()));
        }
    }

    @Test
    public void users() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null) {
            String teamId = this.teamId;
            AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

            AdminUsersListResponse.User user = null;
            String nextCursor = "dummy";
            while (!nextCursor.equals("")) {
                final String cursor = nextCursor.equals("dummy") ? null : nextCursor;
                AdminUsersListResponse users = methodsAsync.adminUsersList(r -> r
                        .limit(100)
                        .cursor(cursor)
                        .teamId(teamId)).get();
                assertThat(users.getError(), is(nullValue()));

                Optional<AdminUsersListResponse.User> maybeUser = users.getUsers().stream()
                        .filter(u -> u.isRestricted() || u.isUltraRestricted()).findFirst();
                if (maybeUser.isPresent()) {
                    user = maybeUser.get();
                    break;
                }
                nextCursor = users.getResponseMetadata().getNextCursor();
            }
            assertThat("Create a guest user for this test", user, is(notNullValue()));
            final String guestUserId = user.getId();
            long defaultExpirationTs = ZonedDateTime.now().toEpochSecond() / 1000;
            // same timestamp results in "failed_to_validate_expiration" error
            final Long expirationTs = user.getExpirationTs() != null && user.getExpirationTs() != 0 ?
                    user.getExpirationTs() + 1 : defaultExpirationTs + 3600;

            AdminUsersSetExpirationResponse response = methodsAsync.adminUsersSetExpiration(r -> r
                    .teamId(teamId)
                    .userId(guestUserId)
                    .expirationTs(expirationTs)
            ).get();
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void emoji() throws Exception {
        if (orgAdminUserToken != null) {
            CompletableFuture<AdminEmojiListResponse> list = methodsAsync.adminEmojiList(r -> r.limit(100));

            Thread.sleep(3000);

            CompletableFuture<AdminEmojiAddResponse> creationError = methodsAsync.adminEmojiAdd(r -> r.name("test"));
            assertThat(creationError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            String name = "java-" + System.currentTimeMillis();
            String url = "https://emoji.slack-edge.com/T03E94MJU/java/624937af2b22523e.png";

            CompletableFuture<AdminEmojiAddResponse> creation = methodsAsync.adminEmojiAdd(r -> r.name(name).url(url));
            assertThat(creation.get().getError(), is(nullValue()));

            CompletableFuture<AdminEmojiAddAliasResponse> aliasCreationError = methodsAsync.adminEmojiAddAlias(r -> r.name(name));
            assertThat(aliasCreationError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            CompletableFuture<AdminEmojiAddAliasResponse> aliasCreation = methodsAsync.adminEmojiAddAlias(r -> r
                    .name(name + "-alias").aliasFor(name));
            assertThat(aliasCreation.get().getError(), is(nullValue()));

            CompletableFuture<AdminEmojiRenameResponse> renamingError = methodsAsync.adminEmojiRename(r -> r.name(name));
            assertThat(renamingError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            CompletableFuture<AdminEmojiRenameResponse> renaming = methodsAsync.adminEmojiRename(r -> r.name(name).newName(name + "-2"));
            assertThat(renaming.get().getError(), is(nullValue()));

            CompletableFuture<AdminEmojiRemoveResponse> removalError = methodsAsync.adminEmojiRemove(r -> r);
            assertThat(removalError.get().getError(), is("invalid_arguments"));

            Thread.sleep(10000);

            CompletableFuture<AdminEmojiRemoveResponse> removal = methodsAsync.adminEmojiRemove(r -> r.name(name + "-2"));
            assertThat(removal.get().getError(), is(nullValue()));
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
