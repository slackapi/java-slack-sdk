package test_with_remote_apis;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatDeleteRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsListResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatDeleteResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.model.Channel;
import config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class ExamplesTest {

    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void postAMessageToRandomChannel() throws IOException, SlackApiException, InterruptedException {

        // find all channels in the team
        ChannelsListResponse channelsResponse = slack.methods().channelsList(ChannelsListRequest.builder().token(token).build());
        assertThat(channelsResponse.isOk(), is(true));
        // find #random
        Channel random = channelsResponse.getChannels().stream()
                .filter(c -> c.getName().equals("random")).findFirst().get();

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
