package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.emoji.EmojiListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class emoji_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("emoji.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void emojiList_bot() throws IOException, SlackApiException {
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
        {
            EmojiListResponse response = slack.methods().emojiList(r -> r.token(botToken));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getEmoji(), is(notNullValue()));
        }
    }

    @Test
    public void emojiList_user() throws IOException, SlackApiException {
        String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        {
            EmojiListResponse response = slack.methods().emojiList(r -> r.token(userToken));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getEmoji(), is(notNullValue()));
        }
    }

}
