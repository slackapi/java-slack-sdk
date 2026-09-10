package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.util.http.SlackHttpClient;
import okhttp3.FormBody;
import okhttp3.Response;
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
        // Resolve a real user id and team id so the schema can carry
        // default_value_typed (user) and emoji_team_id, which the API only
        // echoes back when populated with valid values.
        AuthTestResponse auth = slack.methods(botToken).authTest(r -> r);
        assertThat(auth.getError(), is(nullValue()));
        String selfUserId = auth.getUserId();
        String teamId = auth.getTeamId();

        // Build schema columns
        ListColumn taskNameCol = ListColumn.builder()
                .key("task_name")
                .name("Task Name")
                .type("text")
                .primaryColumn(true)
                .build();

        // A date column with a dateFormat option so the response echoes back date_format
        ListColumn dueDateCol = ListColumn.builder()
                .key("due_date")
                .name("Due Date")
                .type("date")
                .options(ListColumnOptions.builder()
                        .dateFormat("MM/DD/YYYY")
                        .build())
                .build();

        // A number column with precision so the response echoes back precision
        ListColumn estimateCol = ListColumn.builder()
                .key("estimate")
                .name("Estimate")
                .type("number")
                .options(ListColumnOptions.builder()
                        .precision(2)
                        .build())
                .build();

        // A rating column with emoji/max/emoji_team_id so the response echoes those back
        ListColumn ratingCol = ListColumn.builder()
                .key("priority")
                .name("Priority")
                .type("rating")
                .options(ListColumnOptions.builder()
                        .emoji(":star:")
                        .max(5)
                        .emojiTeamId(teamId)
                        .build())
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

        // A user column with notify_users and a typed default so the response
        // echoes back notify_users and default_value_typed (the user variant;
        // the select variant is rejected by slackLists.create).
        ListColumn assigneeCol = ListColumn.builder()
                .key("assignee")
                .name("Assignee")
                .type("user")
                .options(ListColumnOptions.builder()
                        .notifyUsers(true)
                        .defaultValueTyped(ListColumnOptions.DefaultValue.builder()
                                .user(Arrays.asList(selfUserId))
                                .build())
                        .build())
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
                .schema(Arrays.asList(taskNameCol, dueDateCol, estimateCol, ratingCol, statusCol, assigneeCol)));

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
        assertThat(createItemResponse.getItem().getFields(), is(notNullValue()));
        // Columns with typed defaults (e.g. the user column) may also come back populated,
        // so locate the task_name field by column id rather than asserting an exact count.
        ListRecord.Field taskNameField = createItemResponse.getItem().getFields().stream()
                .filter(f -> taskNameColId.equals(f.getColumnId()))
                .findFirst()
                .orElse(null);
        assertThat(taskNameField, is(notNullValue()));
        assertThat(taskNameField.getText(), is("Test task item"));

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

    // ------------------------------------------------------------------------
    // EXPERIMENTAL — proof probe for the ListRecord.Field.message wire shape.
    //
    // Context: slackapi/java-slack-sdk#1587 reports that when a list item
    // carries a "message"-type field, the API can return `message` as an ARRAY
    // of message objects, which the production Gson path fails to deserialize
    // (JsonSyntaxException: Expected BEGIN_OBJECT but was BEGIN_ARRAY). The
    // docs (slackLists.items.create#field-types) show only the request shape
    // (an array of permalink URL strings) and give no response example, so the
    // response shape is unverified in public. This probe observes the real
    // production response shape empirically before we commit to a fix.
    //
    // It does two independent things:
    //   1) captures the RAW JSON of slackLists.items.info and asserts whether
    //      the `message` field value is a JSON array `[` or an object `{`;
    //   2) attempts the TYPED deserialization via the production client and
    //      records whether it throws on that real shape.
    // This method is intentionally standalone and does not modify any SDK
    // source; it is safe to revert wholesale.
    // ------------------------------------------------------------------------
    @Test
    public void messageFieldArrayShapeProbe() throws IOException, SlackApiException {
        MethodsClient methods = slack.methods();

        AuthTestResponse auth = methods.authTest(r -> r.token(botToken));
        assertThat(auth.getError(), is(nullValue()));
        String teamHost = auth.getUrl(); // e.g. https://<team>.slack.com/

        // 1) Post a real message so we have a genuine permalink to reference.
        ChatPostMessageResponse posted = methods.chatPostMessage(r -> r
                .token(botToken)
                .channel(channelId)
                .text("List message-field probe " + System.currentTimeMillis()));
        assertThat(posted.getError(), is(nullValue()));
        assertThat(posted.isOk(), is(true));
        String messageTs = posted.getTs();

        ChatGetPermalinkResponse permalinkResp = methods.chatGetPermalink(r -> r
                .token(botToken)
                .channel(channelId)
                .messageTs(messageTs));
        assertThat(permalinkResp.getError(), is(nullValue()));
        String permalink = permalinkResp.getPermalink();
        assertThat(permalink, is(notNullValue()));
        log.info("[probe] message permalink: {}", permalink);

        // 2) Create a list with a message-type column.
        ListColumn titleCol = ListColumn.builder()
                .key("title").name("Title").type("text").primaryColumn(true).build();
        ListColumn messageCol = ListColumn.builder()
                .key("linked_message").name("Linked Message").type("message").build();

        SlackListsCreateResponse createResponse = methods.slackListsCreate(r -> r
                .token(botToken)
                .name("Message-field probe list")
                .schema(Arrays.asList(titleCol, messageCol)));
        assertThat(createResponse.getError(), is(nullValue()));
        assertThat(createResponse.isOk(), is(true));
        String listId = createResponse.getListId();

        Map<String, String> keyToId = new HashMap<>();
        createResponse.getListMetadata().getSchema().forEach(c -> keyToId.put(c.getKey(), c.getId()));
        String messageColId = keyToId.get("linked_message");
        assertThat(messageColId, is(notNullValue()));

        // grant the bot's channel write access so items can be created
        methods.slackListsAccessSet(r -> r
                .token(botToken).listId(listId).accessLevel("write")
                .channelIds(Arrays.asList(channelId)));

        // 3) Create an item with a primary text field. The message field is
        // set separately below via items.update using the docs-correct request
        // shape (an array of message permalink URL strings), sent as a raw form
        // param so the request carries exactly `message: ["<permalink>"]`.
        SlackListsItemsCreateResponse createItemResponse = methods.slackListsItemsCreate(r -> r
                .token(botToken)
                .listId(listId)
                .initialFields(Arrays.asList(
                        ListRecord.Field.builder().columnId(keyToId.get("title")).text("probe row").build()
                )));
        assertThat(createItemResponse.getError(), is(nullValue()));
        assertThat(createItemResponse.isOk(), is(true));
        String itemId = createItemResponse.getItem().getId();

        // Set the message field on the row via items.update (request = URL array).
        // Sent as a raw form param so the request carries the exact docs shape.
        SlackHttpClient http = slack.getHttpClient();
        FormBody updateForm = new FormBody.Builder()
                .add("list_id", listId)
                .add("cells", "[{\"row_id\":\"" + itemId + "\",\"column_id\":\"" + messageColId
                        + "\",\"message\":[\"" + permalink + "\"]}]")
                .build();
        try (Response updRaw = http.postFormWithBearerHeader(
                MethodsClient.ENDPOINT_URL_PREFIX + "slackLists.items.update", botToken, updateForm)) {
            String updBody = updRaw.body() != null ? updRaw.body().string() : "";
            log.info("[probe] items.update raw ok? contains \\\"ok\\\":true -> {}", updBody.contains("\"ok\":true"));
        }

        // 4) RAW-capture the read-back shape of the message field.
        FormBody infoForm = new FormBody.Builder()
                .add("list_id", listId)
                .add("id", itemId)
                .build();
        String rawBody;
        try (Response infoRaw = http.postFormWithBearerHeader(
                MethodsClient.ENDPOINT_URL_PREFIX + "slackLists.items.info", botToken, infoForm)) {
            rawBody = infoRaw.body() != null ? infoRaw.body().string() : "";
        }
        log.info("[probe] raw items.info body (truncated 4k): {}",
                rawBody.length() > 4000 ? rawBody.substring(0, 4000) : rawBody);

        int msgIdx = rawBody.indexOf("\"message\"");
        assertThat("expected a message field in the raw response", msgIdx, is(not(-1)));
        // Skip past `"message"` and any whitespace / colon to the value's first char.
        int valStart = msgIdx + "\"message\"".length();
        while (valStart < rawBody.length()
                && (rawBody.charAt(valStart) == ':' || Character.isWhitespace(rawBody.charAt(valStart)))) {
            valStart++;
        }
        char firstValChar = rawBody.charAt(valStart);
        boolean isArray = firstValChar == '[';
        boolean isObject = firstValChar == '{';
        log.info("[probe] message field first value char = '{}' => isArray={}, isObject={}",
                firstValChar, isArray, isObject);

        // 5) Probe TYPED (production Gson) deserialization on this real shape.
        boolean typedThrew = false;
        String typedError = null;
        try {
            SlackListsItemsInfoResponse typed = methods.slackListsItemsInfo(r -> r
                    .token(botToken).listId(listId).id(itemId));
            log.info("[probe] typed items.info deserialized ok={}, error={}",
                    typed.isOk(), typed.getError());
        } catch (Exception e) {
            typedThrew = true;
            typedError = e.getClass().getSimpleName() + ": " + e.getMessage();
            log.warn("[probe] typed items.info THREW during deserialization: {}", typedError);
        }
        log.info("[probe] SUMMARY host={} messageIsArray={} messageIsObject={} typedDeserializationThrew={} typedError={}",
                teamHost, isArray, isObject, typedThrew, typedError);

        // The point of the probe is observation, not a pass/fail gate on the
        // shape. We only assert the response actually carried a message value
        // (either shape), and log the empirical result for the issue.
        assertThat("message value should be an array or object", isArray || isObject, is(true));

        // cleanup
        methods.slackListsItemsDelete(r -> r.token(botToken).listId(listId).id(itemId));
        methods.slackListsAccessDelete(r -> r.token(botToken).listId(listId)
                .channelIds(Arrays.asList(channelId)));
    }

}
