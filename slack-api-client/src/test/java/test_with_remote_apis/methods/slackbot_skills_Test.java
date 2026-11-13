package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.canvases.CanvasesCreateResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsCreateResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsInfoResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsUpdateResponse;
import com.slack.api.model.canvas.CanvasDocumentContent;
import com.slack.api.model.slackbot.Skill;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsUpdateRequest;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class slackbot_skills_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("slackbot.skills.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    // Cleans up test skills with a raw call until slackbot.skills.delete is available
    static void deleteSkill(MethodsClient client, String token, String skillId) throws IOException {
        client.runPostFormWithToken(new FormBody.Builder().add("skill_id", skillId), "slackbot.skills.delete", token).close();
    }

    @Test
    public void skills() throws Exception {
        MethodsClient client = slack.methods(botToken);

        SlackbotSkillsCreateResponse creation = client.slackbotSkillsCreate(r -> r
                .displayName("java-slack-sdk test skill " + System.currentTimeMillis())
                .instructions("# Purpose\nTest skill created by the java-slack-sdk tests. Safe to delete.")
                .description("Test skill for SDK development.")
                .category("engineering")
                .examplePrompt("Run {skill} now"));
        assertThat(creation.getError(), is(nullValue()));
        Skill skill = creation.getSkill();
        assertThat(skill.getId(), is(notNullValue()));
        String skillId = skill.getId();

        try {
            SlackbotSkillsInfoResponse info = client.slackbotSkillsInfo(r -> r.skillId(skillId));
            assertThat(info.getError(), is(nullValue()));
            assertThat(info.getSkill().getInstructions(), is(notNullValue()));

            SlackbotSkillsUpdateResponse update = client.slackbotSkillsUpdate(r -> r
                    .skillId(skillId)
                    .description("Updated test skill for SDK development.")
                    .mcpServers(Collections.emptyList()));
            assertThat(update.getError(), is(nullValue()));
            assertThat(update.getSkill().getDescription(), is("Updated test skill for SDK development."));

            SlackbotSkillsUpdateResponse invalidUpdate = client.slackbotSkillsUpdate(r -> r
                    .skillId(skillId)
                    .mcpServers(Arrays.asList(SlackbotSkillsUpdateRequest.McpServer.builder()
                            .serviceType("app")
                            .appId("A0123ABCD")
                            .serverId("Amcp0456EFGH")
                            .build())));
            assertThat(invalidUpdate.getError(), is("mcp_server_not_found"));
            assertThat(invalidUpdate.getInvalidMcpServers().get(0).getAppId(), is("A0123ABCD"));
        } finally {
            deleteSkill(client, botToken, skillId);
        }
    }

    @Test
    public void skills_canvas() throws Exception {
        MethodsClient client = slack.methods(botToken);

        CanvasesCreateResponse canvas = client.canvasesCreate(r -> r
                .title("java-slack-sdk canvas skill " + System.currentTimeMillis())
                .documentContent(CanvasDocumentContent.builder()
                        .markdown("# Purpose\nCanvas converted to a skill by the java-slack-sdk tests. Safe to delete.")
                        .build()));
        assertThat(canvas.getError(), is(nullValue()));
        String canvasId = canvas.getCanvasId();

        SlackbotSkillsCreateResponse creation = client.slackbotSkillsCreate(r -> r
                .displayName("java-slack-sdk canvas skill")
                .canvasId(canvasId));
        assertThat(creation.getError(), is(nullValue()));
        assertThat(creation.getSkill().getId(), is(canvasId));

        Thread.sleep(3000L); // A converted canvas can briefly read as skill_not_found

        deleteSkill(client, botToken, canvasId);
    }

    @Test
    public void skills_async() throws Exception {
        AsyncMethodsClient client = slack.methodsAsync(botToken);

        SlackbotSkillsInfoResponse missing = client.slackbotSkillsInfo(r -> r.skillId("F00000000")).get();
        assertThat(missing.getError(), is("skill_not_found"));
    }
}
