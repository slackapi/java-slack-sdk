package test_with_remote_apis;

import com.slack.api.Slack;
import com.slack.api.methods.response.channels.ChannelsArchiveResponse;
import com.slack.api.methods.response.conversations.ConversationsArchiveResponse;
import com.slack.api.model.Channel;
import com.slack.api.model.Conversation;
import com.slack.api.model.ConversationType;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class UnnecessaryChannelsCleanerTest {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Ignore
    @Test
    public void deleteUnnecessaryPublicChannels() throws Exception {
        for (Conversation channel : slack.methods().conversationsList(r -> r
                .token(botToken)
                .types(Arrays.asList(ConversationType.PUBLIC_CHANNEL))
                .excludeArchived(true)
                .limit(5000)).getChannels()) {

            log.info(channel.toString());

            if (channel.getName().startsWith("test") && !channel.isGeneral()) {
                ConversationsArchiveResponse resp = slack.methods().conversationsArchive(r -> r
                        .token(botToken).channel(channel.getId()));
                assertThat(resp.getError(), is(nullValue()));
            }
        }
    }

    @Ignore
    @Test
    public void deleteUnnecessaryPrivateChannels() throws Exception {
        for (Conversation channel : slack.methods().conversationsList(r -> r
                .token(botToken)
                .excludeArchived(true)
                .limit(1000)
                .types(Arrays.asList(ConversationType.PRIVATE_CHANNEL))).getChannels()) {

            log.info(channel.toString());

            if ((channel.getName().startsWith("test") || channel.getName().startsWith("secret-"))
                    && !channel.isGeneral()) {
                ConversationsArchiveResponse resp = slack.methods().conversationsArchive(r -> r
                        .token(botToken)
                        .channel(channel.getId()));
                assertThat(resp.getError(), is(nullValue()));
            }
        }
    }

}
