package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.channels.ChannelsHistoryResponse;
import com.slack.api.methods.response.channels.ChannelsListResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class channels_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Ignore
    @Test
    public void channelsList() throws IOException, SlackApiException {
        ChannelsListResponse channelsListResponse = slack.methods().channelsList(r ->
                r.token(botToken).excludeArchived(true).limit(100));
        assertThat(channelsListResponse.getError(), is("method_deprecated"));
    }

    @Ignore
    @Test
    public void channelsHistory() throws Exception {
        ChannelsHistoryResponse history = slack.methods().channelsHistory(req -> req
                .token(botToken)
                .channel("C111")
                .count(20));
        assertThat(history.getError(), is("method_deprecated"));
    }

}
