package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsAddTeamsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsListChannelsResponse;
import com.slack.api.methods.response.admin.usergroups.AdminUsergroupsRemoveChannelsResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_usergroups_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String teamAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_WORKSPACE_ADMIN_USER_TOKEN);
    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);
    static String idpUsergroupId = System.getenv(Constants.SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID);

    @Test
    public void channels() throws Exception {
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
    public void teams() throws Exception {
        if (teamAdminUserToken != null && orgAdminUserToken != null && idpUsergroupId != null) {
            List<String> teamIds = slack.methods(orgAdminUserToken).adminTeamsList(r -> r.limit(1))
                    .getTeams()
                    .stream().map(t -> t.getId()).collect(Collectors.toList());
            AdminUsergroupsAddTeamsResponse addTeams = slack.methods(orgAdminUserToken)
                    .adminUsergroupsAddTeams(r -> r.teamIds(teamIds).usergroupId(idpUsergroupId));
            assertThat(addTeams.getError(), is(nullValue()));

            addTeams = slack.methodsAsync(orgAdminUserToken)
                    .adminUsergroupsAddTeams(r -> r.teamIds(teamIds).usergroupId(idpUsergroupId))
                    .get();
            assertThat(addTeams.getError(), is(nullValue()));
        }
    }
}
