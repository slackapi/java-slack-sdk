package test_with_remote_apis.methods_admin_api;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsListResponse;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsPublishResponse;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsSetAutoEnabledResponse;
import com.slack.api.methods.response.admin.slackbot.skills.AdminSlackbotSkillsUnpublishResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsCreateResponse;
import com.slack.api.methods.response.slackbot.skills.SlackbotSkillsDeleteResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AdminApi_slackbot_skills_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("admin.slackbot.skills.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    static String orgAdminUserToken = System.getenv(Constants.SLACK_SDK_TEST_GRID_ORG_ADMIN_USER_TOKEN);
    static String usergroupId = System.getenv(Constants.SLACK_SDK_TEST_GRID_IDP_USERGROUP_ID_2);

    @Test
    public void list() throws Exception {
        if (orgAdminUserToken != null) {
            MethodsClient client = slack.methods(orgAdminUserToken);
            for (String sort : Arrays.asList("date_updated", "name", "usage_count")) {
                AdminSlackbotSkillsListResponse list = client.adminSlackbotSkillsList(r -> r
                        .autoEnabledStatus("all")
                        .limit(100)
                        .publishStatus("all")
                        .sort(sort)
                        .sortDir("desc"));
                assertThat(list.getError(), is(nullValue()));
                assertThat(list.getSkills(), is(notNullValue()));
                assertThat(list.getResponseMetadata(), is(notNullValue()));
            }
        }
    }

    @Test
    public void publishAndAutoEnable() throws Exception {
        if (orgAdminUserToken != null) {
            MethodsClient client = slack.methods(orgAdminUserToken);

            SlackbotSkillsCreateResponse creation = client.slackbotSkillsCreate(r -> r
                    .category("engineering")
                    .description("Test skill for SDK development.")
                    .displayName("java-slack-sdk admin test skill " + System.currentTimeMillis())
                    .instructions("# Purpose\nTest skill created by the java-slack-sdk tests. Safe to delete."));
            assertThat(creation.getError(), is(nullValue()));
            String skillId = creation.getSkill().getId();

            try {
                Thread.sleep(3000L); // A new skill can briefly read as skill_not_found

                AdminSlackbotSkillsPublishResponse publication = client.adminSlackbotSkillsPublish(r -> r
                        .skillIds(Collections.singletonList(skillId)));
                assertThat(publication.getError(), is(nullValue()));

                AdminSlackbotSkillsPublishResponse partialPublication = client.adminSlackbotSkillsPublish(r -> r
                        .skillIds(Arrays.asList(skillId, "F00000000")));
                assertThat(partialPublication.getError(), is("failed_for_some_skills"));
                assertThat(partialPublication.getFailedSkills().get(0).getSkillId(), is("F00000000"));
                assertThat(partialPublication.getFailedSkills().get(0).getError(), is("skill_not_found"));

                if (usergroupId != null) {
                    AdminSlackbotSkillsSetAutoEnabledResponse namedEntities = client.adminSlackbotSkillsSetAutoEnabled(r -> r
                            .permissionType("named_entities")
                            .skillId(skillId)
                            .usergroupIds(Collections.singletonList(usergroupId)));
                    assertThat(namedEntities.getError(), is(nullValue()));
                    assertThat(namedEntities.getUsergroupIds(), is(Collections.singletonList(usergroupId)));
                }

                AdminSlackbotSkillsSetAutoEnabledResponse missingUsergroup = client.adminSlackbotSkillsSetAutoEnabled(r -> r
                        .permissionType("named_entities")
                        .skillId(skillId)
                        .usergroupIds(Collections.singletonList("S00000000")));
                assertThat(missingUsergroup.getError(), is("usergroup_not_found"));
                assertThat(missingUsergroup.getInvalidUsergroupIds(), is(Collections.singletonList("S00000000")));

                AdminSlackbotSkillsSetAutoEnabledResponse noOne = client.adminSlackbotSkillsSetAutoEnabled(r -> r
                        .permissionType("no_one")
                        .skillId(skillId));
                assertThat(noOne.getError(), is(nullValue()));
                assertThat(noOne.getPermissionType(), is("no_one"));
                assertThat(noOne.getSkillId(), is(skillId));
                assertThat(noOne.getUsergroupIds(), is(Collections.emptyList()));

                AdminSlackbotSkillsUnpublishResponse unpublication = client.adminSlackbotSkillsUnpublish(r -> r
                        .skillIds(Collections.singletonList(skillId)));
                assertThat(unpublication.getError(), is(nullValue()));
            } finally {
                SlackbotSkillsDeleteResponse deletion = client.slackbotSkillsDelete(r -> r.skillId(skillId));
                assertThat(deletion.getError(), is(nullValue()));
            }
        }
    }
}
