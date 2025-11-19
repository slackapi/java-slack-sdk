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
        Map<String, Object> taskNameColumn = new HashMap<>();
        taskNameColumn.put("key", "task_name");
        taskNameColumn.put("name", "Task Name");
        taskNameColumn.put("type", "text");
        taskNameColumn.put("is_primary_column", true);

        Map<String, Object> dueDateColumn = new HashMap<>();
        dueDateColumn.put("key", "due_date");
        dueDateColumn.put("name", "Due Date");
        dueDateColumn.put("type", "date");

        Map<String, Object> statusChoices = new HashMap<>();
        statusChoices.put("choices", Arrays.asList(
                createChoice("not_started", "Not Started", "red"),
                createChoice("in_progress", "In Progress", "yellow"),
                createChoice("completed", "Completed", "green")
        ));
        Map<String, Object> statusColumn = new HashMap<>();
        statusColumn.put("key", "status");
        statusColumn.put("name", "Status");
        statusColumn.put("type", "select");
        statusColumn.put("options", statusChoices);

        Map<String, Object> assigneeColumn = new HashMap<>();
        assigneeColumn.put("key", "assignee");
        assigneeColumn.put("name", "Assignee");
        assigneeColumn.put("type", "user");

        assertThat(slack.methods(ValidToken).slackListsCreate(r -> r
                .name("Backlog")
                .descriptionBlocks(Arrays.asList(createRichTextBlock("List to keep track of tasks!")))
                .schema(Arrays.asList(taskNameColumn, dueDateColumn, statusColumn, assigneeColumn)))
                .isOk(), is(true));
    }

    @Test
    public void slackListsCreate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsCreate(r -> r.name("Backlog"))
                .get().isOk(), is(true));
    }

    // Set list access permissions
    @Test
    public void slackListsAccessSet() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsAccessSet(r -> r
                .listId("F1234567")
                .accessLevel("write")
                .userIds(Arrays.asList("U09G4FG3TRN")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsAccessSet_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsAccessSet(r -> r
                .listId("F1234567")
                .accessLevel("write")
                .userIds(Arrays.asList("U09G4FG3TRN")))
                .get().isOk(), is(true));
    }

    // Create several list items
    @Test
    public void slackListsItemsCreate() throws Exception {
        Map<String, Object> initialField = new HashMap<>();
        initialField.put("column_id", "col123");
        initialField.put("rich_text", Arrays.asList(createRichTextBlock("CLI app unlink command")));

        assertThat(slack.methods(ValidToken).slackListsItemsCreate(r -> r
                .listId("F1234567")
                .initialFields(Arrays.asList(initialField)))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsCreate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsCreate(r -> r
                .listId("F1234567"))
                .get().isOk(), is(true));
    }

    // Delete specific list items
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
                .ids(Arrays.asList("Rec018ALE9720", "Rec018ALE9721")))
                .get().isOk(), is(true));
    }

    // Retrieve info for a single list item
    @Test
    public void slackListsItemsInfo() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsItemsInfo(r -> r
                .listId("F1234567")
                .id("Rec018ALE9718")
                .includeIsSubscribed(true))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsInfo_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsInfo(r -> r
                .listId("F1234567")
                .id("Rec018ALE9718")
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
        Map<String, Object> cell = new HashMap<>();
        cell.put("column_id", "col123");
        cell.put("rich_text", Arrays.asList(createRichTextBlock("Updated text")));
        cell.put("row_id", "item_id_1");

        assertThat(slack.methods(ValidToken).slackListsItemsUpdate(r -> r
                .listId("F1234567")
                .cells(Arrays.asList(cell)))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsUpdate_async() throws Exception {
        Map<String, Object> cell = new HashMap<>();
        cell.put("column_id", "col123");
        cell.put("rich_text", Arrays.asList(createRichTextBlock("Updated text")));
        cell.put("row_id", "item_id_1");

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
                .name("Test List - UPDATED")
                .descriptionBlocks(Arrays.asList(createRichTextBlock("This list has been updated via API")))
                .todoMode(false))
                .isOk(), is(true));
    }

    @Test
    public void slackListsUpdate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsUpdate(r -> r
                .id("F1234567")
                .name("Test List - UPDATED")
                .descriptionBlocks(Arrays.asList(createRichTextBlock("This list has been updated via API")))
                .todoMode(false))
                .get().isOk(), is(true));
    }

    // Remove access for the test user
    @Test
    public void slackListsAccessDelete() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsAccessDelete(r -> r
                .listId("F1234567")
                .userIds(Arrays.asList("U09G4FG3TRN")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsAccessDelete_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsAccessDelete(r -> r
                .listId("F1234567")
                .userIds(Arrays.asList("U09G4FG3TRN")))
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

    private Map<String, Object> createChoice(String value, String label, String color) {
        Map<String, Object> choice = new HashMap<>();
        choice.put("value", value);
        choice.put("label", label);
        choice.put("color", color);
        return choice;
    }
}
