package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_slackbot_skills_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("admin.slackbot.skills.*");
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
            for (String sort : Arrays.asList("name", "usage_count", "date_updated")) {
                AdminSlackbotSkillsListResponse list = client.adminSlackbotSkillsList(r -> r
                        .limit(100)
                        .publishStatus("all")
                        .autoEnabledStatus("all")
                        .sort(sort)
                        .sortDir("desc"));
                assertThat(list.getError(), is(nullValue()));
                assertThat(list.getSkills(), is(notNullValue()));
                assertThat(list.getResponseMetadata(), is(notNullValue()));
            }
        }
    }
}
