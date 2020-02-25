package test_locally.api.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.AsyncMethodsClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

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
        assertThat(methods.adminAppsRestrictedList(r -> r.teamId("T123"))
                .get().isOk(), is(true));
    }

    @Test
    public void adminConversations() throws Exception {
        AsyncMethodsClient methods = slack.methodsAsync(ValidToken);

        assertThat(methods.adminConversationsSetTeams(r ->
                r.channelId("C123").teamId("T123").targetTeamIds(Arrays.asList("T123", "T234")))
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

        assertThat(methods.adminUsersSessionReset(r -> r.userId("U123").mobileOnly(false))
                .get().isOk(), is(true));
    }

}
