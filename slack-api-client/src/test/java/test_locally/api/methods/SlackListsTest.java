package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.element.RichTextSectionElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class SlackListsTest {

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

    // Create a new list
    @Test
    public void slackListsCreate() throws Exception {
        Map<String, Object> taskNameCol = new HashMap<>();
        taskNameCol.put("key", "task_name");
        taskNameCol.put("name", "Task Name");
        taskNameCol.put("type", "text");
        taskNameCol.put("is_primary_column", true);

        Map<String, Object> dueDateCol = new HashMap<>();
        dueDateCol.put("key", "due_date");
        dueDateCol.put("name", "Due Date");
        dueDateCol.put("type", "date");

        Map<String, Object> choice1 = new HashMap<>();
        choice1.put("value", "not_started");
        choice1.put("label", "Not Started");
        choice1.put("color", "red");

        Map<String, Object> choice2 = new HashMap<>();
        choice2.put("value", "in_progress");
        choice2.put("label", "In Progress");
        choice2.put("color", "yellow");

        Map<String, Object> choice3 = new HashMap<>();
        choice3.put("value", "completed");
        choice3.put("label", "Completed");
        choice3.put("color", "green");

        Map<String, Object> options = new HashMap<>();
        options.put("choices", Arrays.asList(choice1, choice2, choice3));

        Map<String, Object> statusCol = new HashMap<>();
        statusCol.put("key", "status");
        statusCol.put("name", "Status");
        statusCol.put("type", "select");
        statusCol.put("options", options);

        Map<String, Object> assigneeCol = new HashMap<>();
        assigneeCol.put("key", "assignee");
        assigneeCol.put("name", "Assignee");
        assigneeCol.put("type", "user");

        assertThat(slack.methods(ValidToken).slackListsCreate(r -> r
            .name("Test List - SlackLists API")
            .descriptionBlocks(Arrays.asList(RichTextBlock.builder()
                    .elements(Arrays.asList(RichTextSectionElement.builder()
                            .elements(Arrays.asList(RichTextSectionElement.Text.builder()
                                    .text("List to keep track of tasks!")
                                    .build()))
                            .build()))
                    .build()))
            .schema(Arrays.asList(taskNameCol, dueDateCol, statusCol, assigneeCol)))
            .isOk(), is(true));
    }

    @Test
    public void slackListsCreate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsCreate(r -> r.name("Test List Async - SlackLists API"))
                .get().isOk(), is(true));
    }

    // Set list access permissions
    @Test
    public void slackListsAccessSet() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsAccessSet(r -> r
                .listId("F1234567")
                .accessLevel("write")
                .channelIds(Arrays.asList("C1234567")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsAccessSet_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsAccessSet(r -> r
                .listId("F1234567")
                .accessLevel("write")
                .channelIds(Arrays.asList("C1234567")))
                .get().isOk(), is(true));
    }

    // Create several list items
    @Test
    public void slackListsItemsCreate() throws Exception {
        Map<String, Object> textElement = new HashMap<>();
        textElement.put("type", "text");
        textElement.put("text", "Test task item");

        Map<String, Object> richTextSection = new HashMap<>();
        richTextSection.put("type", "rich_text_section");
        richTextSection.put("elements", Arrays.asList(textElement));

        Map<String, Object> richText = new HashMap<>();
        richText.put("type", "rich_text");
        richText.put("elements", Arrays.asList(richTextSection));

        Map<String, Object> field = new HashMap<>();
        field.put("column_id", "C1234567");
        field.put("rich_text", Arrays.asList(richText));

        assertThat(slack.methods(ValidToken).slackListsItemsCreate(r -> r
            .listId("F1234567")
            .initialFields(Arrays.asList(field)))
            .isOk(), is(true));
    }

    @Test
    public void slackListsItemsCreate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsCreate(r -> r
                .listId("F1234567"))
                .get().isOk(), is(true));
    }

    // delete specific list items
    @Test
    public void slackListsItemsDelete() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsItemsDelete(r -> r
                .listId("F1234567")
                .id("Rec018ALE9718"))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsDelete_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsDelete(r -> r
                .listId("F1234567")
                .id("Rec018ALE9718"))
                .get().isOk(), is(true));
    }

    @Test
    public void slackListsItemsDeleteMultiple() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsItemsDeleteMultiple(r -> r
                .listId("F1234567")
                .ids(Arrays.asList("Rec018ALE9720", "Rec018ALE9721")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsDeleteMultiple_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsDeleteMultiple(r -> r
                .listId("F1234567")
                .ids(Arrays.asList("Rec1234567890", "Rec1234567891")))
                .get().isOk(), is(true));
    }

    // Retrieve info for a single list item
    @Test
    public void slackListsItemsInfo() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsItemsInfo(r -> r
                .listId("F1234567")
                .id("Rec1234567890")
                .includeIsSubscribed(true))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsInfo_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsInfo(r -> r
                .listId("F1234567")
                .id("Rec1234567890")
                .includeIsSubscribed(true))
                .get().isOk(), is(true));
    }

    // Retrieve all list items
    @Test
    public void slackListsItemsList() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsItemsList(r -> r
                .listId("F1234567")
                .limit(50)
                .archived(false))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsList_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsList(r -> r
                .listId("F1234567")
                .limit(50)
                .archived(false))
                .get().isOk(), is(true));
    }

    // Download list data
    @Test
    public void slackListsDownloadStart() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsDownloadStart(r -> r
                .listId("F1234567")
                .includeArchived(false))
                .isOk(), is(true));
    }

    @Test
    public void slackListsDownloadStart_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsDownloadStart(r -> r
                .listId("F1234567")
                .includeArchived(false))
                .get().isOk(), is(true));
    }

    @Test
    public void slackListsDownloadGet() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsDownloadGet(r -> r
                .listId("F1234567")
                .jobId("job123"))
                .isOk(), is(true));
    }

    @Test
    public void slackListsDownloadGet_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsDownloadGet(r -> r
                .listId("F1234567")
                .jobId("job123"))
                .get().isOk(), is(true));
    }

    // Update an existing list item
    @Test
    public void slackListsItemsUpdate() throws Exception {
        Map<String, Object> textElement = new HashMap<>();
        textElement.put("type", "text");
        textElement.put("text", "new task name");

        Map<String, Object> richTextSection = new HashMap<>();
        richTextSection.put("type", "rich_text_section");
        richTextSection.put("elements", Arrays.asList(textElement));

        Map<String, Object> richText = new HashMap<>();
        richText.put("type", "rich_text");
        richText.put("elements", Arrays.asList(richTextSection));

        Map<String, Object> cell = new HashMap<>();
        cell.put("row_id", "Rec1234567890");
        cell.put("column_id", "C1234567");
        cell.put("rich_text", Arrays.asList(richText));

        assertThat(slack.methods(ValidToken).slackListsItemsUpdate(r -> r
            .listId("F1234567")
            .cells(Arrays.asList(cell)))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsUpdate_async() throws Exception {
        Map<String, Object> textElement = new HashMap<>();
        textElement.put("type", "text");
        textElement.put("text", "new task name");

        Map<String, Object> richTextSection = new HashMap<>();
        richTextSection.put("type", "rich_text_section");
        richTextSection.put("elements", Arrays.asList(textElement));

        Map<String, Object> richText = new HashMap<>();
        richText.put("type", "rich_text");
        richText.put("elements", Arrays.asList(richTextSection));

        Map<String, Object> cell = new HashMap<>();
        cell.put("row_id", "Rec1234567890");
        cell.put("column_id", "C1234567");
        cell.put("rich_text", Arrays.asList(richText));

        assertThat(slack.methodsAsync(ValidToken).slackListsItemsUpdate(r -> r
                .listId("F1234567")
                .cells(Arrays.asList(cell)))
                .get().isOk(), is(true));
    }

    // Update list metadata
    @Test
    public void slackListsUpdate() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsUpdate(r -> r
                .id("F1234567")
                .name("Test List - UPDATED"))
                .isOk(), is(true));
    }

    @Test
    public void slackListsUpdate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsUpdate(r -> r
                .id("F1234567")
                .name("Test List - UPDATED"))
                .get().isOk(), is(true));
    }

    // Remove access for the test user
    @Test
    public void slackListsAccessDelete() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsAccessDelete(r -> r
                .listId("F1234567")
                .userIds(Arrays.asList("U1234567")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsAccessDelete_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsAccessDelete(r -> r
                .listId("F1234567")
                .userIds(Arrays.asList("U1234567")))
                .get().isOk(), is(true));
    }

    // Helper methods
    private RichTextBlock createRichTextBlock(String text) {
        return RichTextBlock.builder()
                .elements(Arrays.asList(
                        RichTextSectionElement.builder()
                                .elements(Arrays.asList(
                                        RichTextSectionElement.Text.builder()
                                                .text(text)
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }
}
