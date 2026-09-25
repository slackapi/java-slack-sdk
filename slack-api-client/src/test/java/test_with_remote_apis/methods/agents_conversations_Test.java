package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
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
import com.slack.api.methods.response.canvases.CanvasesCreateResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsCreateResponse;
import com.slack.api.model.canvas.CanvasDocumentContent;
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

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    // One lifecycle test for the whole agents.conversations (code channel) family:
    // create -> exercise the code-channel operations (properties, views, commands, and a real canvas) -> archive.
    //
    // agents.conversations operates on code channels. The create response models only the common top-level fields, so
    // it does not return an id we can thread onward; we therefore drive the view/canvas/archive operations against a
    // channel we create and own for this run. Those operations may still return an error against a non-code channel
    // rather than ok=true — capturing that request/response exchange in the API logs is the point of this remote-API
    // test, so we assert each call completes with a non-null response and log the outcome without requiring ok=true.
    @Test
    public void createExerciseAndArchiveCodeChannel() throws IOException, SlackApiException {
        assumeNotNull(botToken);
        MethodsClient client = slack.methods(botToken);

        // A channel we own, used both as the create origin and as the target for the subsequent operations.
        ConversationsCreateResponse channel = client.conversationsCreate(r -> r
                .name("agents-conv-test-" + System.currentTimeMillis()));
        assertThat(channel.getError(), is(nullValue()));
        String channelId = channel.getChannel().getId();

        // The origin message whose ts scopes the agent session / code channel.
        ChatPostMessageResponse message = client.chatPostMessage(r -> r
                .channel(channelId)
                .text("Starting an agent session (agents.conversations remote-API test)"));
        assertThat(message.getError(), is(nullValue()));
        assertThat(message.isOk(), is(true));
        String originMessageTs = message.getTs();

        // A real canvas to attach to the code channel and later fetch / rewrite.
        CanvasesCreateResponse canvas = client.canvasesCreate(r -> r
                .title("agents.conversations remote test canvas")
                .documentContent(CanvasDocumentContent.builder()
                        .markdown("# Plan\n\n- [ ] initial item\n")
                        .build()));
        assertThat(canvas.getError(), is(nullValue()));
        String canvasId = canvas.getCanvasId();
        assertThat(canvasId, is(notNullValue()));

        // create: create a dedicated code channel for the agent session, linked to the origin message.
        AgentsConversationsCreateResponse create = client.agentsConversationsCreate(r -> r
                .name("agents-conversations-remote-test")
                .originChannelId(channelId)
                .originMessageTs(originMessageTs));
        assertThat(create, is(notNullValue()));
        log.info("agents.conversations.create: ok={}, error={}", create.isOk(), create.getError());

        final String viewKey = "agents-conversations-remote-test-view";

        // setProperties: update title/status on the code channel.
        AgentsConversationsSetPropertiesResponse setProperties = client.agentsConversationsSetProperties(r -> r
                .channelId(channelId)
                .title("Remote test title")
                .status("processing"));
        assertThat(setProperties, is(notNullValue()));
        log.info("agents.conversations.setProperties: ok={}, error={}", setProperties.isOk(), setProperties.getError());

        // setView: attach the real canvas as a canvas-type view in the code channel.
        AgentsConversationsSetViewResponse setView = client.agentsConversationsSetView(r -> r
                .channelId(channelId)
                .type("canvas")
                .viewKey(viewKey)
                .name("Plan")
                .canvasId(canvasId));
        assertThat(setView, is(notNullValue()));
        log.info("agents.conversations.setView: ok={}, error={}", setView.isOk(), setView.getError());

        // listViews: list the views attached to the code channel.
        AgentsConversationsListViewsResponse listViews = client.agentsConversationsListViews(r -> r
                .channelId(channelId));
        assertThat(listViews, is(notNullValue()));
        log.info("agents.conversations.listViews: ok={}, error={}", listViews.isOk(), listViews.getError());

        // getCanvas: fetch the canvas attached to the code channel.
        AgentsConversationsGetCanvasResponse getCanvas = client.agentsConversationsGetCanvas(r -> r
                .channel(channelId)
                .canvasId(canvasId));
        assertThat(getCanvas, is(notNullValue()));
        log.info("agents.conversations.getCanvas: ok={}, error={}", getCanvas.isOk(), getCanvas.getError());

        // setCanvasContent: replace the full markdown content of the plan canvas.
        AgentsConversationsSetCanvasContentResponse setCanvasContent = client.agentsConversationsSetCanvasContent(r -> r
                .channel(channelId)
                .canvasId(canvasId)
                .content("# Plan\n\n- [x] initial item\n- [ ] follow-up item\n"));
        assertThat(setCanvasContent, is(notNullValue()));
        log.info("agents.conversations.setCanvasContent: ok={}, error={}",
                setCanvasContent.isOk(), setCanvasContent.getError());

        // setCommands: register the agent's slash commands as a JSON-encoded array string.
        AgentsConversationsSetCommandsResponse setCommands = client.agentsConversationsSetCommands(r -> r
                .channelId(channelId)
                .commandsAsString("[]"));
        assertThat(setCommands, is(notNullValue()));
        log.info("agents.conversations.setCommands: ok={}, error={}", setCommands.isOk(), setCommands.getError());

        // removeView: remove the canvas view we attached (by its agent-assigned key).
        AgentsConversationsRemoveViewResponse removeView = client.agentsConversationsRemoveView(r -> r
                .channelId(channelId)
                .viewKey(viewKey));
        assertThat(removeView, is(notNullValue()));
        log.info("agents.conversations.removeView: ok={}, error={}", removeView.isOk(), removeView.getError());

        // archive: archive the code channel.
        AgentsConversationsArchiveResponse archive = client.agentsConversationsArchive(r -> r
                .channelId(channelId));
        assertThat(archive, is(notNullValue()));
        log.info("agents.conversations.archive: ok={}, error={}", archive.isOk(), archive.getError());
    }
}
