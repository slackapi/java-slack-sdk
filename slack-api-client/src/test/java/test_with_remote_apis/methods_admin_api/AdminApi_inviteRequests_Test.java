package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.invite_requests.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_inviteRequests_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String teamId = System.getenv(Constants.SLACK_SDK_TEST_GRID_TEAM_ID);

    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

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
                    .limit(1)
                    .teamId(teamId))
                    .get();
            assertThat(list.getError(), is(nullValue()));

            AdminInviteRequestsApprovedListResponse approvedList = methodsAsync.adminInviteRequestsApprovedList(r -> r
                    .limit(1)
                    .teamId(teamId))
                    .get();
            assertThat(approvedList.getError(), is(nullValue()));

            AdminInviteRequestsDeniedListResponse deniedList = methodsAsync.adminInviteRequestsDeniedList(r -> r
                    .limit(1)
                    .teamId(teamId))
                    .get();
            assertThat(deniedList.getError(), is(nullValue()));
        }
    }
}
