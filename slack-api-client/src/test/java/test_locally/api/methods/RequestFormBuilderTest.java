package test_locally.api.methods;

import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.methods.request.admin.slackbot.skills.AdminSlackbotSkillsPublishRequest;
import com.slack.api.methods.request.apps.user.connection.AppsUserConnectionBulkUpdateRequest;
import com.slack.api.methods.request.apps.user.connection.AppsUserConnectionRequestAuthRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.methods.request.entity.EntityAcknowledgeCommentActionRequest;
import com.slack.api.methods.request.entity.EntityPresentCommentsRequest;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsUpdateRequest;
import com.slack.api.methods.request.team.TeamInfoRequest;
import com.slack.api.model.Attachment;
import com.slack.api.model.CallParticipant;
import com.slack.api.model.EntityComment;
import com.slack.api.model.EntityMetadata;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.RichTextBlock;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.OverflowMenuElement;
import com.slack.api.model.block.element.RichTextSectionElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import okhttp3.FormBody;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RequestFormBuilderTest {

    @Test
    public void teamInfoSlackbotAvailability() {
        FormBody omitted = RequestFormBuilder.toForm(TeamInfoRequest.builder().build()).build();
        assertThat(omitted.size(), is(0));

        for (Boolean include : Arrays.asList(true, false)) {
            FormBody form = RequestFormBuilder.toForm(TeamInfoRequest.builder()
                    .includeSlackbotAvailability(include).build()).build();
            assertThat(form.size(), is(1));
            assertThat(form.name(0), is("include_slackbot_availability"));
            assertThat(form.value(0), is(include ? "1" : "0"));
        }
    }

    @Test
    public void adminSlackbotSkillsPublishSkillIds() {
        FormBody form = RequestFormBuilder.toForm(AdminSlackbotSkillsPublishRequest.builder()
                .skillIds(Arrays.asList("F0123ABCD", "F0456EFGH")).build()).build();
        assertThat(form.size(), is(1));
        assertThat(form.name(0), is("skill_ids"));
        assertThat(form.value(0), is("F0123ABCD,F0456EFGH"));
    }

    @Test
    public void slackbotSkillsUpdateMcpServers() {
        FormBody omitted = RequestFormBuilder.toForm(SlackbotSkillsUpdateRequest.builder()
                .skillId("F0123ABCD").build()).build();
        assertThat(omitted.size(), is(1));
        assertThat(omitted.name(0), is("skill_id"));

        FormBody cleared = RequestFormBuilder.toForm(SlackbotSkillsUpdateRequest.builder()
                .skillId("F0123ABCD").mcpServers(Collections.emptyList()).build()).build();
        assertThat(cleared.size(), is(2));
        assertThat(cleared.name(1), is("mcp_servers"));
        assertThat(cleared.value(1), is("[]"));

        FormBody declared = RequestFormBuilder.toForm(SlackbotSkillsUpdateRequest.builder()
                .skillId("F0123ABCD")
                .mcpServers(Arrays.asList(SlackbotSkillsUpdateRequest.McpServer.builder()
                        .serviceType("app")
                        .appId("A0123ABCD")
                        .serverId("Amcp0456EFGH")
                        .build()))
                .build()).build();
        assertThat(declared.name(1), is("mcp_servers"));
        assertThat(declared.value(1), is("[{\"service_type\":\"app\",\"app_id\":\"A0123ABCD\",\"server_id\":\"Amcp0456EFGH\"}]"));
    }

    @Test
    public void entityAcknowledgeCommentActionComment() {
        FormBody form = RequestFormBuilder.toForm(EntityAcknowledgeCommentActionRequest.builder()
                .triggerId("1234567890123.1234567890123.abcdef01234567890abcdef012345689")
                .comment(EntityComment.builder()
                        .id("comment-identifier-002")
                        .sender(EntityMetadata.EntityPayload.User.builder().userId("U123456789").build())
                        .timestamp(1741164358)
                        .comment(EntityComment.Content.builder().value("Plain text comment").format("markdown").build())
                        .build())
                .build()).build();
        assertThat(form.size(), is(2));
        assertThat(form.name(0), is("trigger_id"));
        assertThat(form.name(1), is("comment"));
        assertThat(form.value(1), is("{\"comment\":{\"format\":\"markdown\",\"value\":\"Plain text comment\"},"
                + "\"id\":\"comment-identifier-002\","
                + "\"sender\":{\"user_id\":\"U123456789\"},"
                + "\"timestamp\":1741164358}"));
    }

    @Test
    public void entityPresentCommentsComments() {
        FormBody form = RequestFormBuilder.toForm(EntityPresentCommentsRequest.builder()
                .comments(Arrays.asList(EntityComment.builder()
                        .comment(EntityComment.Content.builder()
                                .blocks(RichTextBlock.builder()
                                        .elements(Arrays.asList(RichTextSectionElement.builder()
                                                .elements(Arrays.asList(RichTextSectionElement.Text.builder()
                                                        .text("Check out these different block types with paragraph breaks between them:\n\n")
                                                        .build()))
                                                .build()))
                                        .build())
                                .build())
                        .id("comment-identifier-001")
                        .sender(EntityMetadata.EntityPayload.User.builder().userId("U123456789").build())
                        .timestamp(1741164235)
                        .replyUsers(Arrays.asList(EntityMetadata.EntityPayload.User.builder().userId("U234567890").build()))
                        .build()))
                .triggerId("1234567890123.1234567890123.abcdef01234567890abcdef012345689")
                .canPostComment(true)
                .build()).build();
        assertThat(form.size(), is(3));
        assertThat(form.name(0), is("comments"));
        assertThat(form.value(0), is("[{\"comment\":{\"blocks\":{\"type\":\"rich_text\",\"elements\":[{\"type\":\"rich_text_section\",\"elements\":["
                + "{\"type\":\"text\",\"text\":\"Check out these different block types with paragraph breaks between them:\\n\\n\"}]}]}},"
                + "\"id\":\"comment-identifier-001\","
                + "\"sender\":{\"user_id\":\"U123456789\"},"
                + "\"timestamp\":1741164235,"
                + "\"reply_users\":[{\"user_id\":\"U234567890\"}]}]"));
        assertThat(form.name(1), is("trigger_id"));
        assertThat(form.name(2), is("can_post_comment"));
        assertThat(form.value(2), is("1"));
    }

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
