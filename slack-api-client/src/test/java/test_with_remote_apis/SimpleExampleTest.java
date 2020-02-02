package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatDeleteRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.channels.ChannelsListResponse;
import com.slack.api.methods.response.chat.ChatDeleteResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Channel;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class SimpleExampleTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String token = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @Test
    public void postAMessageToRandomChannel() throws IOException, SlackApiException, InterruptedException {

        // find all channels in the team
        ChannelsListResponse channelsResponse = slack.methods().channelsList(r -> r.token(token));
        assertThat(channelsResponse.isOk(), is(true));
        // find #random
        List<Channel> channels_ = slack.methods().channelsList(r -> r.token(token)).getChannels();
        Channel random = null;
        for (Channel c : channels_) {
            if (c.getName().equals("random")) {
                random = c;
                break;
            }
        }

        // https://slack.com/api/chat.postMessage
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(token)
                .channel(random.getId())
                .text("Hello World!")
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.isOk(), is(true));

        // timestamp of the posted message
        String messageTimestamp = postResponse.getMessage().getTs();

        Thread.sleep(1000L);

        ChatDeleteResponse deleteResponse = slack.methods().chatDelete(ChatDeleteRequest.builder()
                .token(token)
                .channel(random.getId())
                .ts(messageTimestamp)
                .build());
        assertThat(deleteResponse.getError(), is(nullValue()));
        assertThat(deleteResponse.isOk(), is(true));
    }

}
