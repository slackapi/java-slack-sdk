package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.request.entity.EntityPresentDetailsRequest;
import com.slack.api.methods.response.entity.EntityAcknowledgeCommentActionResponse;
import com.slack.api.methods.response.entity.EntityPresentCommentsResponse;
import com.slack.api.model.EntityComment;
import com.slack.api.model.EntityMetadata;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.element.RichTextSectionElement;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class entity_Test {

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("entity.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    // A trigger_id comes from a comment action in a work object flexpane, so the example from the docs is not found
    static String triggerId = "1234567890123.1234567890123.abcdef01234567890abcdef012345689";

    @Test
    public void acknowledgeCommentAction() throws Exception {
        EntityAcknowledgeCommentActionResponse response = slack.methods(botToken).entityAcknowledgeCommentAction(r -> r
                .triggerId(triggerId)
                .comment(EntityComment.builder()
                        .id("comment-identifier-002")
                        .sender(EntityMetadata.EntityPayload.User.builder().userId("U123456789").build())
                        .timestamp(1741164358)
                        .comment(EntityComment.Content.builder().value("Plain text comment").format("markdown").build())
                        .build()));
        assertThat(response.getError(), is("invalid_trigger_id"));
    }

    @Test
    public void presentComments() throws Exception {
        EntityPresentCommentsResponse response = slack.methods(botToken).entityPresentComments(r -> r
                .comments(Arrays.asList(
                        EntityComment.builder()
                                .sender(EntityMetadata.EntityPayload.User.builder().userId("U123456789").build())
                                .timestamp(1741164235)
                                .id("comment-identifier-001")
                                .url("your.app/item/1/comment/1")
                                .canEdit(true)
                                .canDelete(true)
                                .canReply(true)
                                .replyCount(2)
                                .replyUsers(Arrays.asList(EntityMetadata.EntityPayload.User.builder().userId("U234567890").build()))
                                .latestReplyTs(1741165000)
                                .lastEditTs(1741164300)
                                .comment(EntityComment.Content.builder()
                                        .blocks(RichTextBlock.builder()
                                                .elements(Arrays.asList(RichTextSectionElement.builder()
                                                        .elements(Arrays.asList(RichTextSectionElement.Text.builder()
                                                                .text("Check out these different block types with paragraph breaks between them:\n\n")
                                                                .build()))
                                                        .build()))
                                                .build())
                                        .build())
                                .build(),
                        EntityComment.builder()
                                .sender(EntityMetadata.EntityPayload.User.builder()
                                        .text("UserFirstName UserLastName")
                                        .url("your.app/user/userId")
                                        .email("firstname.lastname@domain.com")
                                        .build())
                                .timestamp(1741164358)
                                .id("comment-identifier-002")
                                .url("your.app/item/1/comment/2")
                                .threadRootId("comment-identifier-001")
                                .comment(EntityComment.Content.builder().value("Plain text comment").format("markdown").build())
                                .build()))
                .canPostComment(true)
                .deleteActionId("your-delete-action-id")
                .triggerId(triggerId));
        assertThat(response.getError(), is("invalid_trigger_id"));
    }

    @Test
    public void presentCommentsError() throws Exception {
        // The error is the same object entity.presentDetails takes, and a string returns invalid_arguments
        EntityPresentCommentsResponse response = slack.methods(botToken).entityPresentComments(r -> r
                .comments(Collections.emptyList())
                .triggerId(triggerId)
                .error(EntityPresentDetailsRequest.Error.builder()
                        .status("custom")
                        .customTitle("Comments unavailable")
                        .customMessage("The comments could not be loaded")
                        .build()));
        assertThat(response.getError(), is("invalid_trigger_id"));
    }
}
