package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.slackbot.SkillMcpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class SlackbotSkillsTest {

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
        assertThat(slack.methods(ValidToken).slackbotSkillsCreate(r -> r
                .displayName("Daily priorities")
                .instructions("# Purpose\nSuggest the three most important things to work on today.")
                .description("Reviews information from Slack and your calendar to suggest priorities for the day.")
                .category("productivity")
                .examplePrompt("Run {skill} for today")
                .mcpServers(Arrays.asList(SkillMcpServer.builder()
                        .serviceType("app")
                        .appId("A0123ABCD")
                        .serverId("Amcp0456EFGH")
                        .build()))
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).slackbotSkillsCreate(r -> r
                .displayName("Daily priorities")
                .canvasId("F0123ABCD")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).slackbotSkillsInfo(r -> r
                .skillId("F0123ABCD")
        ).isOk(), is(true));
    }

    @Test
    public void test_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsCreate(r -> r
                .displayName("Daily priorities")
                .instructions("# Purpose\nSuggest the three most important things to work on today.")
                .description("Reviews information from Slack and your calendar to suggest priorities for the day.")
                .category("productivity")
                .examplePrompt("Run {skill} for today")
                .mcpServers(Arrays.asList(SkillMcpServer.builder()
                        .serviceType("app")
                        .appId("A0123ABCD")
                        .serverId("Amcp0456EFGH")
                        .build()))
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsCreate(r -> r
                .displayName("Daily priorities")
                .canvasId("F0123ABCD")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsInfo(r -> r
                .skillId("F0123ABCD")
        ).get().isOk(), is(true));
    }
}
