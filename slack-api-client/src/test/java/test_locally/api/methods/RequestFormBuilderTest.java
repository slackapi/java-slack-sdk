package test_locally.api.methods;

import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.methods.request.assistant.search.AssistantSearchContextRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.CallParticipant;
import com.slack.api.model.block.LayoutBlock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.FormBody;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestFormBuilderTest {


    @Test
    public void testBlocksJsonSerialization() {
        // GIVEN
        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(MyTestBlock.builder().build());

        ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder().build();
        chatPostMessageRequest.setBlocks(blocks);

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatPostMessageRequest).build();

        // THEN
        final int blocksIndexInForm = 2;
        assertThat(form.name(blocksIndexInForm), is("blocks"));
        assertThat(form.value(blocksIndexInForm), is("[{\"type\":\"myTestBlock\"}]"));
    }

    @Test
    public void testEmptyListJsonSerialization() {
        // GIVEN
        List<LayoutBlock> blocks = new ArrayList<>();

        ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder().build();
        chatPostMessageRequest.setBlocks(blocks);

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatPostMessageRequest).build();

        // THEN
        final int blocksIndexInForm = 2;
        assertThat(form.name(blocksIndexInForm), is("blocks"));
        assertThat(form.value(blocksIndexInForm), is("[]"));
    }

    @Test
    public void testBlocksJsonSerializationWithInnerClassInit() {
        // GIVEN
        ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder().build();
        chatPostMessageRequest.setBlocks(new ArrayList<LayoutBlock>() {{
            add(MyTestBlock.builder().build());
        }});

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatPostMessageRequest).build();

        // THEN
        final int blocksIndexInForm = 2;
        assertThat(form.name(blocksIndexInForm), is("blocks"));
        assertThat(form.value(blocksIndexInForm), is("[{\"type\":\"myTestBlock\"}]"));
    }

    @Test
    public void testAttachmentsJsonSerializationWithInnerClassInit() {
        // GIVEN
        ChatPostMessageRequest chatPostMessageRequest = ChatPostMessageRequest.builder().build();
        chatPostMessageRequest.setAttachments(new ArrayList<Attachment>() {{
            add(Attachment.builder().build());
        }});

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatPostMessageRequest).build();

        // THEN
        final int attachmentIndexInForm = 2;
        assertThat(form.name(attachmentIndexInForm), is("attachments"));
        assertThat(form.value(attachmentIndexInForm), is("[{}]"));
    }

    @Test
    public void testUserAuthBlocksJsonSerializationWithInnerClassInit() {
        // GIVEN
        ChatUnfurlRequest chatUnfurlRequest = ChatUnfurlRequest.builder().build();
        chatUnfurlRequest.setUserAuthBlocks(new ArrayList<LayoutBlock>() {{
            add(MyTestBlock.builder().build());
        }});

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatUnfurlRequest).build();

        // THEN
        final int userAuthBlocksIndexInFrom = 1;
        assertThat(form.name(userAuthBlocksIndexInFrom), is("user_auth_blocks"));
        assertThat(form.value(userAuthBlocksIndexInFrom), is("[{\"type\":\"myTestBlock\"}]"));
    }

    @Test
    public void testUsersJsonSerializationWithInnerClassInit() {
        // GIVEN
        CallParticipant participant = CallParticipant.builder().build();
        participant.setDisplayName("Bill");

        CallsAddRequest callsAddRequest = CallsAddRequest.builder().build();
        callsAddRequest.setUsers(new ArrayList<CallParticipant>() {{
            add(participant);
        }});

        // WHEN
        FormBody form = RequestFormBuilder.toForm(callsAddRequest).build();

        // THEN
        final int usersIndexInFrom = 0;
        assertThat(form.name(usersIndexInFrom), is("users"));
        assertThat(form.value(usersIndexInFrom), is("[{\"display_name\":\"Bill\"}]"));
    }

    @Test
    public void testUnfurlsJsonSerializationWithInnerClassInit() {
        // GIVEN
        ChatUnfurlRequest chatUnfurlRequest = ChatUnfurlRequest.builder().build();
        chatUnfurlRequest.setUnfurls(new HashMap<String, ChatUnfurlRequest.UnfurlDetail>() {{
            put("key", ChatUnfurlRequest.UnfurlDetail.builder().build());
        }});

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatUnfurlRequest).build();

        // THEN
        final int userAuthBlocksIndexInFrom = 0;
        assertThat(form.name(userAuthBlocksIndexInFrom), is("unfurls"));
        assertThat(form.value(userAuthBlocksIndexInFrom), is("{\"key\":{}}"));
    }

    @Test
    public void testUnfurlsJsonSerialization() {
        // GIVEN
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
        unfurls.put("key", ChatUnfurlRequest.UnfurlDetail.builder().build());

        ChatUnfurlRequest chatUnfurlRequest = ChatUnfurlRequest.builder().build();
        chatUnfurlRequest.setUnfurls(unfurls);

        // WHEN
        FormBody form = RequestFormBuilder.toForm(chatUnfurlRequest).build();

        // THEN
        final int userAuthBlocksIndexInFrom = 0;
        assertThat(form.name(userAuthBlocksIndexInFrom), is("unfurls"));
        assertThat(form.value(userAuthBlocksIndexInFrom), is("{\"key\":{}}"));
    }

    @Test
    public void testAssistantSearchContextSerialization() {
        // GIVEN
        AssistantSearchContextRequest request = AssistantSearchContextRequest.builder()
                .query("project gizmo")
                .actionToken("xoxe-123")
                .channelTypes(new ArrayList<String>() {{
                    add("public_channel");
                    add("private_channel");
                }})
                .contentTypes(new ArrayList<String>() {{
                    add("messages");
                    add("files");
                }})
                .includeBots(true)
                .includeDeletedUsers(false)
                .before(1717200000)
                .after(1717113600)
                .includeContextMessages(true)
                .contextChannelId("C12345678")
                .cursor("dXNlcjpVMDYxTkZUVDI=")
                .limit(10)
                .sort("timestamp")
                .sortDir("desc")
                .includeMessageBlocks(true)
                .highlight(false)
                .keywordsClauses(new ArrayList<List<String>>() {{
                    add(new ArrayList<String>() {{
                        add("project");
                        add("gizmo");
                    }});
                    add(new ArrayList<String>() {{
                        add("roadmap");
                    }});
                }})
                .termClauses(new ArrayList<String>() {{
                    add("launch");
                    add("milestone");
                }})
                .modifiers("from:@sergei")
                .includeArchivedChannels(true)
                .disableSemanticSearch(false)
                .build();

        // WHEN
        FormBody form = RequestFormBuilder.toForm(request).build();

        // THEN
        assertThat(form.size(), is(21));
        assertThat(form.name(0), is("query"));
        assertThat(form.value(0), is("project gizmo"));
        assertThat(form.name(1), is("action_token"));
        assertThat(form.value(1), is("xoxe-123"));
        assertThat(form.name(2), is("channel_types"));
        assertThat(form.value(2), is("public_channel,private_channel"));
        assertThat(form.name(3), is("content_types"));
        assertThat(form.value(3), is("messages,files"));
        assertThat(form.name(4), is("include_bots"));
        assertThat(form.value(4), is("1"));
        assertThat(form.name(5), is("include_deleted_users"));
        assertThat(form.value(5), is("0"));
        assertThat(form.name(6), is("before"));
        assertThat(form.value(6), is("1717200000"));
        assertThat(form.name(7), is("after"));
        assertThat(form.value(7), is("1717113600"));
        assertThat(form.name(8), is("include_context_messages"));
        assertThat(form.value(8), is("1"));
        assertThat(form.name(9), is("context_channel_id"));
        assertThat(form.value(9), is("C12345678"));
        assertThat(form.name(10), is("cursor"));
        assertThat(form.value(10), is("dXNlcjpVMDYxTkZUVDI="));
        assertThat(form.name(11), is("limit"));
        assertThat(form.value(11), is("10"));
        assertThat(form.name(12), is("sort"));
        assertThat(form.value(12), is("timestamp"));
        assertThat(form.name(13), is("sort_dir"));
        assertThat(form.value(13), is("desc"));
        assertThat(form.name(14), is("include_message_blocks"));
        assertThat(form.value(14), is("1"));
        assertThat(form.name(15), is("highlight"));
        assertThat(form.value(15), is("0"));
        assertThat(form.name(16), is("keywords_clauses"));
        assertThat(form.value(16), is("[[\"project\",\"gizmo\"],[\"roadmap\"]]"));
        assertThat(form.name(17), is("term_clauses"));
        assertThat(form.value(17), is("[\"launch\",\"milestone\"]"));
        assertThat(form.name(18), is("modifiers"));
        assertThat(form.value(18), is("from:@sergei"));
        assertThat(form.name(19), is("include_archived_channels"));
        assertThat(form.value(19), is("1"));
        assertThat(form.name(20), is("disable_semantic_search"));
        assertThat(form.value(20), is("0"));
    }

    @Test
    public void testAssistantSearchContextSkipsNullJsonClauses() {
        // GIVEN
        AssistantSearchContextRequest request = AssistantSearchContextRequest.builder()
                .query("project gizmo")
                .build();

        // WHEN
        FormBody form = RequestFormBuilder.toForm(request).build();

        // THEN
        assertThat(form.size(), is(1));
        assertThat(form.name(0), is("query"));
        assertThat(form.value(0), is("project gizmo"));
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class MyTestBlock implements LayoutBlock {
        public static final String TYPE = "myTestBlock";
        private final String type = TYPE;
        private String blockId;
    }
}
