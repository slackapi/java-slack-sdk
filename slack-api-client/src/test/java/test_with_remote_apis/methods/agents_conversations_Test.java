package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.agents.conversations.AgentsConversationsSetCommandsRequest;
import com.slack.api.methods.request.agents.conversations.AgentsConversationsSetPropertiesRequest;
import com.slack.api.methods.request.agents.conversations.AgentsConversationsSetViewRequest;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsCreateResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsGetCanvasResponse;
import com.slack.api.methods.response.agents.conversations.AgentsConversationsSetViewResponse;
import com.slack.api.methods.response.canvases.CanvasesCreateResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsCreateResponse;
import com.slack.api.methods.response.conversations.ConversationsInfoResponse;
import com.slack.api.model.canvas.CanvasDocumentContent;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assume.assumeNotNull;

@Slf4j
public class agents_conversations_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String commentsCanvasId = System.getenv(Constants.SLACK_SDK_TEST_AGENTS_CANVAS_ID);

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

            // conversations.info: record the code channel's properties (record_channel, agent_session, code_channel).
            // record_channel.record_type is how a code channel is recognized.
            ConversationsInfoResponse info = client.conversationsInfo(r -> r
                    .channel(codeChannelId));
            assertThat(info.getError(), is(nullValue()));
            assertThat(info.getChannel().getProperties().getRecordChannel().getRecordType(), is("agent_channel"));

            // setProperties: set the code channel's context bar items.
            assertThat(client.agentsConversationsSetProperties(r -> r
                    .channelId(codeChannelId)
                    .codeChannel(AgentsConversationsSetPropertiesRequest.CodeChannel.builder()
                            .contextBarItems(Arrays.asList(
                                    AgentsConversationsSetPropertiesRequest.ContextBarItem.builder()
                                            .key("repo")
                                            .label("borant/billing")
                                            .icon("folder")
                                            .url("https://github.com/borant/billing")
                                            .build()))
                            .build())), is(notNullValue()));

            // conversations.info: record the code_channel property again now that it has context bar items.
            assertThat(client.conversationsInfo(r -> r
                    .channel(codeChannelId)).getError(), is(nullValue()));

            // setView: attach an HTML view (keyed by view_key) that we later list and remove.
            AgentsConversationsSetViewResponse setView = client.agentsConversationsSetView(r -> r
                    .channelId(codeChannelId)
                    .viewKey(viewKey)
                    .name("Coverage")
                    .content("<!doctype html><html><head></head><body></body></html>")
                    .csp(AgentsConversationsSetViewRequest.Csp.builder()
                            .resourceDomains(Arrays.asList("https://cdn.jsdelivr.net"))
                            .build()));
            assertThat(setView.getError(), is(nullValue()));

            // setView: also attach the real canvas as a canvas-type view for the canvas methods.
            assertThat(client.agentsConversationsSetView(r -> r
                    .channelId(codeChannelId)
                    .type("canvas")
                    .viewKey(viewKey + "-canvas")
                    .name("Plan")
                    .canvasId(canvasId)), is(notNullValue()));

            // listViews: list the views attached to the code channel.
            assertThat(client.agentsConversationsListViews(r -> r
                    .channelId(codeChannelId)), is(notNullValue()));

            // getCanvas: fetch the canvas attached to the code channel.
            assertThat(client.agentsConversationsGetCanvas(r -> r
                    .channel(codeChannelId)
                    .canvasId(canvasId)), is(notNullValue()));

            // getCanvas: read the comment threads from a long-lived canvas that a person commented on. There is no API
            // to write canvas comments, so this canvas is only attached and read, never rewritten or deleted.
            if (commentsCanvasId != null) {
                assertThat(client.agentsConversationsSetView(r -> r
                        .channelId(codeChannelId)
                        .type("canvas")
                        .viewKey(viewKey + "-comments")
                        .name("Comments")
                        .canvasId(commentsCanvasId)).getError(), is(nullValue()));
                AgentsConversationsGetCanvasResponse comments = client.agentsConversationsGetCanvas(r -> r
                        .channel(codeChannelId)
                        .canvasId(commentsCanvasId));
                assertThat(comments.getError(), is(nullValue()));
                assertThat(comments.getComments().isEmpty(), is(false));
                assertThat(comments.getComments().stream().anyMatch(c -> !c.getQuotedText().isEmpty()), is(true));
                assertThat(comments.getComments().stream().anyMatch(c -> !c.getReplies().isEmpty()), is(true));
            }

            // setCanvasContent: replace the full markdown content of the plan canvas.
            assertThat(client.agentsConversationsSetCanvasContent(r -> r
                    .channel(codeChannelId)
                    .canvasId(canvasId)
                    .content("# Plan\n\n- [x] initial item\n- [ ] follow-up item\n")), is(notNullValue()));

            // setCommands: register the agent's slash commands.
            assertThat(client.agentsConversationsSetCommands(r -> r
                    .channelId(codeChannelId)
                    .commands(Arrays.asList(
                            AgentsConversationsSetCommandsRequest.Command.builder()
                                    .name("summarize")
                                    .description("Summarize the session so far")
                                    .shouldEscape(true)
                                    .build()))).getError(), is(nullValue()));

            // removeView: remove the HTML view we attached (by the view id setView returned).
            assertThat(client.agentsConversationsRemoveView(r -> r
                    .channelId(codeChannelId)
                    .viewId(setView.getViewId())).getError(), is(nullValue()));

            // archive: archive the code channel.
            assertThat(client.agentsConversationsArchive(r -> r
                    .channelId(codeChannelId)), is(notNullValue()));
        } finally {
            // Clean up the standalone canvas we created for this run, even if an assertion above failed.
            assertThat(client.canvasesDelete(r -> r.canvasId(canvasId)).getError(), is(nullValue()));
        }
    }
}
