package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.slacklists.SlackListsAccessDeleteResponse;
import com.slack.api.methods.response.slacklists.SlackListsAccessSetResponse;
import com.slack.api.methods.response.slacklists.SlackListsCreateResponse;
import com.slack.api.methods.response.slacklists.SlackListsDownloadGetResponse;
import com.slack.api.methods.response.slacklists.SlackListsDownloadStartResponse;
import com.slack.api.methods.response.slacklists.SlackListsItemsCreateResponse;
import com.slack.api.methods.response.slacklists.SlackListsItemsDeleteResponse;
import com.slack.api.methods.response.slacklists.SlackListsItemsDeleteMultipleResponse;
import com.slack.api.methods.response.slacklists.SlackListsItemsInfoResponse;
import com.slack.api.methods.response.slacklists.SlackListsItemsListResponse;
import com.slack.api.methods.response.slacklists.SlackListsItemsUpdateResponse;
import com.slack.api.methods.response.slacklists.SlackListsUpdateResponse;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class slacklists_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

   @BeforeClass
   public static void setUp() throws Exception {
       SlackTestConfig.initializeRawJSONDataFiles("slackLists.*");
   }

   @AfterClass
   public static void tearDown() throws InterruptedException {
       SlackTestConfig.awaitCompletion(testConfig);
   }

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    /*
     *  remove the @Ignore from the tests after setting up botToken
     */
    @Test
    @Ignore
    public void create() throws IOException, SlackApiException {
        SlackListsCreateResponse response = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Backlog"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void create_async() throws Exception {
        SlackListsCreateResponse response = slack.methodsAsync().slackListsCreate(r -> r
                .token(botToken)
                .name("Backlog")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void create_with_todo_mode() throws IOException, SlackApiException {
        SlackListsCreateResponse response = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Backlog")
                .todoMode(true));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void update() throws IOException, SlackApiException {
        SlackListsUpdateResponse response = slack.methods().slackListsUpdate(r -> r
                .token(botToken)
                .id("F1234567")
                .name("Updated Backlog")
                .todoMode(true));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void update_async() throws Exception {
        SlackListsUpdateResponse response = slack.methodsAsync().slackListsUpdate(r -> r
                .token(botToken)
                .id("F1234567")
                .name("Updated Backlog")
                .todoMode(true)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void accessSet() throws IOException, SlackApiException {
        SlackListsAccessSetResponse response = slack.methods().slackListsAccessSet(r -> r
                .token(botToken)
                .listId("F1234567")
                .accessLevel("write"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void accessSet_async() throws Exception {
        SlackListsAccessSetResponse response = slack.methodsAsync().slackListsAccessSet(r -> r
                .token(botToken)
                .listId("F1234567")
                .accessLevel("write")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void accessDelete() throws IOException, SlackApiException {
        SlackListsAccessDeleteResponse response = slack.methods().slackListsAccessDelete(r -> r
                .token(botToken)
                .listId("F1234567"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void accessDelete_async() throws Exception {
        SlackListsAccessDeleteResponse response = slack.methodsAsync().slackListsAccessDelete(r -> r
                .token(botToken)
                .listId("F1234567")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void downloadStart() throws IOException, SlackApiException {
        SlackListsDownloadStartResponse response = slack.methods().slackListsDownloadStart(r -> r
                .token(botToken)
                .listId("F1234567")
                .includeArchived(false));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void downloadStart_async() throws Exception {
        SlackListsDownloadStartResponse response = slack.methodsAsync().slackListsDownloadStart(r -> r
                .token(botToken)
                .listId("F1234567")
                .includeArchived(false)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void downloadGet() throws IOException, SlackApiException {
        SlackListsDownloadGetResponse response = slack.methods().slackListsDownloadGet(r -> r
                .token(botToken)
                .listId("F1234567")
                .jobId("job123"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void downloadGet_async() throws Exception {
        SlackListsDownloadGetResponse response = slack.methodsAsync().slackListsDownloadGet(r -> r
                .token(botToken)
                .listId("F1234567")
                .jobId("job123")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsCreate() throws IOException, SlackApiException {
        SlackListsItemsCreateResponse response = slack.methods().slackListsItemsCreate(r -> r
                .token(botToken)
                .listId("F1234567"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItem(), is(notNullValue()));
        assertThat(response.getItem().getId(), is(notNullValue()));
    }

    @Test
    @Ignore
    public void itemsCreate_async() throws Exception {
        SlackListsItemsCreateResponse response = slack.methodsAsync().slackListsItemsCreate(r -> r
                .token(botToken)
                .listId("F1234567")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItem(), is(notNullValue()));
    }

    @Test
    @Ignore
    public void itemsInfo() throws IOException, SlackApiException {
        SlackListsItemsInfoResponse response = slack.methods().slackListsItemsInfo(r -> r
                .token(botToken)
                .listId("F1234567")
                .id("Rec018ALE9718")
                .includeIsSubscribed(true));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsInfo_async() throws Exception {
        SlackListsItemsInfoResponse response = slack.methodsAsync().slackListsItemsInfo(r -> r
                .token(botToken)
                .listId("F1234567")
                .id("Rec018ALE9718")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsList() throws IOException, SlackApiException {
        SlackListsItemsListResponse response = slack.methods().slackListsItemsList(r -> r
                .token(botToken)
                .limit(100)
                .archived(false));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItems(), is(notNullValue()));
        assertThat(response.getResponseMetadata(), is(notNullValue()));
    }

    @Test
    @Ignore
    public void itemsList_async() throws Exception {
        SlackListsItemsListResponse response = slack.methodsAsync().slackListsItemsList(r -> r
                .token(botToken)
                .limit(100)
                .archived(false)).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
        assertThat(response.getItems(), is(notNullValue()));
    }

    @Test
    @Ignore
    public void itemsUpdate() throws IOException, SlackApiException {
        SlackListsItemsUpdateResponse response = slack.methods().slackListsItemsUpdate(r -> r
                .token(botToken)
                .listId("F1234567"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsUpdate_async() throws Exception {
        SlackListsItemsUpdateResponse response = slack.methodsAsync().slackListsItemsUpdate(r -> r
                .token(botToken)
                .listId("F1234567")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsDelete() throws IOException, SlackApiException {
        SlackListsItemsDeleteResponse response = slack.methods().slackListsItemsDelete(r -> r
                .token(botToken)
                .listId("F1234567")
                .id("Rec018ALE9718"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsDelete_async() throws Exception {
        SlackListsItemsDeleteResponse response = slack.methodsAsync().slackListsItemsDelete(r -> r
                .token(botToken)
                .listId("F1234567")
                .id("Rec018ALE9718")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsDeleteMultiple() throws IOException, SlackApiException {
        SlackListsItemsDeleteMultipleResponse response = slack.methods().slackListsItemsDeleteMultiple(r -> r
                .token(botToken)
                .listId("F1234567"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

    @Test
    @Ignore
    public void itemsDeleteMultiple_async() throws Exception {
        SlackListsItemsDeleteMultipleResponse response = slack.methodsAsync().slackListsItemsDeleteMultiple(r -> r
                .token(botToken)
                .listId("F1234567")).get();
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.isOk(), is(true));
    }

}

