package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsCreateRequest;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsCreateResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsInfoResponse;
import com.slack.api.util.json.GsonFactory;
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
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsInfo(r -> r
                .skillId("F0123ABCD")
        ).get().isOk(), is(true));
    }

    @Test
    public void slackbotSkillsCreateResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": true," +
                "  \"skill\": {" +
                "    \"id\": \"F0123ABCD\"," +
                "    \"type\": \"canvas\"," +
                "    \"display_name\": \"Daily priorities\"," +
                "    \"name\": null," +
                "    \"description\": \"Reviews information from Slack and your calendar to suggest priorities for the day.\"," +
                "    \"category\": \"productivity\"," +
                "    \"example_prompt\": \"Run {skill} for today\"," +
                "    \"instructions\": null," +
                "    \"visibility\": \"invite_only\"," +
                "    \"is_official\": false," +
                "    \"access\": \"owner\"," +
                "    \"mcp_servers\": [" +
                "      {" +
                "        \"service_type\": \"app\"," +
                "        \"display_name\": \"Google Calendar\"," +
                "        \"is_available\": true," +
                "        \"app_id\": \"A0123ABCD\"," +
                "        \"server_id\": \"Amcp0456EFGH\"" +
                "      }" +
                "    ]," +
                "    \"usage_count\": 0," +
                "    \"library_count\": 0," +
                "    \"creator_id\": \"U0123ABCD\"," +
                "    \"date_created\": 1794467151," +
                "    \"date_updated\": null," +
                "    \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"" +
                "  }" +
                "}";
        SlackbotSkillsCreateResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, SlackbotSkillsCreateResponse.class);
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

    @Test
    public void slackbotSkillsInfoResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": true," +
                "  \"skill\": {" +
                "    \"id\": \"F0123ABCD\"," +
                "    \"type\": \"canvas\"," +
                "    \"display_name\": \"Daily priorities\"," +
                "    \"name\": null," +
                "    \"description\": \"Reviews information from Slack and your calendar to suggest priorities for the day.\"," +
                "    \"category\": \"productivity\"," +
                "    \"example_prompt\": \"Run {skill} for today\"," +
                "    \"instructions\": \"# Daily priorities\\n\\n# Purpose\\n\\nSuggest the three most important things to work on today.\"," +
                "    \"visibility\": \"invite_only\"," +
                "    \"is_official\": false," +
                "    \"access\": \"owner\"," +
                "    \"mcp_servers\": [" +
                "      {" +
                "        \"service_type\": \"app\"," +
                "        \"display_name\": \"Google Calendar\"," +
                "        \"is_available\": true," +
                "        \"app_id\": \"A0123ABCD\"," +
                "        \"server_id\": \"Amcp0456EFGH\"" +
                "      }" +
                "    ]," +
                "    \"usage_count\": 0," +
                "    \"library_count\": 0," +
                "    \"creator_id\": \"U0123ABCD\"," +
                "    \"date_created\": 1794467151," +
                "    \"date_updated\": 1794467151," +
                "    \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"" +
                "  }" +
                "}";
        SlackbotSkillsInfoResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, SlackbotSkillsInfoResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkill().getId(), is("F0123ABCD"));
        assertThat(response.getSkill().getInstructions(), is("# Daily priorities\n\n# Purpose\n\nSuggest the three most important things to work on today."));
        assertThat(response.getSkill().getDateUpdated(), is(1794467151));
        assertThat(response.getSkill().getMcpServers().get(0).getServiceType(), is("app"));
        assertThat(response.getSkill().getMcpServers().get(0).getAppId(), is("A0123ABCD"));
        assertThat(response.getSkill().getMcpServers().get(0).getServerId(), is("Amcp0456EFGH"));
        assertThat(response.getSkill().getMcpServers().get(0).getDisplayName(), is("Google Calendar"));
        assertThat(response.getSkill().getMcpServers().get(0).getIsAvailable(), is(true));
    }
}
