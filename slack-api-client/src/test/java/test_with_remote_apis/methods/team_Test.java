package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.team.*;
import com.slack.api.methods.response.team.profile.TeamProfileGetResponse;
import com.slack.api.model.User;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class team_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("team.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    String gridWorkspaceUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
    String gridTeamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);

    @Test
    public void teamAccessLogs() throws Exception {
        TeamAccessLogsResponse response = slack.methods().teamAccessLogs(r -> r.token(userToken));
        if (response.isOk()) {
            // when you pay for this team
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        } else {
            // when you don't pay for this team
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("paid_only"));
        }
    }

    @Test
    public void teamAccessLogs_async() throws Exception {
        TeamAccessLogsResponse response = slack.methodsAsync().teamAccessLogs(r -> r.token(userToken)).get();
        if (response.isOk()) {
            // when you pay for this team
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        } else {
            // when you don't pay for this team
            assertThat(response.isOk(), is(false));
            assertThat(response.getError(), is("paid_only"));
        }
    }

    @Test
    public void teamBillableInfo() throws Exception {
        // Using async client to avoid an exception due to rate limited errors
        List<User> users = slack.methodsAsync().usersList(r -> r.token(userToken)).get().getMembers();
        User user = null;
        for (User u : users) {
            if (!u.isBot() && !"USLACKBOT".equals(u.getId())) {
                user = u;
                break;
            }
        }
        String userId = user.getId();
        TeamBillableInfoResponse response = slack.methods().teamBillableInfo(r -> r
                .token(userToken)
                .user(userId));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamBillableInfo_async() throws Exception {
        // Using async client to avoid an exception due to rate limited errors
        List<User> users = slack.methodsAsync().usersList(r -> r.token(userToken)).get().getMembers();
        User user = null;
        for (User u : users) {
            if (!u.isBot() && !"USLACKBOT".equals(u.getId())) {
                user = u;
                break;
            }
        }
        String userId = user.getId();
        TeamBillableInfoResponse response = slack.methodsAsync().teamBillableInfo(r -> r
                .token(userToken)
                .user(userId))
                .get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamInfo() throws Exception {
        TeamInfoResponse response = slack.methods().teamInfo(r -> r.token(botToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamInfo_EnterpriseGrid() throws Exception {
        MethodsClient client = slack.methods(gridWorkspaceUserToken);
        TeamInfoResponse response = client.teamInfo(r -> r);
        assertThat(response.getError(), is(nullValue()));

        TeamInfoResponse domainResponse = client.teamInfo(r -> r
                .domain(response.getTeam().getDomain())
        );
        assertThat(domainResponse.getError(), is(nullValue()));
    }

    @Test
    public void teamInfo_Grid_Workspace() throws Exception {
        if (gridWorkspaceUserToken != null) {
            TeamInfoResponse response = slack.methods().teamInfo(r -> r
                    .token(gridWorkspaceUserToken)
                    .team(gridTeamId)
            );
            assertThat(response.getError(), is(nullValue()));
        }
    }

    @Test
    public void teamInfo_async() throws Exception {
        TeamInfoResponse response = slack.methodsAsync().teamInfo(r -> r.token(botToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamIntegrationLogs() throws Exception {
        // Using async client to avoid an exception due to rate limited errors
        String user = slack.methodsAsync().usersList(r -> r.token(userToken)).get().getMembers().get(0).getId();
        TeamIntegrationLogsResponse response = slack.methods().teamIntegrationLogs(r -> r
                .token(userToken)
                .user(user));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamIntegrationLogs_async() throws Exception {
        // Using async client to avoid an exception due to rate limited errors
        String user = slack.methodsAsync().usersList(r -> r.token(userToken)).get().getMembers().get(0).getId();
        TeamIntegrationLogsResponse response = slack.methodsAsync().teamIntegrationLogs(r -> r
                .token(userToken)
                .user(user)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamProfileGet() throws Exception {
        TeamProfileGetResponse response = slack.methods().teamProfileGet(r -> r.token(botToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamProfileGet_async() throws Exception {
        TeamProfileGetResponse response = slack.methodsAsync().teamProfileGet(r -> r.token(botToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamBilling() throws Exception {
        TeamBillingInfoResponse response = slack.methods().teamBillingInfo(r -> r.token(botToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamBilling_async() throws Exception {
        TeamBillingInfoResponse response = slack.methodsAsync().teamBillingInfo(r -> r.token(botToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamPreferencesList() throws Exception {
        TeamPreferencesListResponse response = slack.methods().teamPreferencesList(r -> r.token(botToken));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void teamPreferencesList_async() throws Exception {
        TeamPreferencesListResponse response = slack.methodsAsync()
                .teamPreferencesList(r -> r.token(botToken)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

}
