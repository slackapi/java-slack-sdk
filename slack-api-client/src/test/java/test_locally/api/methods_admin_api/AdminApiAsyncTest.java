package test_locally.api.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsConfig;
import com.slack.api.methods.MethodsCustomRateLimitResolver;
import com.slack.api.model.admin.AppConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class AdminApiAsyncTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
        MethodsConfig methodsConfig = new MethodsConfig();
        methodsConfig.setCustomRateLimitResolver(new MethodsCustomRateLimitResolver() {
            @Override
            public Optional<Integer> getCustomAllowedRequestsPerMinute(String teamId, String methodName) {
                return Optional.of(50);
            }

            @Override
            public Optional<Integer> getCustomAllowedRequestsForChatPostMessagePerMinute(String teamId, String channel) {
                return Optional.empty();
            }

            @Override
            public Optional<Integer> getCustomAllowedRequestsForAssistantThreadsSetStatusPerMinute(String teamId, String channel) {
                return Optional.empty();
            }
        });
        config.setMethodsConfig(methodsConfig);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void adminApps() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminAppsApprove(r -> r.appId("A123").teamId("T123").requestId("test"))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsRestrict(r -> r.appId("A123").teamId("T123").requestId("test"))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsApprovedList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsRequestsList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsRequestsCancel(r -> r.requestId("XXX").enterpriseId("E123").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsRestrictedList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsUninstall(r -> r.appId("A111").enterpriseId("E111").teamIds(Arrays.asList("T123")))
                .get().isOk(), is(true));
        assertThat(methods.adminAppsActivitiesList(r -> r.appId("A111")).get().isOk(), is(true));
        assertThat(methods.adminAppsConfigLookup(r -> r.appIds(Arrays.asList("A111"))).get().isOk(), is(true));
        assertThat(methods.adminAppsConfigSet(r -> r.appId("A111").domainRestrictions(new AppConfig.DomainRestrictions())).get().isOk(), is(true));
    }

    @Test
    public void adminAuthPolicy() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminAuthPolicyAssignEntities(r -> r).get().isOk(), is(true));
        assertThat(methods.adminAuthPolicyGetEntities(r -> r).get().isOk(), is(true));
        assertThat(methods.adminAuthPolicyRemoveEntities(r -> r).get().isOk(), is(true));
    }

    @Test
    public void adminBarriers() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminBarriersCreate(r -> r).get().isOk(), is(true));
        assertThat(methods.adminBarriersDelete(r -> r).get().isOk(), is(true));
        assertThat(methods.adminBarriersList(r -> r).get().isOk(), is(true));
        assertThat(methods.adminBarriersUpdate(r -> r).get().isOk(), is(true));
    }

    @Test
    public void adminConversations() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminConversationsSetTeams(r ->
                r.channelId("C123").teamId("T123").targetTeamIds(Arrays.asList("T123", "T234")))
                .get().isOk(), is(true));

        assertThat(methods.adminConversationsArchive(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsConvertToPrivate(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsCreate(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsDelete(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsDisconnectShared(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsEkmListOriginalConnectedChannelInfo(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsGetConversationPrefs(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsGetTeams(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsInvite(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsRename(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsSearch(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsSetConversationPrefs(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsSetTeams(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsUnarchive(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsBulkMove(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsBulkDelete(r -> r).get().isOk(), is(true));
        assertThat(methods.adminConversationsBulkArchive(r -> r).get().isOk(), is(true));
    }

    @Test
    public void adminConversationsWhitelist() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminConversationsWhitelistAdd(r -> r.channelId("C123").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminConversationsWhitelistRemove(r -> r.channelId("C123").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminConversationsWhitelistListGroupsLinkedToChannel(r -> r.channelId("C123").teamId("T123"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminConversationsCustomRetention() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminConversationsGetCustomRetention(r -> r.channelId("C123"))
                .get().isOk(), is(true));
        assertThat(methods.adminConversationsSetCustomRetention(r -> r.channelId("C123").durationDays(10))
                .get().isOk(), is(true));
        assertThat(methods.adminConversationsRemoveCustomRetention(r -> r.channelId("C123"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminConversationsRestrictAccess() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminConversationsRestrictAccessAddGroup(r -> r.channelId("C123").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminConversationsRestrictAccessRemoveGroup(r -> r.channelId("C123").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminConversationsRestrictAccessListGroups(r -> r.channelId("C123").teamId("T123"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminEmoji() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminEmojiAdd(r -> r.name("smile").url("https://www.example.com"))
                .get().isOk(), is(true));
        assertThat(methods.adminEmojiAddAlias(r -> r.name("happy").aliasFor("smiley"))
                .get().isOk(), is(true));
        assertThat(methods.adminEmojiList(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(methods.adminEmojiRemove(r -> r.name("smile"))
                .get().isOk(), is(true));
        assertThat(methods.adminEmojiRename(r -> r.name("smile").newName("smile2"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminFunctions() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminFunctionsList(r -> r.appIds(Arrays.asList(""))).get().isOk(), is(true));
        assertThat(methods.adminFunctionsPermissionsSet(r -> r.userIds(Arrays.asList(""))).get().isOk(), is(true));
        assertThat(methods.adminFunctionsPermissionsLookup(r -> r.functionIds(Arrays.asList(""))).get().isOk(), is(true));
    }

    @Test
    public void adminInviteRequests() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminInviteRequestsApprove(r -> r.inviteRequestId("id").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminInviteRequestsDeny(r -> r.inviteRequestId("id").teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminInviteRequestsList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminInviteRequestsApprovedList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminInviteRequestsDeniedList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminRoles() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminRolesAddAssignments(r -> r)
                .get().isOk(), is(true));
        assertThat(methods.adminRolesListAssignments(r -> r)
                .get().isOk(), is(true));
        assertThat(methods.adminRolesRemoveAssignments(r -> r)
                .get().isOk(), is(true));
    }

    @Test
    public void adminTeams() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminTeamsAdminsList(r -> r.teamId("T123").limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsCreate(r ->
                r.teamName("foo").teamDescription("this is the team").teamDomain("awesome-domain"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsList(r -> r.limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsOwnersList(r -> r.teamId("T123").limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsSettingsInfo(r -> r.teamId("T123"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetDefaultChannels(r -> r.teamId("T123").channelIds(Arrays.asList("T123")))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetDescription(r -> r.teamId("T123").description("foo"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetDiscoverability(r -> r.teamId("T123").discoverability("foo"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetIcon(r -> r.teamId("T123").imageUrl("https://www.example.com"))
                .get().isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetName(r -> r.teamId("T123").name("new name"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminUsers() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminUsersAssign(r -> r.teamId("T123").userId("U123").isRestricted(false))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersInvite(r ->
                r.teamId("T123").channelIds(Arrays.asList("C123")).email("who@example.com"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersList(r -> r.teamId("T123").limit(1).cursor("xxx"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersRemove(r -> r.teamId("T123").userId("U123"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSetAdmin(r -> r.teamId("T123").userId("U123"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSetOwner(r -> r.teamId("T123").userId("U123"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSetRegular(r -> r.teamId("T123").userId("U123"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSetExpiration(r -> r.teamId("T123").userId("U123").expirationTs(12345L))
                .get().isOk(), is(true));

        assertThat(methods.adminUsersSessionReset(r -> r.userId("U123").mobileOnly(false))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSessionList(r -> r.cursor("XXXX").limit(10).teamId("T123").userId("U123"))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSessionGetSettings(r -> r.userIds(Arrays.asList("U123")))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSessionSetSettings(r -> r.userIds(Arrays.asList("U123")))
                .get().isOk(), is(true));
        assertThat(methods.adminUsersSessionClearSettings(r -> r.userIds(Arrays.asList("U123")))
                .get().isOk(), is(true));

        assertThat(methods.adminUsersUnsupportedVersionsExport(r -> r).get().isOk(), is(true));
    }

    @Test
    public void adminUsergroups() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminUsergroupsAddChannels(r -> r.teamId("T123").usergroupId("S123").channelIds(Arrays.asList("C123", "C234")))
                .get().isOk(), is(true));
        assertThat(methods.adminUsergroupsListChannels(r -> r.teamId("T123").usergroupId("S123").includeNumMembers(true))
                .get().isOk(), is(true));
        assertThat(methods.adminUsergroupsRemoveChannels(r -> r.usergroupId("S123").channelIds(Arrays.asList("C123", "C234")))
                .get().isOk(), is(true));
        assertThat(methods.adminUsergroupsAddTeams(r -> r.usergroupId("S123").teamIds(Arrays.asList("T123", "T234")))
                .get().isOk(), is(true));
    }

    @Test
    public void adminWorkflows() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminWorkflowsSearch(r -> r.collaboratorIds(Arrays.asList(""))).get().isOk(), is(true));
        assertThat(methods.adminWorkflowsCollaboratorsAdd(r -> r.workflowIds(Arrays.asList("")).collaboratorIds(Arrays.asList(""))).get().isOk(), is(true));
        assertThat(methods.adminWorkflowsCollaboratorsRemove(r -> r.workflowIds(Arrays.asList("")).collaboratorIds(Arrays.asList(""))).get().isOk(), is(true));
        assertThat(methods.adminWorkflowsPermissionsLookup(r -> r.workflowIds(Arrays.asList(""))).get().isOk(), is(true));
        assertThat(methods.adminWorkflowsUnpublish(r -> r.workflowIds(Arrays.asList(""))).get().isOk(), is(true));
    }
}
