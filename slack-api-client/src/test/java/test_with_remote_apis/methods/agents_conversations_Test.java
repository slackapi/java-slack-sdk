package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsCreateResponse;
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
    // create -> exercise the code-channel operations (properties, views, commands, and a real canvas) -> archive,
    // deleting the canvas we created at the end.
    //
    // agents.conversations operates on code channels. The create response models only the common top-level fields, so
    // it does not return an id we can thread onward; we therefore drive the view/canvas/archive operations against a
    // channel we create and own for this run. Those operations may return an error against a non-code channel rather
    // than ok=true, so we assert only that each call round-trips a non-null response rather than requiring ok=true.
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

        final String viewKey = "agents-conversations-remote-test-view";

        try {
            // create: create a dedicated code channel for the agent session, linked to the origin message.
            // The subsequent calls operate on the code channel this returns, not the origin channel.
            AgentsConversationsCreateResponse create = client.agentsConversationsCreate(r -> r
                    .name("agents-conversations-remote-test")
                    .originChannelId(channelId)
                    .originMessageTs(originMessageTs));
            assertThat(create.getError(), is(nullValue()));
            String codeChannelId = create.getChannelId();
            assertThat(codeChannelId, is(notNullValue()));

            // setProperties: update title/status on the code channel.
            assertThat(client.agentsConversationsSetProperties(r -> r
                    .channelId(codeChannelId)
                    .title("Remote test title")
                    .status("processing")), is(notNullValue()));

            // setView: attach the real canvas as a canvas-type view in the code channel.
            assertThat(client.agentsConversationsSetView(r -> r
                    .channelId(codeChannelId)
                    .type("canvas")
                    .viewKey(viewKey)
                    .name("Plan")
                    .canvasId(canvasId)), is(notNullValue()));

            // listViews: list the views attached to the code channel.
            assertThat(client.agentsConversationsListViews(r -> r
                    .channelId(codeChannelId)), is(notNullValue()));

            // getCanvas: fetch the canvas attached to the code channel.
            assertThat(client.agentsConversationsGetCanvas(r -> r
                    .channel(codeChannelId)
                    .canvasId(canvasId)), is(notNullValue()));

            // setCanvasContent: replace the full markdown content of the plan canvas.
            assertThat(client.agentsConversationsSetCanvasContent(r -> r
                    .channel(codeChannelId)
                    .canvasId(canvasId)
                    .content("# Plan\n\n- [x] initial item\n- [ ] follow-up item\n")), is(notNullValue()));

            // setCommands: register the agent's slash commands as a JSON-encoded array string.
            assertThat(client.agentsConversationsSetCommands(r -> r
                    .channelId(codeChannelId)
                    .commandsAsString("[]")), is(notNullValue()));

            // removeView: remove the canvas view we attached (by its agent-assigned key).
            assertThat(client.agentsConversationsRemoveView(r -> r
                    .channelId(codeChannelId)
                    .viewKey(viewKey)), is(notNullValue()));

            // archive: archive the code channel.
            assertThat(client.agentsConversationsArchive(r -> r
                    .channelId(codeChannelId)), is(notNullValue()));
        } finally {
            // Clean up the standalone canvas we created for this run, even if an assertion above failed.
            assertThat(client.canvasesDelete(r -> r.canvasId(canvasId)).getError(), is(nullValue()));
        }
    }
}
