package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.asssistant.search.AssistantSearchContextResponse;
import com.slack.api.methods.response.asssistant.search.AssistantSearchInfoResponse;
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
public class assistant_search_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("assistant.search.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void context() throws Exception {
        AssistantSearchContextResponse response = slack.methods(userToken).assistantSearchContext(r -> r
                .query("test")
                .contentTypes(Arrays.asList("messages", "files", "channels", "users"))
                .includeContextMessages(true));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getResults(), is(notNullValue()));
    }

    @Test
    public void context_users() throws Exception {
        String userName = slack.methods(userToken).authTest(r -> r).getUser();
        AssistantSearchContextResponse response = slack.methods(userToken).assistantSearchContext(r -> r
                .query(userName)
                .contentTypes(Arrays.asList("users")));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getResults().getUsers().isEmpty(), is(false));
    }

    @Test
    public void info() throws Exception {
        AssistantSearchInfoResponse response = slack.methods(userToken).assistantSearchInfo(r -> r);
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getIsAiSearchEnabled(), is(notNullValue()));
    }
}
