package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.admin.analytics.members.AdminAnalyticsMembersActivityResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_analytics_members_activity_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("admin.analytics.members.activity");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);

    @Test
    public void list() throws Exception {
        if (orgAdminUserToken != null) {
            MethodsClient client = slack.methods(orgAdminUserToken);
            AdminAnalyticsMembersActivityResponse first = client.adminAnalyticsMembersActivity(r -> r.limit(1));
            assertThat(first.getError(), is(nullValue()));
            assertThat(first.getAvailableDateRange(), is(notNullValue()));
            String cursor = first.getResponseMetadata().getNextCursor();
            AdminAnalyticsMembersActivityResponse next = client.adminAnalyticsMembersActivity(r -> r.limit(1).cursor(cursor));
            assertThat(next.getError(), is(nullValue()));
            assertThat(next.getMembers(), is(notNullValue()));
        }
    }
}
