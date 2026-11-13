package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsCreateRequest;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsCreateResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsInfoResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsListResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsUpdateResponse;
import com.slack.api.util.json.GsonFactory;
import config.SlackTestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class SlackbotTest {

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
        assertThat(slack.methods(ValidToken).slackbotSkillsLibraryAdd(r -> r
                .skillId("F0123ABCD")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).slackbotSkillsLibraryRemove(r -> r
                .skillId("F0123ABCD")
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).slackbotSkillsList(r -> r
                .filter("owned")
                .category("productivity")
                .cursor("dGVhbTpDMDYxRkE1UEI=")
                .limit(100)
        ).isOk(), is(true));
        assertThat(slack.methods(ValidToken).slackbotSkillsUpdate(r -> r
                .skillId("F0123ABCD")
                .displayName("Daily priorities")
                .instructions("# Purpose\nSuggest the three most important things to work on today.")
                .description("")
                .category("")
                .examplePrompt("")
                .mcpServers(Collections.emptyList())
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
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsLibraryAdd(r -> r
                .skillId("F0123ABCD")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsLibraryRemove(r -> r
                .skillId("F0123ABCD")
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsList(r -> r
                .filter("owned")
                .category("productivity")
                .cursor("dGVhbTpDMDYxRkE1UEI=")
                .limit(100)
        ).get().isOk(), is(true));
        assertThat(slack.methodsAsync(ValidToken).slackbotSkillsUpdate(r -> r
                .skillId("F0123ABCD")
                .displayName("Daily priorities")
                .instructions("# Purpose\nSuggest the three most important things to work on today.")
                .description("")
                .category("")
                .examplePrompt("")
                .mcpServers(Collections.emptyList())
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

    @Test
    public void slackbotSkillsInfoResponse() {
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
                "    \"instructions\": \"# Daily priorities\\n\\n# Purpose\\n\\nSuggest the three most important things to work on today.\",\n" +
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
                "    \"date_updated\": 1794467151,\n" +
                "    \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"\n" +
                "  }\n" +
                "}\n";
        SlackbotSkillsInfoResponse response = gson.fromJson(json, SlackbotSkillsInfoResponse.class);
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

    @Test
    public void slackbotSkillsListResponse() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());

        String json = "{\n" +
                "  \"ok\": true,\n" +
                "  \"skills\": [\n" +
                "    {\n" +
                "      \"id\": \"F0123ABCD\",\n" +
                "      \"type\": \"canvas\",\n" +
                "      \"display_name\": \"Daily priorities\",\n" +
                "      \"name\": null,\n" +
                "      \"description\": \"Reviews information from Slack and your calendar to suggest priorities for the day.\",\n" +
                "      \"category\": null,\n" +
                "      \"example_prompt\": \"Run {skill} for today\",\n" +
                "      \"instructions\": null,\n" +
                "      \"visibility\": \"invite_only\",\n" +
                "      \"is_official\": false,\n" +
                "      \"access\": \"owner\",\n" +
                "      \"mcp_servers\": [],\n" +
                "      \"usage_count\": 0,\n" +
                "      \"library_count\": 1,\n" +
                "      \"creator_id\": \"U0123ABCD\",\n" +
                "      \"date_created\": 1794467151,\n" +
                "      \"date_updated\": 1794467151,\n" +
                "      \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"response_metadata\": {\n" +
                "    \"next_cursor\": \"dGVhbTpDMDYxRkE1UEI=\"\n" +
                "  }\n" +
                "}\n";
        SlackbotSkillsListResponse response = gson.fromJson(json, SlackbotSkillsListResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkills().get(0).getId(), is("F0123ABCD"));
        assertThat(response.getSkills().get(0).getCategory(), is(nullValue()));
        assertThat(response.getSkills().get(0).getMcpServers(), is(Collections.emptyList()));
        assertThat(response.getSkills().get(0).getLibraryCount(), is(1));
        assertThat(response.getResponseMetadata().getNextCursor(), is("dGVhbTpDMDYxRkE1UEI="));
    }

    @Test
    public void slackbotSkillsUpdateResponse() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());

        String json = "{\n" +
                "  \"ok\": true,\n" +
                "  \"skill\": {\n" +
                "    \"id\": \"F0123ABCD\",\n" +
                "    \"type\": \"canvas\",\n" +
                "    \"display_name\": \"Daily priorities\",\n" +
                "    \"name\": null,\n" +
                "    \"description\": \"Suggests priorities for the day.\",\n" +
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
                "    \"date_updated\": 1794467151,\n" +
                "    \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"\n" +
                "  }\n" +
                "}\n";
        SlackbotSkillsUpdateResponse response = gson.fromJson(json, SlackbotSkillsUpdateResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkill().getDescription(), is("Suggests priorities for the day."));
        assertThat(response.getSkill().getInstructions(), is(nullValue()));
        assertThat(response.getSkill().getDateUpdated(), is(1794467151));
        assertThat(response.getSkill().getMcpServers().get(0).getServiceType(), is("app"));
        assertThat(response.getSkill().getMcpServers().get(0).getAppId(), is("A0123ABCD"));
        assertThat(response.getSkill().getMcpServers().get(0).getServerId(), is("Amcp0456EFGH"));
        assertThat(response.getSkill().getMcpServers().get(0).getDisplayName(), is("Google Calendar"));
        assertThat(response.getSkill().getMcpServers().get(0).getIsAvailable(), is(true));
    }
}
