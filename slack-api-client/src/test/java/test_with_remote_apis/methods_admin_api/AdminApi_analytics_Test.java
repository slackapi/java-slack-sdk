package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.analytics.AdminAnalyticsGetFileResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class AdminApi_analytics_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void getFile() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2020-10-20")
                    .type("member")
            ).get();
            assertTrue(response.getFile().length > 0);
            assertNotNull(response.getFile());
            assertTrue(response.getFile().equals(response.asBytes()));
        }
    }
}
