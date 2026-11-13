package test_locally.api.methods;

import com.google.gson.Gson;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsCreateRequest;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsListResponse;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsPublishResponse;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsSetAutoEnabledResponse;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsUnpublishResponse;
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
    public void adminSlackbotSkillsListResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": true," +
                "  \"skills\": [" +
                "    {" +
                "      \"id\": \"F0123ABCD\"," +
                "      \"display_name\": \"Daily priorities\"," +
                "      \"description\": null," +
                "      \"category\": null," +
                "      \"creator_id\": \"U0123ABCD\"," +
                "      \"date_created\": 1794467171," +
                "      \"date_updated\": 1794467171," +
                "      \"visibility\": \"anyone_can_view\"," +
                "      \"is_official\": true," +
                "      \"usage_count\": 0," +
                "      \"library_count\": 1," +
                "      \"auto_enabled\": {" +
                "        \"permission_type\": \"named_entities\"," +
                "        \"usergroup_ids\": [" +
                "          \"S0123ABCD\"" +
                "        ]" +
                "      }" +
                "    }" +
                "  ]," +
                "  \"response_metadata\": {" +
                "    \"next_cursor\": \"dGVhbTpDMDYxRkE1UEI=\"" +
                "  }" +
                "}";
        AdminSlackbotSkillsListResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, AdminSlackbotSkillsListResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkills().get(0).getId(), is("F0123ABCD"));
        assertThat(response.getSkills().get(0).getDescription(), is(nullValue()));
        assertThat(response.getSkills().get(0).getVisibility(), is("anyone_can_view"));
        assertThat(response.getSkills().get(0).getIsOfficial(), is(true));
        assertThat(response.getSkills().get(0).getLibraryCount(), is(1));
        assertThat(response.getSkills().get(0).getAutoEnabled().getPermissionType(), is("named_entities"));
        assertThat(response.getSkills().get(0).getAutoEnabled().getUsergroupIds(), is(Collections.singletonList("S0123ABCD")));
        assertThat(response.getResponseMetadata().getNextCursor(), is("dGVhbTpDMDYxRkE1UEI="));
    }

    @Test
    public void adminSlackbotSkillsPublishResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": false," +
                "  \"error\": \"failed_for_some_skills\"," +
                "  \"failed_skills\": [" +
                "    {" +
                "      \"skill_id\": \"F0456EFGH\"," +
                "      \"error\": \"skill_not_found\"" +
                "    }" +
                "  ]" +
                "}";
        AdminSlackbotSkillsPublishResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, AdminSlackbotSkillsPublishResponse.class);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("failed_for_some_skills"));
        assertThat(response.getFailedSkills().get(0).getSkillId(), is("F0456EFGH"));
        assertThat(response.getFailedSkills().get(0).getError(), is("skill_not_found"));
    }

    @Test
    public void adminSlackbotSkillsSetAutoEnabledResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": true," +
                "  \"skill_id\": \"F0123ABCD\"," +
                "  \"permission_type\": \"named_entities\"," +
                "  \"usergroup_ids\": [" +
                "    \"S0123ABCD\"" +
                "  ]" +
                "}";
        AdminSlackbotSkillsSetAutoEnabledResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, AdminSlackbotSkillsSetAutoEnabledResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkillId(), is("F0123ABCD"));
        assertThat(response.getPermissionType(), is("named_entities"));
        assertThat(response.getUsergroupIds(), is(Collections.singletonList("S0123ABCD")));

        String errorJson = "{" +
                "  \"ok\": false," +
                "  \"error\": \"usergroup_not_found\"," +
                "  \"invalid_usergroup_ids\": [" +
                "    \"S0456EFGH\"" +
                "  ]" +
                "}";
        AdminSlackbotSkillsSetAutoEnabledResponse errorResponse = GsonFactory.createSnakeCase(strict).fromJson(errorJson, AdminSlackbotSkillsSetAutoEnabledResponse.class);
        assertThat(errorResponse.isOk(), is(false));
        assertThat(errorResponse.getError(), is("usergroup_not_found"));
        assertThat(errorResponse.getInvalidUsergroupIds(), is(Collections.singletonList("S0456EFGH")));
    }

    @Test
    public void adminSlackbotSkillsUnpublishResponse() {
        SlackTestConfig testConfig = SlackTestConfig.getInstance();
        Gson gson = GsonFactory.createSnakeCase(testConfig.getConfig());

        String json = "{\n" +
                "  \"ok\": false,\n" +
                "  \"error\": \"failed_for_some_skills\",\n" +
                "  \"failed_skills\": [\n" +
                "    {\n" +
                "      \"skill_id\": \"F0456EFGH\",\n" +
                "      \"error\": \"skill_not_found\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n";
        AdminSlackbotSkillsUnpublishResponse response = gson.fromJson(json, AdminSlackbotSkillsUnpublishResponse.class);
        assertThat(response.isOk(), is(false));
        assertThat(response.getError(), is("failed_for_some_skills"));
        assertThat(response.getFailedSkills().get(0).getSkillId(), is("F0456EFGH"));
        assertThat(response.getFailedSkills().get(0).getError(), is("skill_not_found"));
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

    @Test
    public void slackbotSkillsListResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": true," +
                "  \"skills\": [" +
                "    {" +
                "      \"id\": \"F0123ABCD\"," +
                "      \"type\": \"canvas\"," +
                "      \"display_name\": \"Daily priorities\"," +
                "      \"name\": null," +
                "      \"description\": \"Reviews information from Slack and your calendar to suggest priorities for the day.\"," +
                "      \"category\": null," +
                "      \"example_prompt\": \"Run {skill} for today\"," +
                "      \"instructions\": null," +
                "      \"visibility\": \"invite_only\"," +
                "      \"is_official\": false," +
                "      \"access\": \"owner\"," +
                "      \"mcp_servers\": []," +
                "      \"usage_count\": 0," +
                "      \"library_count\": 1," +
                "      \"creator_id\": \"U0123ABCD\"," +
                "      \"date_created\": 1794467151," +
                "      \"date_updated\": 1794467151," +
                "      \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"" +
                "    }" +
                "  ]," +
                "  \"response_metadata\": {" +
                "    \"next_cursor\": \"dGVhbTpDMDYxRkE1UEI=\"" +
                "  }" +
                "}";
        SlackbotSkillsListResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, SlackbotSkillsListResponse.class);
        assertThat(response.isOk(), is(true));
        assertThat(response.getSkills().get(0).getId(), is("F0123ABCD"));
        assertThat(response.getSkills().get(0).getCategory(), is(nullValue()));
        assertThat(response.getSkills().get(0).getMcpServers(), is(Collections.emptyList()));
        assertThat(response.getSkills().get(0).getLibraryCount(), is(1));
        assertThat(response.getResponseMetadata().getNextCursor(), is("dGVhbTpDMDYxRkE1UEI="));
    }

    @Test
    public void slackbotSkillsUpdateResponse() {
        SlackConfig strict = new SlackConfig();
        strict.setFailOnUnknownProperties(true);

        String json = "{" +
                "  \"ok\": true," +
                "  \"skill\": {" +
                "    \"id\": \"F0123ABCD\"," +
                "    \"type\": \"canvas\"," +
                "    \"display_name\": \"Daily priorities\"," +
                "    \"name\": null," +
                "    \"description\": \"Suggests priorities for the day.\"," +
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
                "    \"date_updated\": 1794467151," +
                "    \"permalink\": \"https://example.slack.com/skills/F0123ABCD\"" +
                "  }" +
                "}";
        SlackbotSkillsUpdateResponse response = GsonFactory.createSnakeCase(strict).fromJson(json, SlackbotSkillsUpdateResponse.class);
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
