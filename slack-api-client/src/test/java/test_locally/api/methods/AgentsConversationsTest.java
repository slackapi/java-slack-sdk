package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.agents.conversations.AgentsConversationsSetCommandsRequest;
import com.slack.api.methods.request.agents.conversations.AgentsConversationsSetPropertiesRequest;
import com.slack.api.methods.request.agents.conversations.AgentsConversationsSetViewRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class AgentsConversationsTest {

    MockSlackApiServer server = new MockSlackApiServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);

    @Before
    public void setup() throws Exception {
        server.start();
        config.setMethodsEndpointUrlPrefix(server.getMethodsEndpointPrefix());
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws Exception {
        assertThat(slack.methods(ValidToken).agentsConversationsArchive(r -> r
                .channelId("C123")
                .summaryMessageTs("123.123")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsCreate(r -> r
                .name("code-channel")
                .isPrivate(true)
                .originChannelId("C123")
                .originMessageTs("123.123")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsGetCanvas(r -> r
                .channel("C123")
                .canvasId("F123")
                .contentFormat("markdown")
                .includeResolved(true)
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsListViews(r -> r
                .channelId("C123")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsRemoveView(r -> r
                .channelId("C123")
                .viewId("V123")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsSetCanvasContent(r -> r
                .channel("C123")
                .canvasId("F123")
                .content("# Plan")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsSetCommands(r -> r
                .channelId("C123")
                .commands(Arrays.asList(AgentsConversationsSetCommandsRequest.Command.builder()
                        .name("summarize")
                        .description("Summarize the session so far")
                        .argumentHint("[topic]")
                        .shouldEscape(true)
                        .build()))
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsSetProperties(r -> r
                .channelId("C123")
                .codeChannel(AgentsConversationsSetPropertiesRequest.CodeChannel.builder()
                        .contextBarItems(Arrays.asList(AgentsConversationsSetPropertiesRequest.ContextBarItem.builder()
                                .key("repo")
                                .label("borant/billing")
                                .icon("folder")
                                .url("https://github.com/borant/billing")
                                .build()))
                        .summaryMessage(AgentsConversationsSetPropertiesRequest.SummaryMessage.builder()
                                .messageTs("123.123")
                                .build())
                        .build())
                .agentResource(AgentsConversationsSetPropertiesRequest.AgentResource.builder()
                        .url("https://github.com/borant/billing/pull/1")
                        .resourceType("pull_request")
                        .title("Fix billing")
                        .provider("github")
                        .build())
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsSetView(r -> r
                .channelId("C123")
                .viewKey("coverage")
                .name("Coverage")
                .content("<!doctype html><html><head></head><body></body></html>")
                .csp(AgentsConversationsSetViewRequest.Csp.builder()
                        .connectDomains(Arrays.asList("https://api.example.com"))
                        .resourceDomains(Arrays.asList("https://cdn.jsdelivr.net"))
                        .build())
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsSetView(r -> r
                .channelId("C123")
                .type("block_kit")
                .viewKey("summary")
                .name("Summary")
                .blocks(asBlocks(section(s -> s.text(plainText("hi")))))
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).agentsConversationsSetView(r -> r
                .channelId("C123")
                .type("canvas")
                .viewKey("plan")
                .name("Plan")
                .canvasId("F123")
        ).isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsArchive(r -> r
                .channelId("C123")
                .summaryMessageTs("123.123")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsCreate(r -> r
                .name("code-channel")
                .isPrivate(true)
                .originChannelId("C123")
                .originMessageTs("123.123")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsGetCanvas(r -> r
                .channel("C123")
                .canvasId("F123")
                .contentFormat("markdown")
                .includeResolved(true)
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsListViews(r -> r
                .channelId("C123")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsRemoveView(r -> r
                .channelId("C123")
                .viewId("V123")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsSetCanvasContent(r -> r
                .channel("C123")
                .canvasId("F123")
                .content("# Plan")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsSetCommands(r -> r
                .channelId("C123")
                .commands(Arrays.asList(AgentsConversationsSetCommandsRequest.Command.builder()
                        .name("summarize")
                        .description("Summarize the session so far")
                        .argumentHint("[topic]")
                        .shouldEscape(true)
                        .build()))
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsSetProperties(r -> r
                .channelId("C123")
                .codeChannel(AgentsConversationsSetPropertiesRequest.CodeChannel.builder()
                        .contextBarItems(Arrays.asList(AgentsConversationsSetPropertiesRequest.ContextBarItem.builder()
                                .key("repo")
                                .label("borant/billing")
                                .icon("folder")
                                .url("https://github.com/borant/billing")
                                .build()))
                        .summaryMessage(AgentsConversationsSetPropertiesRequest.SummaryMessage.builder()
                                .messageTs("123.123")
                                .build())
                        .build())
                .agentResource(AgentsConversationsSetPropertiesRequest.AgentResource.builder()
                        .url("https://github.com/borant/billing/pull/1")
                        .resourceType("pull_request")
                        .title("Fix billing")
                        .provider("github")
                        .build())
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsSetView(r -> r
                .channelId("C123")
                .viewKey("coverage")
                .name("Coverage")
                .content("<!doctype html><html><head></head><body></body></html>")
                .csp(AgentsConversationsSetViewRequest.Csp.builder()
                        .connectDomains(Arrays.asList("https://api.example.com"))
                        .resourceDomains(Arrays.asList("https://cdn.jsdelivr.net"))
                        .build())
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsSetView(r -> r
                .channelId("C123")
                .type("block_kit")
                .viewKey("summary")
                .name("Summary")
                .blocks(asBlocks(section(s -> s.text(plainText("hi")))))
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).agentsConversationsSetView(r -> r
                .channelId("C123")
                .type("canvas")
                .viewKey("plan")
                .name("Plan")
                .canvasId("F123")
        ).get().isOk(), is(true));
    }

}
