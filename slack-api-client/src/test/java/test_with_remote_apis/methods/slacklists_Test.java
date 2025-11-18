package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.slacklists.SlackListsCreateResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class slacklists_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

   @BeforeClass
   public static void setUp() throws Exception {
       SlackTestConfig.initializeRawJSONDataFiles("slackLists.*");
   }

   @AfterClass
   public static void tearDown() throws InterruptedException {
       SlackTestConfig.awaitCompletion(testConfig);
   }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @Test
    public void create() throws IOException, SlackApiException {
        SlackListsCreateResponse response = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Backlog"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void create_async() throws Exception {
        SlackListsCreateResponse response = slack.methodsAsync().slackListsCreate(r -> r
                .token(botToken)
                .name("Backlog")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    public void create_with_todo_mode() throws IOException, SlackApiException {
        SlackListsCreateResponse response = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Backlog")
                .todoMode(true));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

}

