package test_locally.api.methods;

import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.methods.request.apps.user.connection.AppsUserConnectionBulkUpdateRequest;
import com.slack.api.methods.request.apps.user.connection.AppsUserConnectionRequestAuthRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.CallParticipant;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.OverflowMenuElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.FormBody;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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
    public void testAppsUserConnectionBulkUpdateUserIdsJsonSerialization() {
        // GIVEN
        AppsUserConnectionBulkUpdateRequest request = AppsUserConnectionBulkUpdateRequest.builder()
                .connectedUserIds(Arrays.asList("U1", "U2"))
                .disconnectedUserIds(Arrays.asList("U3"))
                .build();

        // WHEN
        FormBody form = RequestFormBuilder.toForm(request).build();

        // THEN
        assertThat(form.size(), is(2));
        assertThat(form.name(0), is("connected_user_ids"));
        assertThat(form.value(0), is("[\"U1\",\"U2\"]"));
        assertThat(form.name(1), is("disconnected_user_ids"));
        assertThat(form.value(1), is("[\"U3\"]"));
    }

    @Test
    public void testAppsUserConnectionBulkUpdateOmitsMissingUserIds() {
        // GIVEN
        AppsUserConnectionBulkUpdateRequest request = AppsUserConnectionBulkUpdateRequest.builder()
                .disconnectedUserIds(Arrays.asList("U3"))
                .build();

        // WHEN
        FormBody form = RequestFormBuilder.toForm(request).build();

        // THEN
        assertThat(form.size(), is(1));
        assertThat(form.name(0), is("disconnected_user_ids"));
    }

    @Test
    public void testAppsUserConnectionRequestAuthPrimaryProviderJsonSerialization() {
        // GIVEN
        AppsUserConnectionRequestAuthRequest request = AppsUserConnectionRequestAuthRequest.builder()
                .triggerId("T1")
                .primaryProvider(AppsUserConnectionRequestAuthRequest.PrimaryProvider.builder()
                        .value("github")
                        .name(PlainTextObject.builder().text("GitHub").build())
                        .isConnected(false)
                        .connectedIdentity(PlainTextObject.builder().text("octocat").build())
                        .accessory(OverflowMenuElement.builder()
                                .actionId("menu")
                                .options(Arrays.asList(
                                        OptionObject.builder().text(PlainTextObject.builder().text("Switch").build()).value("switch").build(),
                                        OptionObject.builder().text(PlainTextObject.builder().text("Disconnect").build()).value("disconnect").build()))
                                .build())
                        .build())
                .build();

        // WHEN
        FormBody form = RequestFormBuilder.toForm(request).build();

        // THEN
        assertThat(form.size(), is(2));
        assertThat(form.name(0), is("trigger_id"));
        assertThat(form.value(0), is("T1"));
        assertThat(form.name(1), is("primary_provider"));
        assertThat(form.value(1), is("{\"value\":\"github\","
                + "\"name\":{\"type\":\"plain_text\",\"text\":\"GitHub\"},"
                + "\"is_connected\":false,"
                + "\"connected_identity\":{\"type\":\"plain_text\",\"text\":\"octocat\"},"
                + "\"accessory\":{\"type\":\"overflow\",\"action_id\":\"menu\",\"options\":["
                + "{\"text\":{\"type\":\"plain_text\",\"text\":\"Switch\"},\"value\":\"switch\"},"
                + "{\"text\":{\"type\":\"plain_text\",\"text\":\"Disconnect\"},\"value\":\"disconnect\"}]}}"));
    }

    @Test
    public void testAppsUserConnectionRequestAuthPrimaryProviderAsStringTakesPrecedence() {
        // GIVEN
        AppsUserConnectionRequestAuthRequest request = AppsUserConnectionRequestAuthRequest.builder()
                .userId("U1")
                .primaryProvider(AppsUserConnectionRequestAuthRequest.PrimaryProvider.builder().value("ignored").build())
                .primaryProviderAsString("{\"value\":\"github\"}")
                .build();

        // WHEN
        FormBody form = RequestFormBuilder.toForm(request).build();

        // THEN
        assertThat(form.size(), is(2));
        assertThat(form.name(0), is("user_id"));
        assertThat(form.name(1), is("primary_provider"));
        assertThat(form.value(1), is("{\"value\":\"github\"}"));
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
