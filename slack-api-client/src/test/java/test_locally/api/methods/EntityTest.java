package test_locally.api.methods;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.model.EntityComment;
import com.slack.api.model.EntityMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.MockSlackApiServer;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static util.MockSlackApi.ValidToken;

public class EntityTest {

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

    @Test
    public void entityAcknowledgeCommentAction() throws Exception {
        assertThat(slack.methods(ValidToken).entityAcknowledgeCommentAction(r -> r
                .triggerId("1234567890123.1234567890123.abcdef01234567890abcdef012345689")
                .comment(EntityComment.builder()
                        .id("comment-identifier-002")
                        .sender(EntityMetadata.EntityPayload.User.builder().userId("U123456789").build())
                        .timestamp(1741164358)
                        .comment(EntityComment.Content.builder().value("Plain text comment").format("markdown").build())
                        .build())).isOk(), is(true));
    }

    @Test
    public void entityAcknowledgeCommentAction_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).entityAcknowledgeCommentAction(r -> r
                .triggerId("1234567890123.1234567890123.abcdef01234567890abcdef012345689")
                .error("The comment could not be saved")).get().isOk(), is(true));
    }

    @Test
    public void entityPresentComments() throws Exception {
        assertThat(slack.methods(ValidToken).entityPresentComments(r -> r
                .comments(Arrays.asList(EntityComment.builder()
                        .id("comment-identifier-002")
                        .sender(EntityMetadata.EntityPayload.User.builder().userId("U123456789").build())
                        .timestamp(1741164358)
                        .comment(EntityComment.Content.builder().value("Plain text comment").format("markdown").build())
                        .build()))
                .triggerId("1234567890123.1234567890123.abcdef01234567890abcdef012345689")
                .canPostComment(true)
                .deleteActionId("your-delete-action-id")).isOk(), is(true));
    }

    @Test
    public void entityPresentComments_async() throws Exception {
        assertThat(slack.methodsAsync(ValidToken).entityPresentComments(r -> r
                .comments(Collections.emptyList())
                .triggerId("1234567890123.1234567890123.abcdef01234567890abcdef012345689")
                .userAuthRequired(true)
                .userAuthUrl("https://example.com/onboarding?user_id=xxx")).get().isOk(), is(true));
    }
}
