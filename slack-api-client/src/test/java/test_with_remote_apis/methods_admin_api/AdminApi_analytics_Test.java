package test_with_remote_apis.methods_admin_api;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.response.admin.analytics.AdminAnalyticsGetFileResponse;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class AdminApi_analytics_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());
    static Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static AsyncMethodsClient methodsAsync = slack.methodsAsync(orgAdminUserToken);

    @Test
    public void getFile_member_error() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2035-12-31")
                    .type("member")
            ).get();
            assertEquals("file_not_yet_available", response.getError());
            assertFalse(response.isOk());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));
        }
    }

    @Test
    public void getFile_member_forEach() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2022-10-20")
                    .type("member")
            ).get();
            assertNull(response.getError());
            assertNotNull(response.getFileStream());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

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
    public void getFile_member_forEach_validation() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2022-10-20")
                    .type("member")
            ).get();
            assertNull(response.getError());
            assertNotNull(response.getFileStream());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

            List<AdminAnalyticsGetFileResponse.AnalyticsData> results = new ArrayList<>();
            response.forEach(gson, data -> results.add(data));
            assertTrue(results.size() > 0);
            assertNull(response.getFileStream());
        }
    }

    @Test
    public void getFile_member_asBytes() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2022-10-20")
                    .type("member")
            ).get();
            assertNotNull(response.getFileStream());
            byte[] bytes = response.asBytes();
            assertTrue(bytes.length > 0);
            assertTrue(response.isOk());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

            // Even after consuming the input stream,
            // #asBytes() should be available for multiple calls as the loaded bytes are cached
            byte[] bytes2 = response.asBytes();
            assertTrue(bytes.length == bytes2.length);

            // can read the bytes as the loaded bytes are cached
            response.forEach(data -> {
            });
        }
    }

    @Test
    public void getFile_public_channel() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2020-10-20")
                    .type("public_channel")
            ).get();
            assertNull(response.getError());
            assertNotNull(response.getFileStream());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

            List<AdminAnalyticsGetFileResponse.AnalyticsData> results = new ArrayList<>();
            response.forEach(data -> results.add(data));
            assertTrue(results.size() > 0);

            assertNotNull(results.get(0).getChannelId());

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
    public void getFile_public_channel_validation() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .date("2020-10-20")
                    .type("public_channel")
            ).get();
            assertNull(response.getError());
            assertNotNull(response.getFileStream());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

            List<AdminAnalyticsGetFileResponse.AnalyticsData> results = new ArrayList<>();
            response.forEach(gson, data -> results.add(data));
            assertTrue(results.size() > 0);
        }
    }

    @Test
    public void getFile_public_channel_metadata_only() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .type("public_channel")
                    .metadataOnly(true)
            ).get();
            assertNull(response.getError());
            assertNotNull(response.getFileStream());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

            List<AdminAnalyticsGetFileResponse.AnalyticsData> results = new ArrayList<>();
            response.forEach(data -> results.add(data));
            assertTrue(results.size() > 0);

            assertNotNull(results.get(0).getChannelId());
            assertNotNull(results.get(0).getName());
            assertNotNull(results.get(0).getTopic());
            assertNotNull(results.get(0).getDescription());
            assertNotNull(results.get(0).getDate());

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
    public void getFile_public_channel_metadata_only_validation() throws Exception {
        if (orgAdminUserToken != null) {
            AdminAnalyticsGetFileResponse response = methodsAsync.adminAnalyticsGetFile(r -> r
                    .type("public_channel")
                    .metadataOnly(true)
            ).get();
            assertNull(response.getError());
            assertNotNull(response.getFileStream());
            String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
            assertThat(scopes, is(notNullValue()));

            List<AdminAnalyticsGetFileResponse.AnalyticsData> results = new ArrayList<>();
            response.forEach(gson, data -> results.add(data));
            assertTrue(results.size() > 0);
        }
    }
}
