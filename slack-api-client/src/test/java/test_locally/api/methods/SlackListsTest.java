package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.element.RichTextSectionElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.List;
import java.util.Arrays;
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
        assertThat(slack.methods(ValidToken).slackListsCreate(r -> r
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
                            "type", "user"))))
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
                .channelIds(List.of("C1234567")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsAccessSet_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsAccessSet(r -> r
                .listId("F1234567")
                .accessLevel("write")
                .channelIds(List.of("C1234567")))
                .get().isOk(), is(true));
    }

    // Create several list items
    @Test
    public void slackListsItemsCreate() throws Exception {
        assertThat(slack.methods(ValidToken).slackListsItemsCreate(r -> r
            .listId("F1234567")
            .initialFields(List.of(Map.of(
                    "column_id", "C1234567",
                    "rich_text", List.of(Map.of(
                            "type", "rich_text",
                            "elements", List.of(Map.of(
                                    "type", "rich_text_section",
                                    "elements", List.of(Map.of(
                                            "type", "text",
                                            "text", "Test task item"))))))))))
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
                .ids(List.of("Rec018ALE9720", "Rec018ALE9721")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsDeleteMultiple_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsDeleteMultiple(r -> r
                .listId("F1234567")
                .ids(List.of("Rec1234567890", "Rec1234567891")))
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
        assertThat(slack.methods(ValidToken).slackListsItemsUpdate(r -> r
            .listId("F1234567")
            .cells(List.of(Map.of(
                    "row_id", "Rec1234567890",
                    "column_id", "C1234567",
                    "rich_text", List.of(Map.of(
                            "type", "rich_text",
                            "elements", List.of(Map.of(
                                    "type", "rich_text_section",
                                    "elements", List.of(Map.of(
                                            "type", "text",
                                            "text", "new task name"))))))))))
                .isOk(), is(true));
    }

    @Test
    public void slackListsItemsUpdate_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsItemsUpdate(r -> r
                .listId("F1234567")
                .cells(List.of(Map.of(
                    "row_id", "Rec1234567890",
                    "column_id", "C1234567",
                    "rich_text", List.of(Map.of(
                            "type", "rich_text",
                            "elements", List.of(Map.of(
                                    "type", "rich_text_section",
                                    "elements", List.of(Map.of(
                                            "type", "text",
                                            "text", "new task name"))))))))))
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
                .userIds(List.of("U1234567")))
                .isOk(), is(true));
    }

    @Test
    public void slackListsAccessDelete_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).slackListsAccessDelete(r -> r
                .listId("F1234567")
                .userIds(List.of("U1234567")))
                .get().isOk(), is(true));
    }

    // Helper methods
    private RichTextBlock createRichTextBlock(String text) {
        return RichTextBlock.builder()
                .elements(List.of(
                        RichTextSectionElement.builder()
                                .elements(List.of(
                                        RichTextSectionElement.Text.builder()
                                                .text(text)
                                                .build()
                                ))
                                .build()
                ))
                .build();
    }
}