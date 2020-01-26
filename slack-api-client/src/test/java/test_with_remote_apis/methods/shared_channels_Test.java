package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class shared_channels_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    @Test
    public void fetchSharedChannel() throws Exception {
        Optional<String> sharedChannelId = Optional.ofNullable(System.getenv(Constants.SLACK_SDK_TEST_SHARED_CHANNEL_ID));
        if (sharedChannelId.isPresent()) {
            String channel = sharedChannelId.get();
            ConversationsInfoResponse response = slack.methods().conversationsInfo(r ->
                    r.token(token).channel(channel));
            assertThat(response.getError(), is(nullValue()));
            assertThat(response.getChannel().isShared(), is(true));
            assertThat(response.getChannel().isExtShared(), is(true));
            assertThat(response.getChannel().getConversationHostId(), is(notNullValue()));
        }
    }

}
