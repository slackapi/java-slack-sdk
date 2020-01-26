package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.emoji.EmojiListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class emoji_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void emojiList() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
        {
            EmojiListResponse response = slack.methods().emojiList(r -> r.token(token));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getEmoji(), is(notNullValue()));
        }
    }

}
