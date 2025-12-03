package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.slack_lists.SlackListsAccessDeleteResponse;
import com.slack.api.methods.response.slack_lists.SlackListsAccessSetResponse;
import com.slack.api.methods.response.slack_lists.SlackListsCreateResponse;
import com.slack.api.methods.response.slack_lists.SlackListsDownloadGetResponse;
import com.slack.api.methods.response.slack_lists.SlackListsDownloadStartResponse;
import com.slack.api.methods.response.slack_lists.SlackListsItemsCreateResponse;
import com.slack.api.methods.response.slack_lists.SlackListsItemsDeleteResponse;
import com.slack.api.methods.response.slack_lists.SlackListsItemsDeleteMultipleResponse;
import com.slack.api.methods.response.slack_lists.SlackListsItemsInfoResponse;
import com.slack.api.methods.response.slack_lists.SlackListsItemsListResponse;
import com.slack.api.methods.response.slack_lists.SlackListsItemsUpdateResponse;
import com.slack.api.methods.response.slack_lists.SlackListsUpdateResponse;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.element.RichTextSectionElement;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    String channelId = System.getenv(Constants.SLACK_SDK_TEST_SHARED_CHANNEL_ID);

    @Test
    public void fullSlackListsWorkflow() throws IOException, SlackApiException {
        // create list
        log.info("Creating list...");
        SlackListsCreateResponse createResponse = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Test List - SlackLists API")
                .descriptionBlocks(List.of(RichTextBlock.builder()
                        .elements(List.of(RichTextSectionElement.builder()
                                .elements(List.of(RichTextSectionElement.Text.builder()
                                        .text("List to keep track of tasks!")
                                        .build()))
                                .build()))
                        .build()))
                .schema(List.of(
                        Map.of(
                                "key", "task_name",
                                "name", "Task Name",
                                "type", "text",
                                "is_primary_column", true),
                        Map.of(
                                "key", "due_date",
                                "name", "Due Date",
                                "type", "date"),
                        Map.of(
                                "key", "status",
                                "name", "Status",
                                "type", "select",
                                "options", Map.of(
                                        "choices", List.of(
                                                Map.of("value", "not_started", "label", "Not Started", "color", "red"),
                                                Map.of("value", "in_progress", "label", "In Progress", "color", "yellow"),
                                                Map.of("value", "completed", "label", "Completed", "color", "green")))),
                        Map.of(
                                "key", "assignee",
                                "name", "Assignee",
                                "type", "user"))));

        log.info("List created: {}", createResponse);
        assertThat(createResponse.getError(), is(nullValue()));
        assertThat(createResponse.isOk(), is(true));

        String listId = createResponse.getListId();
        assertThat(listId, is(notNullValue()));

        Map<String, String> keyToId = new HashMap<>();
        if (createResponse.getListMetadata() != null
                && createResponse.getListMetadata().getSchema() != null) {
            createResponse.getListMetadata().getSchema().forEach(col -> {
                keyToId.put(col.getKey(), col.getId());
            });
        }
        String taskNameColId = keyToId.get("task_name");
        log.info("Column IDs: {}", keyToId);
        
        // set access 
        log.info("Setting access...");
            SlackListsAccessSetResponse accessSetResponse = slack.methods().slackListsAccessSet(r -> r
                    .token(botToken)
                    .listId(listId)
                    .accessLevel("write")
                    .channelIds(List.of(channelId)));
            log.info("Access set: {}", accessSetResponse);
            assertThat(accessSetResponse.getError(), is(nullValue()));
            assertThat(accessSetResponse.isOk(), is(true));

        try {
           // create an item
            log.info("Creating item...");
            SlackListsItemsCreateResponse createItemResponse = slack.methods().slackListsItemsCreate(r -> r
                    .token(botToken)
                    .listId(listId)
                    .initialFields(List.of(Map.of(
                            "column_id", taskNameColId,
                            "rich_text", List.of(Map.of(
                                    "type", "rich_text",
                                    "elements", List.of(Map.of(
                                            "type", "rich_text_section",
                                            "elements", List.of(Map.of(
                                                    "type", "text",
                                                    "text", "Test task item"))))))))));
            log.info("Item created: {}", createItemResponse);
            assertThat(createItemResponse.getError(), is(nullValue()));
            assertThat(createItemResponse.isOk(), is(true));
            assertThat(createItemResponse.getItem(), is(notNullValue()));

            String itemId = createItemResponse.getItem().getId();
            assertThat(itemId, is(notNullValue()));

            // get item info
            log.info("Getting item info...");
            SlackListsItemsInfoResponse itemInfoResponse = slack.methods().slackListsItemsInfo(r -> r
                    .token(botToken)
                    .listId(listId)
                    .id(itemId)
                    .includeIsSubscribed(true));
            log.info("Item info retrieved: {}", itemInfoResponse);
            assertThat(itemInfoResponse.getError(), is(nullValue()));
            assertThat(itemInfoResponse.isOk(), is(true));

            // update item
            log.info("Updating item...");
            SlackListsItemsUpdateResponse updateItemResponse = slack.methods().slackListsItemsUpdate(r -> r
                    .token(botToken)
                    .listId(listId)
                    .cells(List.of(Map.of(
                            "row_id", itemId,
                            "column_id", taskNameColId,
                            "rich_text", List.of(Map.of(
                                    "type", "rich_text",
                                    "elements", List.of(Map.of(
                                            "type", "rich_text_section",
                                            "elements", List.of(Map.of(
                                                    "type", "text",
                                                    "text", "new task name"))))))))));
            log.info("Item updated: {}", updateItemResponse);
            assertThat(updateItemResponse.getError(), is(nullValue()));
            assertThat(updateItemResponse.isOk(), is(true));

            // list items
            log.info("Listing items...");
            SlackListsItemsListResponse listItemsResponse = slack.methods().slackListsItemsList(r -> r
                    .token(botToken)
                    .listId(listId)
                    .limit(50));
            log.info("Items listed: {}", listItemsResponse);
            assertThat(listItemsResponse.getError(), is(nullValue()));
            assertThat(listItemsResponse.isOk(), is(true));
            assertThat(listItemsResponse.getItems(), is(notNullValue()));

            // start download
            log.info("Starting download...");
            SlackListsDownloadStartResponse downloadStartResponse = slack.methods().slackListsDownloadStart(r -> r
                    .token(botToken)
                    .listId(listId)
                    .includeArchived(false));
            log.info("Download started: {}", downloadStartResponse);
            assertThat(downloadStartResponse.getError(), is(nullValue()));
            assertThat(downloadStartResponse.isOk(), is(true));

            String jobId = downloadStartResponse.getJobId();
            if (jobId != null) {
                // get download status
                log.info("Getting download status...");
                SlackListsDownloadGetResponse downloadGetResponse = slack.methods().slackListsDownloadGet(r -> r
                        .token(botToken)
                        .listId(listId)
                        .jobId(jobId));
                log.info("Download status retrieved: {}", downloadGetResponse);
                assertThat(downloadGetResponse.getError(), is(nullValue()));
                assertThat(downloadGetResponse.isOk(), is(true));
            }
             
            // delete the item
            log.info("Deleting item...");
            SlackListsItemsDeleteResponse deleteItemResponse = slack.methods().slackListsItemsDelete(r -> r
                    .token(botToken)
                    .listId(listId)
                    .id(itemId));
            log.info("Item deleted: {}", deleteItemResponse);
            assertThat(deleteItemResponse.getError(), is(nullValue()));
            assertThat(deleteItemResponse.isOk(), is(true));

            // update list
            log.info("Updating list...");
            SlackListsUpdateResponse updateResponse = slack.methods().slackListsUpdate(r -> r
                    .token(botToken)
                    .id(listId)
                    .name("Updated Test List")
                    .todoMode(true));
            log.info("List updated: {}", updateResponse);
            assertThat(updateResponse.getError(), is(nullValue()));
            assertThat(updateResponse.isOk(), is(true));

            // delete access
            log.info("Deleting access...");
            SlackListsAccessDeleteResponse accessDeleteResponse = slack.methods().slackListsAccessDelete(r -> r
                    .token(botToken)
                    .listId(listId)
                    .channelIds(List.of(channelId)));
            log.info("Access removed: {}", accessDeleteResponse);
            assertThat(accessDeleteResponse.getError(), is(nullValue()));
            assertThat(accessDeleteResponse.isOk(), is(true));

        } finally {
            log.info("Slack Lists API workflow completed for list: {}", listId);
        }
    }

    @Test
    public void fullSlackListsWorkflow_async() throws Exception {
        // 1. Create a list
        log.info("Creating list (async)...");
        SlackListsCreateResponse createResponse = slack.methodsAsync().slackListsCreate(r -> r
                .token(botToken)
                .name("Test List Async - SlackLists API")
                .todoMode(true)).get();

        log.info("List created: {}", createResponse);
        assertThat(createResponse.getError(), is(nullValue()));
        assertThat(createResponse.isOk(), is(true));

        String listId = createResponse.getListId();
        assertThat(listId, is(notNullValue()));

        try {
            // create an item
            log.info("Creating item (async)...");
            SlackListsItemsCreateResponse createItemResponse = slack.methodsAsync().slackListsItemsCreate(r -> r
                    .token(botToken)
                    .listId(listId)).get();
            log.info("Item created: {}", createItemResponse);
            assertThat(createItemResponse.getError(), is(nullValue()));
            assertThat(createItemResponse.isOk(), is(true));

            if (createItemResponse.getItem() != null) {
                String itemId = createItemResponse.getItem().getId();

                // get item info
                log.info("Getting item info (async)...");
                SlackListsItemsInfoResponse itemInfoResponse = slack.methodsAsync().slackListsItemsInfo(r -> r
                        .token(botToken)
                        .listId(listId)
                        .id(itemId)).get();
                log.info("Item info: {}", itemInfoResponse);
                assertThat(itemInfoResponse.getError(), is(nullValue()));
                assertThat(itemInfoResponse.isOk(), is(true));

                // list items
                log.info("Listing items (async)...");
                SlackListsItemsListResponse listItemsResponse = slack.methodsAsync().slackListsItemsList(r -> r
                        .token(botToken)
                        .listId(listId)
                        .limit(50)).get();
                log.info("Items listed: {}", listItemsResponse);
                assertThat(listItemsResponse.getError(), is(nullValue()));
                assertThat(listItemsResponse.isOk(), is(true));

                // delete the item
                log.info("Deleting item (async)...");
                SlackListsItemsDeleteResponse deleteItemResponse = slack.methodsAsync().slackListsItemsDelete(r -> r
                        .token(botToken)
                        .listId(listId)
                        .id(itemId)).get();
                log.info("Item deleted: {}", deleteItemResponse);
                assertThat(deleteItemResponse.getError(), is(nullValue()));
                assertThat(deleteItemResponse.isOk(), is(true));
            }

        } finally {
            log.info("Async Slack Lists API workflow completed for list: {}", listId);
        }
    }

    @Test
    public void itemsDeleteMultiple() throws IOException, SlackApiException {
        // create a list and the items to delete
        SlackListsCreateResponse createResponse = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Test Delete Multiple"));
        assertThat(createResponse.isOk(), is(true));

        String listId = createResponse.getListId();

        // make multiple items
        SlackListsItemsCreateResponse item1 = slack.methods().slackListsItemsCreate(r -> r
                .token(botToken)
                .listId(listId));
        SlackListsItemsCreateResponse item2 = slack.methods().slackListsItemsCreate(r -> r
                .token(botToken)
                .listId(listId));

        assertThat(item1.isOk(), is(true));
        assertThat(item2.isOk(), is(true));

        String itemId1 = item1.getItem().getId();
        String itemId2 = item2.getItem().getId();

        // delete multiple items
        log.info("Deleting multiple items...");
        SlackListsItemsDeleteMultipleResponse deleteMultipleResponse = slack.methods().slackListsItemsDeleteMultiple(r -> r
                .token(botToken)
                .listId(listId)
                .ids(List.of(itemId1, itemId2)));
        log.info("Multiple items deleted: {}", deleteMultipleResponse);
        assertThat(deleteMultipleResponse.getError(), is(nullValue()));
        assertThat(deleteMultipleResponse.isOk(), is(true));
    }

}

