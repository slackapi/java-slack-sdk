package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.slackbot.skill_sets.SlackbotSkillSetsCreateResponse;
import com.slack.api.methods.response.slackbot.skill_sets.SlackbotSkillSetsDeleteResponse;
import com.slack.api.methods.response.slackbot.skill_sets.SlackbotSkillSetsInfoResponse;
import com.slack.api.methods.response.slackbot.skill_sets.SlackbotSkillSetsListResponse;
import com.slack.api.methods.response.slackbot.skill_sets.SlackbotSkillSetsUpdateResponse;
import com.slack.api.methods.response.slackbot.skill_sets.items.SlackbotSkillSetsItemsAddResponse;
import com.slack.api.methods.response.slackbot.skill_sets.items.SlackbotSkillSetsItemsRemoveResponse;
import com.slack.api.methods.response.slackbot.skill_sets.library.SlackbotSkillSetsLibraryAddResponse;
import com.slack.api.methods.response.slackbot.skill_sets.library.SlackbotSkillSetsLibraryRemoveResponse;
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
public class slackbot_skill_sets_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("slackbot.skillSets.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void skillSets() throws Exception {
        MethodsClient client = slack.methods(botToken);

        SlackbotSkillsCreateResponse skillCreation = client.slackbotSkillsCreate(r -> r
                .displayName("java-slack-sdk test skill " + System.currentTimeMillis())
                .instructions("# Purpose\nTest skill created by the java-slack-sdk tests. Safe to delete."));
        assertThat(skillCreation.getError(), is(nullValue()));
        String skillId = skillCreation.getSkill().getId();

        SlackbotSkillSetsCreateResponse creation = client.slackbotSkillSetsCreate(r -> r
                .displayName("java-slack-sdk test skill set " + System.currentTimeMillis())
                .description("Test skill set for SDK development."));
        assertThat(creation.getError(), is(nullValue()));
        assertThat(creation.getSkillSet().getId(), is(notNullValue()));
        String skillSetId = creation.getSkillSet().getId();

        Thread.sleep(3000L); // A new skill or skill set can briefly read as not found

        try {
            SlackbotSkillSetsItemsAddResponse addition = client.slackbotSkillSetsItemsAdd(r -> r
                    .skillSetId(skillSetId)
                    .skillIds(Arrays.asList(skillId)));
            assertThat(addition.getError(), is(nullValue()));

            SlackbotSkillSetsInfoResponse info = client.slackbotSkillSetsInfo(r -> r.skillSetId(skillSetId));
            assertThat(info.getError(), is(nullValue()));
            assertThat(info.getItems().get(0).getSkillId(), is(skillId));

            SlackbotSkillSetsUpdateResponse update = client.slackbotSkillSetsUpdate(r -> r
                    .skillSetId(skillSetId)
                    .description("Updated test skill set for SDK development."));
            assertThat(update.getError(), is(nullValue()));
            assertThat(update.getSkillSet().getDescription(), is("Updated test skill set for SDK development."));

            SlackbotSkillSetsListResponse owned = client.slackbotSkillSetsList(r -> r.filter("owned").limit(100));
            assertThat(owned.getError(), is(nullValue()));
            assertThat(owned.getSkillSets(), is(notNullValue()));

            SlackbotSkillSetsLibraryRemoveResponse libraryRemoval = client.slackbotSkillSetsLibraryRemove(r -> r.skillSetId(skillSetId));
            assertThat(libraryRemoval.getError(), is(nullValue()));

            SlackbotSkillSetsLibraryAddResponse libraryAddition = client.slackbotSkillSetsLibraryAdd(r -> r.skillSetId(skillSetId));
            assertThat(libraryAddition.getError(), is(nullValue()));

            SlackbotSkillSetsItemsRemoveResponse removal = client.slackbotSkillSetsItemsRemove(r -> r
                    .skillSetId(skillSetId)
                    .skillIds(Collections.singletonList(skillId)));
            assertThat(removal.getError(), is(nullValue()));
        } finally {
            SlackbotSkillSetsDeleteResponse deletion = client.slackbotSkillSetsDelete(r -> r.skillSetId(skillSetId));
            assertThat(deletion.getError(), is(nullValue()));
            SlackbotSkillsDeleteResponse skillDeletion = client.slackbotSkillsDelete(r -> r.skillId(skillId));
            assertThat(skillDeletion.getError(), is(nullValue()));
        }
    }

    @Test
    public void skillSets_async() throws Exception {
        AsyncMethodsClient client = slack.methodsAsync(botToken);

        for (String filter : Arrays.asList("library", "owned", "editable", "accessible")) {
            SlackbotSkillSetsListResponse list = client.slackbotSkillSetsList(r -> r.filter(filter).limit(1)).get();
            assertThat(list.getError(), is(nullValue()));
            assertThat(list.getSkillSets(), is(notNullValue()));
        }

        SlackbotSkillSetsInfoResponse missing = client.slackbotSkillSetsInfo(r -> r.skillSetId("F00000000")).get();
        assertThat(missing.getError(), is("skill_set_not_found"));
    }
}
