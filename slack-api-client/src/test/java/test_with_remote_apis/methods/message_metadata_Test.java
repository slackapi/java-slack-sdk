package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.conversations.ConversationsRepliesRequest;
import com.slack.api.methods.response.chat.ChatDeleteResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatScheduleMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.conversations.ConversationsRepliesResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;
import config.Constants;
import config.SlackTestConfig;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class message_metadata_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @BeforeClass
    public static void setUp() throws Exception {
        SlackTestConfig.initializeRawJSONDataFiles("chat.*");
    }

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    private String randomChannelId = null;

    void loadRandomChannelId() throws IOException, SlackApiException {
        if (randomChannelId == null) {
            ConversationsListResponse channelsListResponse =
                    slack.methods().conversationsList(r -> r.token(botToken).excludeArchived(true).limit(100));
            assertThat(channelsListResponse.getError(), is(nullValue()));
            for (Conversation channel : channelsListResponse.getChannels()) {
                if (channel.getName().equals("random")) {
                    randomChannelId = channel.getId();
                    break;
                }
            }
        }
    }

    @Data
    @Builder
    public static class Something {
        private String name;
        private Something child;
    }

    @Test
    public void sendAndRead() throws Exception {
        loadRandomChannelId();

        Map<String, Object> messageEventPayload = new HashMap<>();
        messageEventPayload.put("id", "id-123");
        messageEventPayload.put("count", 123);
        messageEventPayload.put("tags", Arrays.asList("foo", "bar", "baz"));
        messageEventPayload.put("objects", Arrays.asList(Something.builder()
                .name("foo")
                .child(Something.builder().name("bar").build())
                .build()));

        Map<String, Object> parsedObject = new HashMap<>();
        parsedObject.put("name", "foo");
        Map<String, Object> child = new HashMap<>();
        child.put("name", "bar");
        parsedObject.put("child", child);
        List<Map<String, Object>> parsedObjects = Arrays.asList(parsedObject);

        MethodsClient client = slack.methods(botToken);
        ChatPostMessageResponse messageWithMetadata = client.chatPostMessage(req -> req
                .channel(randomChannelId)
                .text("Message with metadata")
                .metadata(Message.Metadata.builder()
                        .eventType("java-sdk-unit-test-metadata")
                        .eventPayload(messageEventPayload)
                        .build())
                .blocks(asBlocks(section(s -> s.text(plainText("You can also do slack.methods(token)")))))
        );
        assertThat(messageWithMetadata.getError(), is(nullValue()));
        {
            Map<String, Object> returnedPayload = messageWithMetadata.getMessage().getMetadata().getEventPayload();
            assertThat(returnedPayload.get("id"), is(messageEventPayload.get("id")));
            assertThat(returnedPayload.get("tags"), is(messageEventPayload.get("tags")));
            assertThat(returnedPayload.get("objects"), is(parsedObjects));
        }

        int postAt = (int) ((new Date().getTime() / 1000) + 180);
        ChatScheduleMessageResponse scheduledMessage = client.chatScheduleMessage(req -> req
                .channel(randomChannelId)
                .text("Scheduled message with metadata")
                .postAt(postAt)
                .metadata(Message.Metadata.builder()
                        .eventType("java-sdk-unit-test-metadata")
                        .eventPayload(messageEventPayload)
                        .build())
                .blocks(asBlocks(section(s -> s.text(plainText("You can also do slack.methods(token)")))))
        );
        assertThat(scheduledMessage.getError(), is(nullValue()));
        {
            Map<String, Object> returnedPayload = scheduledMessage.getMessage().getMetadata().getEventPayload();
            assertThat(returnedPayload.get("id"), is(messageEventPayload.get("id")));
            assertThat(returnedPayload.get("tags"), is(messageEventPayload.get("tags")));
            assertThat(returnedPayload.get("objects"), is(parsedObjects));
        }

        ConversationsHistoryResponse history = client.conversationsHistory(req -> req
                .channel(randomChannelId)
                .limit(1)
                .includeAllMetadata(true)
        );
        assertThat(history.getError(), is(nullValue()));
        {
            Map<String, Object> returnedPayload = history.getMessages().get(0).getMetadata().getEventPayload();
            assertThat(returnedPayload.get("id"), is(messageEventPayload.get("id")));
            assertThat(returnedPayload.get("tags"), is(messageEventPayload.get("tags")));
            assertThat(returnedPayload.get("objects"), is(parsedObjects));
        }

        ConversationsRepliesResponse replies = client.conversationsReplies(req -> req
                .channel(randomChannelId)
                .ts(messageWithMetadata.getTs())
                .limit(1)
                .includeAllMetadata(true)
        );
        assertThat(replies.getError(), is(nullValue()));
        {
            Map<String, Object> returnedPayload = replies.getMessages().get(0).getMetadata().getEventPayload();
            assertThat(returnedPayload.get("id"), is(messageEventPayload.get("id")));
            assertThat(returnedPayload.get("tags"), is(messageEventPayload.get("tags")));
            assertThat(returnedPayload.get("objects"), is(parsedObjects));
        }

        messageEventPayload.put("tags", Arrays.asList("FOO"));
        ChatUpdateResponse modification = client.chatUpdate(r -> r
                .channel(messageWithMetadata.getChannel())
                .ts(messageWithMetadata.getTs())
                .text("Modified message with metadata")
                .metadata(Message.Metadata.builder()
                        .eventType("java-sdk-unit-test-metadata")
                        .eventPayload(messageEventPayload)
                        .build())
        );
        assertThat(modification.getError(), is(nullValue()));
        {
            Map<String, Object> returnedPayload = modification.getMessage().getMetadata().getEventPayload();
            assertThat(returnedPayload.get("id"), is(messageEventPayload.get("id")));
            assertThat(returnedPayload.get("tags"), is(messageEventPayload.get("tags")));
            assertThat(returnedPayload.get("objects"), is(parsedObjects));
        }

        ChatDeleteResponse deletion = client.chatDelete(r -> r
                .channel(messageWithMetadata.getChannel())
                .ts(messageWithMetadata.getTs())
        );
        assertThat(deletion.getError(), is(nullValue()));
    }

}
