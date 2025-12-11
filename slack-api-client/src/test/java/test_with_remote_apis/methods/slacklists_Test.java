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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.slack.api.model.list.ListColumn;
import com.slack.api.model.list.ListColumnOptions;
import com.slack.api.model.list.ListRecord;

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
        // Build schema columns
        ListColumn taskNameCol = ListColumn.builder()
                .key("task_name")
                .name("Task Name")
                .type("text")
                .primaryColumn(true)
                .build();

        ListColumn dueDateCol = ListColumn.builder()
                .key("due_date")
                .name("Due Date")
                .type("date")
                .build();

        ListColumnOptions.Choice choice1 = ListColumnOptions.Choice.builder()
                .value("not_started")
                .label("Not Started")
                .color("red")
                .build();

        ListColumnOptions.Choice choice2 = ListColumnOptions.Choice.builder()
                .value("in_progress")
                .label("In Progress")
                .color("yellow")
                .build();

        ListColumnOptions.Choice choice3 = ListColumnOptions.Choice.builder()
                .value("completed")
                .label("Completed")
                .color("green")
                .build();

        ListColumnOptions statusOptions = ListColumnOptions.builder()
                .choices(Arrays.asList(choice1, choice2, choice3))
                .build();

        ListColumn statusCol = ListColumn.builder()
                .key("status")
                .name("Status")
                .type("select")
                .options(statusOptions)
                .build();

        ListColumn assigneeCol = ListColumn.builder()
                .key("assignee")
                .name("Assignee")
                .type("user")
                .build();

        // create list
        SlackListsCreateResponse createResponse = slack.methods().slackListsCreate(r -> r
                .token(botToken)
                .name("Test List - SlackLists API")
                .descriptionBlocks(Arrays.asList(RichTextBlock.builder()
                        .elements(Arrays.asList(RichTextSectionElement.builder()
                                .elements(Arrays.asList(RichTextSectionElement.Text.builder()
                                        .text("List to keep track of tasks!")
                                        .build()))
                                .build()))
                        .build()))
                .schema(Arrays.asList(taskNameCol, dueDateCol, statusCol, assigneeCol)));

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
      
        // set access 
        SlackListsAccessSetResponse accessSetResponse = slack.methods().slackListsAccessSet(r -> r
                .token(botToken)
                .listId(listId)
                .accessLevel("write")
                .channelIds(Arrays.asList(channelId)));
        assertThat(accessSetResponse.getError(), is(nullValue()));
        assertThat(accessSetResponse.isOk(), is(true));

        // Build initial fields for item creation
        ListRecord.Field field = ListRecord.Field.builder()
                .columnId(taskNameColId)
                .richText(Arrays.asList(RichTextBlock.builder()
                        .elements(Arrays.asList(RichTextSectionElement.builder()
                                .elements(Arrays.asList(RichTextSectionElement.Text.builder()
                                        .text("Test task item")
                                        .build()))
                                .build()))
                        .build()))
                .build();

        // create an item
        SlackListsItemsCreateResponse createItemResponse = slack.methods().slackListsItemsCreate(r -> r
                .token(botToken)
                .listId(listId)
                .initialFields(Arrays.asList(field)));
        assertThat(createItemResponse.getError(), is(nullValue()));
        assertThat(createItemResponse.isOk(), is(true));
        assertThat(createItemResponse.getItem(), is(notNullValue()));

        String itemId = createItemResponse.getItem().getId();
        assertThat(itemId, is(notNullValue()));

        // get item info
        SlackListsItemsInfoResponse itemInfoResponse = slack.methods().slackListsItemsInfo(r -> r
                .token(botToken)
                .listId(listId)
                .id(itemId)
                .includeIsSubscribed(true));
        assertThat(itemInfoResponse.getError(), is(nullValue()));
        assertThat(itemInfoResponse.isOk(), is(true));

        // Build update cell
        ListRecord.CellUpdate cell = ListRecord.CellUpdate.builder()
                .rowId(itemId)
                .columnId(taskNameColId)
                .richText(Arrays.asList(RichTextBlock.builder()
                        .elements(Arrays.asList(RichTextSectionElement.builder()
                                .elements(Arrays.asList(RichTextSectionElement.Text.builder()
                                        .text("new task name")
                                        .build()))
                                .build()))
                        .build()))
                .build();

        // update item
        SlackListsItemsUpdateResponse updateItemResponse = slack.methods().slackListsItemsUpdate(r -> r
                .token(botToken)
                .listId(listId)
                .cells(Arrays.asList(cell)));
        assertThat(updateItemResponse.getError(), is(nullValue()));
        assertThat(updateItemResponse.isOk(), is(true));

        // list items
        SlackListsItemsListResponse listItemsResponse = slack.methods().slackListsItemsList(r -> r
                .token(botToken)
                .listId(listId)
                .limit(50));
        assertThat(listItemsResponse.getError(), is(nullValue()));
        assertThat(listItemsResponse.isOk(), is(true));
        assertThat(listItemsResponse.getItems(), is(notNullValue()));

        // start download
        SlackListsDownloadStartResponse downloadStartResponse = slack.methods().slackListsDownloadStart(r -> r
                .token(botToken)
                .listId(listId)
                .includeArchived(false));
        assertThat(downloadStartResponse.getError(), is(nullValue()));
        assertThat(downloadStartResponse.isOk(), is(true));

        String jobId = downloadStartResponse.getJobId();
        if (jobId != null) {
            // get download status
            SlackListsDownloadGetResponse downloadGetResponse = slack.methods().slackListsDownloadGet(r -> r
                    .token(botToken)
                    .listId(listId)
                    .jobId(jobId));
            assertThat(downloadGetResponse.getError(), is(nullValue()));
            assertThat(downloadGetResponse.isOk(), is(true));
        }

        // delete the item
        SlackListsItemsDeleteResponse deleteItemResponse = slack.methods().slackListsItemsDelete(r -> r
                .token(botToken)
                .listId(listId)
                .id(itemId));
        assertThat(deleteItemResponse.getError(), is(nullValue()));
        assertThat(deleteItemResponse.isOk(), is(true));

        // update list
        SlackListsUpdateResponse updateResponse = slack.methods().slackListsUpdate(r -> r
                .token(botToken)
                .id(listId)
                .name("Updated Test List")
                .todoMode(true));
        assertThat(updateResponse.getError(), is(nullValue()));
        assertThat(updateResponse.isOk(), is(true));

        // delete access
        SlackListsAccessDeleteResponse accessDeleteResponse = slack.methods().slackListsAccessDelete(r -> r
                .token(botToken)
                .listId(listId)
                .channelIds(Arrays.asList(channelId)));
        assertThat(accessDeleteResponse.getError(), is(nullValue()));
        assertThat(accessDeleteResponse.isOk(), is(true));

        log.info("Slack Lists API workflow completed for list: {}", listId);
    }

    @Test
    public void fullSlackListsWorkflow_async() throws Exception {
        // 1. Create a list
        SlackListsCreateResponse createResponse = slack.methodsAsync().slackListsCreate(r -> r
                .token(botToken)
                .name("Test List Async - SlackLists API")
                .todoMode(true)).get();

        assertThat(createResponse.getError(), is(nullValue()));
        assertThat(createResponse.isOk(), is(true));

        String listId = createResponse.getListId();
        assertThat(listId, is(notNullValue()));

        // create an item
        SlackListsItemsCreateResponse createItemResponse = slack.methodsAsync().slackListsItemsCreate(r -> r
                .token(botToken)
                .listId(listId)).get();
        assertThat(createItemResponse.getError(), is(nullValue()));
        assertThat(createItemResponse.isOk(), is(true));

        if (createItemResponse.getItem() != null) {
                String itemId = createItemResponse.getItem().getId();

                // get item info
                SlackListsItemsInfoResponse itemInfoResponse = slack.methodsAsync().slackListsItemsInfo(r -> r
                        .token(botToken)
                        .listId(listId)
                        .id(itemId)).get();
                assertThat(itemInfoResponse.getError(), is(nullValue()));
                assertThat(itemInfoResponse.isOk(), is(true));

                // list items
                SlackListsItemsListResponse listItemsResponse = slack.methodsAsync().slackListsItemsList(r -> r
                        .token(botToken)
                        .listId(listId)
                        .limit(50)).get();
                assertThat(listItemsResponse.getError(), is(nullValue()));
                assertThat(listItemsResponse.isOk(), is(true));

                // delete the item
                SlackListsItemsDeleteResponse deleteItemResponse = slack.methodsAsync().slackListsItemsDelete(r -> r
                        .token(botToken)
                        .listId(listId)
                        .id(itemId)).get();
                assertThat(deleteItemResponse.getError(), is(nullValue()));
                assertThat(deleteItemResponse.isOk(), is(true));
        }
        log.info("Async Slack Lists API workflow completed for list: {}", listId);
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
        SlackListsItemsDeleteMultipleResponse deleteMultipleResponse = slack.methods().slackListsItemsDeleteMultiple(r -> r
                .token(botToken)
                .listId(listId)
                .ids(Arrays.asList(itemId1, itemId2)));
        assertThat(deleteMultipleResponse.getError(), is(nullValue()));
        assertThat(deleteMultipleResponse.isOk(), is(true));
    }

}
