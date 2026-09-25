package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsArchiveResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsCreateResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsGetCanvasResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsListViewsResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsRemoveViewResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsSetCanvasContentResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsSetCommandsResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsSetPropertiesResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsSetViewResponse;
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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeNotNull;

@Slf4j
public class agents_conversations_Test {

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

    // agents.conversations operates on code channels. Exercising these methods against a plain workspace channel is
    // expected to return an error rather than ok=true; the point of this remote-API test is to generate real
    // request/response logs for the whole agents.conversations method family. We therefore assert that each call
    // completes with a non-null response and log the outcome, without requiring ok=true.
    @Test
    public void exerciseAgentsConversationsMethods() throws IOException, SlackApiException {
        assumeNotNull(botToken);
        loadRandomChannelId();

        // The origin message whose ts scopes the agent session / code channel.
        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .text("Starting an agent session (agents.conversations remote-API test)"));
        assertThat(message.getError(), is(nullValue()));
        assertThat(message.isOk(), is(true));
        String originMessageTs = message.getTs();

        // create: create a dedicated code channel for the agent session, linked to the origin message.
        AgentsConversationsCreateResponse create = slack.methods(botToken).agentsConversationsCreate(r -> r
                .name("agents-conversations-remote-test")
                .originChannelId(randomChannelId)
                .originMessageTs(originMessageTs));
        assertThat(create, is(notNullValue()));
        log.info("agents.conversations.create: ok={}, error={}", create.isOk(), create.getError());

        // Placeholder ids for the calls that need real code-channel state. When run against a real code channel these
        // can be sourced from the create response / a provisioned fixture; against a plain channel they exercise the
        // request path and produce an error response, which is what we want to capture in the logs.
        final String codeChannelId = randomChannelId;
        final String viewKey = "agents-conversations-remote-test-view";
        final String canvasId = "F0000000000";

        // setProperties: update title/status on the code channel.
        AgentsConversationsSetPropertiesResponse setProperties = slack.methods(botToken)
                .agentsConversationsSetProperties(r -> r
                        .channelId(codeChannelId)
                        .title("Remote test title")
                        .status("processing"));
        assertThat(setProperties, is(notNullValue()));
        log.info("agents.conversations.setProperties: ok={}, error={}", setProperties.isOk(), setProperties.getError());

        // setView: create/update an HTML view in the code channel.
        AgentsConversationsSetViewResponse setView = slack.methods(botToken).agentsConversationsSetView(r -> r
                .channelId(codeChannelId)
                .type("html")
                .viewKey(viewKey)
                .name("Remote test view")
                .content("<html><body><h1>agents.conversations remote test</h1></body></html>"));
        assertThat(setView, is(notNullValue()));
        log.info("agents.conversations.setView: ok={}, error={}", setView.isOk(), setView.getError());

        // listViews: list the views attached to the code channel.
        AgentsConversationsListViewsResponse listViews = slack.methods(botToken).agentsConversationsListViews(r -> r
                .channelId(codeChannelId));
        assertThat(listViews, is(notNullValue()));
        log.info("agents.conversations.listViews: ok={}, error={}", listViews.isOk(), listViews.getError());

        // removeView: remove the view we just tried to set (by its agent-assigned key).
        AgentsConversationsRemoveViewResponse removeView = slack.methods(botToken).agentsConversationsRemoveView(r -> r
                .channelId(codeChannelId)
                .viewKey(viewKey));
        assertThat(removeView, is(notNullValue()));
        log.info("agents.conversations.removeView: ok={}, error={}", removeView.isOk(), removeView.getError());

        // setCommands: register the agent's slash commands as a JSON-encoded array string.
        AgentsConversationsSetCommandsResponse setCommands = slack.methods(botToken).agentsConversationsSetCommands(r -> r
                .channelId(codeChannelId)
                .commandsAsString("[]"));
        assertThat(setCommands, is(notNullValue()));
        log.info("agents.conversations.setCommands: ok={}, error={}", setCommands.isOk(), setCommands.getError());

        // getCanvas: fetch a canvas attached to the code channel.
        AgentsConversationsGetCanvasResponse getCanvas = slack.methods(botToken).agentsConversationsGetCanvas(r -> r
                .channel(codeChannelId)
                .canvasId(canvasId));
        assertThat(getCanvas, is(notNullValue()));
        log.info("agents.conversations.getCanvas: ok={}, error={}", getCanvas.isOk(), getCanvas.getError());

        // setCanvasContent: replace the full markdown content of a plan canvas attached to the code channel.
        AgentsConversationsSetCanvasContentResponse setCanvasContent = slack.methods(botToken)
                .agentsConversationsSetCanvasContent(r -> r
                        .channel(codeChannelId)
                        .canvasId(canvasId)
                        .content("# Remote test plan\n\nContent set by agents.conversations remote-API test."));
        assertThat(setCanvasContent, is(notNullValue()));
        log.info("agents.conversations.setCanvasContent: ok={}, error={}",
                setCanvasContent.isOk(), setCanvasContent.getError());

        // archive: archive the code channel.
        AgentsConversationsArchiveResponse archive = slack.methods(botToken).agentsConversationsArchive(r -> r
                .channelId(codeChannelId));
        assertThat(archive, is(notNullValue()));
        log.info("agents.conversations.archive: ok={}, error={}", archive.isOk(), archive.getError());
    }
}
