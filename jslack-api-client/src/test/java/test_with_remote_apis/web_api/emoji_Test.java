package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.emoji.EmojiListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class emoji_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void emojiList() throws IOException, SlackApiException {
        String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);
        {
            EmojiListResponse response = slack.methods().emojiList(r -> r.token(token).build());
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
            assertThat(response.getEmoji(), is(notNullValue()));
        }
    }

}