package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsCreateRequest;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsCreateResponse;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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
                .mcpServers(Arrays.asList(SlackbotSkillsCreateRequest.McpServer.builder()
                        .serviceType("app")
                        .appId("A0123ABCD")
                        .serverId("Amcp0456EFGH")
                        .build()))
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).slackbotSkillsCreate(r -> r
                .displayName("Daily priorities")
                .canvasId("F0123ABCD")
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
                .mcpServers(Arrays.asList(SlackbotSkillsCreateRequest.McpServer.builder()
                        .serviceType("app")
                        .appId("A0123ABCD")
                        .serverId("Amcp0456EFGH")
                        .build()))
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsCreate(r -> r
                .displayName("Daily priorities")
                .canvasId("F0123ABCD")
        ).get().isOk(), is(true));
    }

    @Test
    public void slackbotSkillsCreateResponse() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());

        String json = "{\n" +
                "  \"ok\": true,\n" +
                "  \"skill\": {\n" +
                "    \"id\": \"F0123ABCD\",\n" +
                "    \"type\": \"canvas\",\n" +
                "    \"display_name\": \"Daily priorities\",\n" +
                "    \"name\": null,\n" +
                "    \"description\": \"Reviews information from Slack and your calendar to suggest priorities for the day.\",\n" +
                "    \"category\": \"productivity\",\n" +
                "    \"example_prompt\": \"Run {skill} for today\",\n" +
                "    \"instructions\": null,\n" +
                "    \"visibility\": \"invite_only\",\n" +
                "    \"is_official\": false,\n" +
                "    \"access\": \"owner\",\n" +
                "    \"mcp_servers\": [\n" +
                "      {\n" +
                "        \"service_type\": \"app\",\n" +
                "        \"display_name\": \"Google Calendar\",\n" +
                "        \"is_available\": true,\n" +
                "        \"app_id\": \"A0123ABCD\",\n" +
                "        \"server_id\": \"Amcp0456EFGH\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"usage_count\": 0,\n" +
                "    \"library_count\": 0,\n" +
                "    \"creator_id\": \"U0123ABCD\",\n" +
                "    \"date_created\": 1794467151,\n" +
                "    \"date_updated\": null,\n" +
                "    \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"\n" +
                "  }\n" +
                "}\n";
        SlackbotSkillsCreateResponse response = gson.fromJson(json, SlackbotSkillsCreateResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkill().getId(), is("F0123ABCD"));
        assertThat(response.getSkill().getType(), is("canvas"));
        assertThat(response.getSkill().getDisplayName(), is("Daily priorities"));
        assertThat(response.getSkill().getName(), is(nullValue()));
        assertThat(response.getSkill().getInstructions(), is(nullValue()));
        assertThat(response.getSkill().getVisibility(), is("invite_only"));
        assertThat(response.getSkill().getIsOfficial(), is(false));
        assertThat(response.getSkill().getAccess(), is("owner"));
        assertThat(response.getSkill().getMcpServers().get(0).getServiceType(), is("app"));
        assertThat(response.getSkill().getMcpServers().get(0).getAppId(), is("A0123ABCD"));
        assertThat(response.getSkill().getMcpServers().get(0).getServerId(), is("Amcp0456EFGH"));
        assertThat(response.getSkill().getMcpServers().get(0).getDisplayName(), is("Google Calendar"));
        assertThat(response.getSkill().getMcpServers().get(0).getIsAvailable(), is(true));
        assertThat(response.getSkill().getUsageCount(), is(0));
        assertThat(response.getSkill().getCreatorId(), is("U0123ABCD"));
        assertThat(response.getSkill().getDateCreated(), is(1794467151));
        assertThat(response.getSkill().getDateUpdated(), is(nullValue()));
        assertThat(response.getSkill().getPermalink(), is("https://example.slack.com/skills/F0123ABCD"));
    }
}
