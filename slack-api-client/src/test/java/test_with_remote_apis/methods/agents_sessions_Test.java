package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.agents.sessions.AgentsSessionsRenameResponse;
import com.slack.api.methods.response.agents.sessions.AgentsSessionsSetStatusResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeNotNull;

@Slf4j
public class agents_sessions_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String teamId = null; // Required if testing in an org environment. eg. "T0123ABC"

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    private String randomChannelId = null;

    void loadRandomChannelId() throws IOException, SlackApiException {
        if (randomChannelId == null) {
            ConversationsListResponse channelsListResponse = slack.methods()
                    .conversationsList(r -> {
                        r.token(botToken).excludeArchived(true).limit(100);
                        if (teamId != null) {
                            r.teamId(teamId);
                        }
                        return r;
                    });
            assertThat(channelsListResponse.getError(), is(nullValue()));
            for (Conversation channel : channelsListResponse.getChannels()) {
                if (channel.getName().equals("random")) {
                    randomChannelId = channel.getId();
                    break;
                }
            }
        }
    }

    @Test
    public void setStatusAndRename() throws IOException, SlackApiException {
        assumeNotNull(botToken);
        loadRandomChannelId();

        // Start a thread with a plain message; its ts scopes the agent session.
        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .text("Starting an agent session"));
        assertThat(message.getError(), is(nullValue()));
        assertThat(message.isOk(), is(true));
        String threadTs = message.getTs();

        AgentsSessionsSetStatusResponse setStatus = slack.methods(botToken).agentsSessionsSetStatus(r -> r
                .channelId(randomChannelId)
                .threadTs(threadTs)
                .status("processing")
                .title("Working on your request"));
        assertThat(setStatus.getError(), is(nullValue()));
        assertThat(setStatus.isOk(), is(true));

        AgentsSessionsRenameResponse rename = slack.methods(botToken).agentsSessionsRename(r -> r
                .channelId(randomChannelId)
                .threadTs(threadTs)
                .title("Renamed agent session"));
        assertThat(rename.getError(), is(nullValue()));
        assertThat(rename.isOk(), is(true));

        AgentsSessionsSetStatusResponse closed = slack.methods(botToken).agentsSessionsSetStatus(r -> r
                .channelId(randomChannelId)
                .threadTs(threadTs)
                .status("closed"));
        assertThat(closed.getError(), is(nullValue()));
        assertThat(closed.isOk(), is(true));
    }
}
