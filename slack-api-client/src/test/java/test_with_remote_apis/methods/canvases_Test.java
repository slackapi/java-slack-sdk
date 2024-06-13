package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.canvases.sections.CanvasesSectionsLookupRequest;
import com.slack.api.methods.response.canvases.CanvasesCreateResponse;
import com.slack.api.methods.response.canvases.CanvasesDeleteResponse;
import com.slack.api.methods.response.canvases.CanvasesEditResponse;
import com.slack.api.methods.response.canvases.access.CanvasesAccessDeleteResponse;
import com.slack.api.methods.response.canvases.access.CanvasesAccessSetResponse;
import com.slack.api.methods.response.canvases.sections.CanvasesSectionsLookupResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsCreateResponse;
import com.slack.api.methods.response.conversations.canvases.ConversationsCanvasesCreateResponse;
import com.slack.api.methods.response.files.FilesInfoResponse;
import com.slack.api.model.canvas.*;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class canvases_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("canvases.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    @Test
    public void channel_canvases() throws Exception {
        MethodsClient client = slack.methods(botToken);

        ConversationsCreateResponse newChannel = client.conversationsCreate(r -> r.name("test-" + System.currentTimeMillis()));
        assertThat(newChannel.getError(), is(nullValue()));
        String channelId = newChannel.getChannel().getId();

        Thread.sleep(500L); // To avoid occasional 500 errors

        ConversationsCanvasesCreateResponse creation = client.conversationsCanvasesCreate(r -> r
                .channelId(channelId)
                .documentContent(CanvasDocumentContent.builder()
                        .markdown("# Channel canvas document\n" +
                                "\n" +
                                "---\n" +
                                "## Before\n" +
                                "**foo** _bar_\n" +
                                "hey hey\n" +
                                "\n")
                        .build())
        );
        assertThat(creation.getError(), is(nullValue()));

        String canvasId = creation.getCanvasId();
        List<String> userIds = Arrays.asList(client.authTest(r -> r).getUserId());
        FilesInfoResponse details = verifyCanvasOps(client, canvasId, channelId, userIds);
        ChatPostMessageResponse message = client.chatPostMessage(r -> r
                .channel(channelId)
                .text("Here you are: " + details.getFile().getPermalink())
        );
        assertThat(message.getError(), is(nullValue()));
    }

    @Test
    public void standalone_canvases() throws Exception {
        MethodsClient client = slack.methods(botToken);
        CanvasesCreateResponse creation = client.canvasesCreate(r -> r
                .title("My canvas " + System.currentTimeMillis())
                .documentContent(CanvasDocumentContent.builder()
                        .markdown("# Standalone canvas document\n" +
                                "\n" +
                                "---\n" +
                                "## Before\n" +
                                "**foo** _bar_\n" +
                                "hey hey\n" +
                                "\n")
                        .build())
        );
        assertThat(creation.getError(), is(nullValue()));

        String canvasId = creation.getCanvasId();
        try {
            List<String> userIds = Arrays.asList(client.authTest(r -> r).getUserId());
            verifyCanvasOps(client, canvasId, null, userIds);

            CanvasesAccessSetResponse set = client.canvasesAccessSet(r -> r
                    .canvasId(canvasId)
                    .accessLevel(CanvasDocumentAccessLevel.WRITE)
                    .userIds(userIds)
            );
            assertThat(set.getError(), is(nullValue()));
            CanvasesAccessDeleteResponse delete = client.canvasesAccessDelete(r -> r
                    .canvasId(canvasId)
                    .userIds(userIds)
            );
            assertThat(delete.getError(), is(nullValue()));

        } finally {
            CanvasesDeleteResponse deletion = client.canvasesDelete(r -> r.canvasId(canvasId));
            assertThat(deletion.getError(), is(nullValue()));
        }
    }

    FilesInfoResponse verifyCanvasOps(MethodsClient client, String canvasId, String channelId, List<String> userIds) throws Exception {
        CanvasesSectionsLookupResponse lookupResult = client.canvasesSectionsLookup(r -> r
                .canvasId(canvasId)
                .criteria(CanvasesSectionsLookupRequest.Criteria.builder()
                        .sectionTypes(Arrays.asList(CanvasDocumentSectionType.H2))
                        .containsText("Before")
                        .build()
                )
        );
        assertThat(lookupResult.getError(), is(nullValue()));

        String sectionId = lookupResult.getSections().get(0).getId();
        CanvasesEditResponse edit = client.canvasesEdit(r -> r
                .canvasId(canvasId)
                .changes(Arrays.asList(CanvasDocumentChange.builder()
                        .sectionId(sectionId)
                        .operation(CanvasEditOperation.REPLACE)
                        .documentContent(CanvasDocumentContent.builder().markdown("## After").build())
                        .build()
                ))
        );
        assertThat(edit.getError(), is(nullValue()));

        FilesInfoResponse details = client.filesInfo(r -> r.file(canvasId));
        assertThat(details.getError(), is(nullValue()));
        return details;
    }

    @Test
    public void standalone_canvases_error() throws Exception {
        MethodsClient client = slack.methods(botToken);
        CanvasesCreateResponse creation = client.canvasesCreate(r -> r
                .title("test")
                .documentContent(CanvasDocumentContent.builder()
                        .markdown("test")
                        .type("invalid")
                        .build())
        );
        assertThat(creation.getError(), is("invalid_arguments"));
    }
}
