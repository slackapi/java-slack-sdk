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
    public void getFile_error() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2035-12-31")
                    .type("member")
            ).get();
            assertEquals("file_not_yet_available", response.getError());
            assertFalse(response.isOk());
        }
    }

    @Test
    public void getFile_forEach() throws Exception {
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
            assertTrue(response.isOk());

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
            byte[] bytes = response.asBytes();
            assertTrue(bytes.length > 0);
            assertTrue(response.isOk());

            // Even after consuming the input stream,
            // #asBytes() should be available for multiple calls as the loaded bytes are cached
            byte[] bytes2 = response.asBytes();
            assertTrue(bytes.length == bytes2.length);

            // can read the bytes as the loaded bytes are cached
            response.forEach(data -> {
            });
        }
    }
}
