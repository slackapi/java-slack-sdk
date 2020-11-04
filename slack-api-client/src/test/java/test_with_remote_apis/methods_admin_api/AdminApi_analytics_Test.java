package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.analytics.AdminAnalyticsGetFileResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
            assertNotNull(response.getFileStream());
            List<AdminAnalyticsGetFileResponse.AnalyticsData> results = new ArrayList<>();
            response.forEach(data -> results.add(data));
            assertTrue(results.size() > 0);
            assertNull(response.getFileStream());

            try {
                response.asBytes();
                fail();
            } catch (IOException e) {
                assertEquals("The byte stream has been already consumed.", e.getMessage());
            }
            try {
                response.forEach(data -> {
                });
                fail();
            } catch (IOException e) {
                assertEquals("The byte stream has been already consumed.", e.getMessage());
            }
        }
    }

    @Test
    public void getFile_asBytes() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2020-10-20")
                    .type("member")
            ).get();
            assertNotNull(response.getFileStream());
            assertTrue(response.asBytes().length > 0);
            // can read the bytes
            response.forEach(data -> {
            });
        }
    }
}
