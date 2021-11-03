package test_locally.api.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.admin.analytics.AdminAnalyticsGetFileResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class AdminApiTest {

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
    public void adminAnalytics() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        AdminAnalyticsGetFileResponse response = methods.adminAnalyticsGetFile(r -> r.date("2020-10-20").type("member"));
        assertThat(response.isOk(), is(true));
        assertThat(response.getFileStream(), is(nullValue()));
    }

    @Test
    public void adminAuthPolicy() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminAuthPolicyAssignEntities(r -> r
                .policyName("user_password").entityIds(Arrays.asList("U1"))
        ).isOk(), is(true));
        assertThat(methods.adminAuthPolicyGetEntities(r -> r
                .policyName("user_password")
        ).isOk(), is(true));
        assertThat(methods.adminAuthPolicyRemoveEntities(r -> r
                .policyName("user_password").entityIds(Arrays.asList("U1"))
        ).isOk(), is(true));
    }

    @Test
    public void adminBarriers() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminBarriersCreate(r -> r).isOk(), is(true));
        assertThat(methods.adminBarriersDelete(r -> r).isOk(), is(true));
        assertThat(methods.adminBarriersList(r -> r).isOk(), is(true));
        assertThat(methods.adminBarriersUpdate(r -> r).isOk(), is(true));
    }

    @Test
    public void adminApps() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminAppsApprove(r -> r.appId("A123").teamId("T123").requestId("test"))
                .isOk(), is(true));
        assertThat(methods.adminAppsRestrict(r -> r.appId("A123").teamId("T123").requestId("test"))
                .isOk(), is(true));
        assertThat(methods.adminAppsApprovedList(r -> r.teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminAppsRequestsList(r -> r.teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminAppsRestrictedList(r -> r.teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminAppsClearResolution(r -> r.appId("A123").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminAppsUninstall(r -> r.appId("A123").enterpriseId("E111").teamIds(Arrays.asList("T123")))
                .isOk(), is(true));
    }

    @Test
    public void adminConversations() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminConversationsSetTeams(r ->
                r.channelId("C123").teamId("T123").targetTeamIds(Arrays.asList("T123", "T234")))
                .isOk(), is(true));
        assertThat(methods.adminConversationsArchive(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsConvertToPrivate(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsCreate(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsDelete(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsDisconnectShared(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsEkmListOriginalConnectedChannelInfo(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsGetConversationPrefs(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsGetTeams(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsInvite(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsRename(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsSearch(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsSetConversationPrefs(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsSetTeams(r -> r).isOk(), is(true));
        assertThat(methods.adminConversationsUnarchive(r -> r).isOk(), is(true));
    }

    @Test
    public void adminConversationsWhitelist() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminConversationsWhitelistAdd(r -> r.channelId("C123").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminConversationsWhitelistRemove(r -> r.channelId("C123").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminConversationsWhitelistListGroupsLinkedToChannel(r -> r.channelId("C123").teamId("T123"))
                .isOk(), is(true));
    }

    @Test
    public void adminConversationsCustomRetention() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminConversationsGetCustomRetention(r -> r.channelId("C123"))
                .isOk(), is(true));
        assertThat(methods.adminConversationsRemoveCustomRetention(r -> r.channelId("C123"))
                .isOk(), is(true));
        assertThat(methods.adminConversationsSetCustomRetention(r -> r.channelId("C123").durationDays(365))
                .isOk(), is(true));
    }

    @Test
    public void adminConversationsRestrictAccess() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminConversationsRestrictAccessAddGroup(r -> r.channelId("C123").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminConversationsRestrictAccessRemoveGroup(r -> r.channelId("C123").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminConversationsRestrictAccessListGroups(r -> r.channelId("C123").teamId("T123"))
                .isOk(), is(true));
    }

    @Test
    public void adminEmoji() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminEmojiAdd(r -> r.name("smile").url("https://www.example.com"))
                .isOk(), is(true));
        assertThat(methods.adminEmojiAddAlias(r -> r.name("happy").aliasFor("smiley"))
                .isOk(), is(true));
        assertThat(methods.adminEmojiList(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(methods.adminEmojiRemove(r -> r.name("smile"))
                .isOk(), is(true));
        assertThat(methods.adminEmojiRename(r -> r.name("smile").newName("smile2"))
                .isOk(), is(true));
    }

    @Test
    public void adminInviteRequests() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminInviteRequestsApprove(r -> r.inviteRequestId("id").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminInviteRequestsDeny(r -> r.inviteRequestId("id").teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminInviteRequestsList(r -> r.teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminInviteRequestsApprovedList(r -> r.teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminInviteRequestsDeniedList(r -> r.teamId("T123"))
                .isOk(), is(true));
    }

    @Test
    public void adminTeams() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminTeamsAdminsList(r -> r.teamId("T123").limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsCreate(r ->
                r.teamName("foo").teamDescription("this is the team").teamDomain("awesome-domain"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsList(r -> r.limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsOwnersList(r -> r.teamId("T123").limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsSettingsInfo(r -> r.teamId("T123"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetDefaultChannels(r -> r.teamId("T123").channelIds(Arrays.asList("T123")))
                .isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetDescription(r -> r.teamId("T123").description("foo"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetDiscoverability(r -> r.teamId("T123").discoverability("foo"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetIcon(r -> r.teamId("T123").imageUrl("https://www.example.com"))
                .isOk(), is(true));
        assertThat(methods.adminTeamsSettingsSetName(r -> r.teamId("T123").name("new name"))
                .isOk(), is(true));
    }

    @Test
    public void adminUsers() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminUsersAssign(r -> r.teamId("T123").userId("U123").isRestricted(false))
                .isOk(), is(true));
        assertThat(methods.adminUsersInvite(r ->
                r.teamId("T123").channelIds(Arrays.asList("C123")).email("who@example.com"))
                .isOk(), is(true));
        assertThat(methods.adminUsersList(r -> r.teamId("T123").limit(1).cursor("xxx"))
                .isOk(), is(true));
        assertThat(methods.adminUsersRemove(r -> r.teamId("T123").userId("U123"))
                .isOk(), is(true));
        assertThat(methods.adminUsersSetAdmin(r -> r.teamId("T123").userId("U123"))
                .isOk(), is(true));
        assertThat(methods.adminUsersSetOwner(r -> r.teamId("T123").userId("U123"))
                .isOk(), is(true));
        assertThat(methods.adminUsersSetRegular(r -> r.teamId("T123").userId("U123"))
                .isOk(), is(true));
        assertThat(methods.adminUsersSetExpiration(r -> r.teamId("T123").userId("U123").expirationTs(12345L))
                .isOk(), is(true));

        assertThat(methods.adminUsersSessionReset(r -> r.userId("U123").mobileOnly(false))
                .isOk(), is(true));
        assertThat(methods.adminUsersSessionResetBulk(r -> r.userIds(Arrays.asList("U123")).mobileOnly(false))
                .isOk(), is(true));
        assertThat(methods.adminUsersSessionList(r -> r.cursor("XXXX").limit(10).teamId("T123").userId("U123"))
                .isOk(), is(true));
        assertThat(methods.adminUsersSessionGetSettings(r -> r.userIds(Arrays.asList("U123")))
                .isOk(), is(true));
        assertThat(methods.adminUsersSessionSetSettings(r -> r.userIds(Arrays.asList("U123")))
                .isOk(), is(true));
        assertThat(methods.adminUsersSessionClearSettings(r -> r.userIds(Arrays.asList("U123")))
                .isOk(), is(true));
    }

    @Test
    public void adminUsergroups() throws Exception {
        MethodsClient methods = slack.methods(ValidToken);

        assertThat(methods.adminUsergroupsAddChannels(r -> r.teamId("T123").usergroupId("S123").channelIds(Arrays.asList("C123", "C234")))
                .isOk(), is(true));
        assertThat(methods.adminUsergroupsListChannels(r -> r.teamId("T123").usergroupId("S123").includeNumMembers(true))
                .isOk(), is(true));
        assertThat(methods.adminUsergroupsRemoveChannels(r -> r.usergroupId("S123").channelIds(Arrays.asList("C123", "C234")))
                .isOk(), is(true));
        assertThat(methods.adminUsergroupsAddTeams(r -> r.usergroupId("S123").teamIds(Arrays.asList("T123", "T234")))
                .isOk(), is(true));

    }
}
