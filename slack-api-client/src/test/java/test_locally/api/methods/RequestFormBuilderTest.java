package test_locally.api.methods;

import com.slack.api.methods.RequestFormBuilder;
import com.slack.api.methods.request.admin.slackbot.skills.AdminSlackbotSkillsPublishRequest;
import com.slack.api.methods.request.apps.user.connection.AppsUserConnectionBulkUpdateRequest;
import com.slack.api.methods.request.apps.user.connection.AppsUserConnectionRequestAuthRequest;
import com.slack.api.methods.request.assistant.search.AssistantSearchContextRequest;
import com.slack.api.methods.request.calls.CallsAddRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.methods.request.slackbot.skill_sets.SlackbotSkillSetsUpdateRequest;
import com.slack.api.methods.request.slackbot.skill_sets.items.SlackbotSkillSetsItemsAddRequest;
import com.slack.api.methods.request.slackbot.skill_sets.items.SlackbotSkillSetsItemsRemoveRequest;
import com.slack.api.methods.request.slackbot.skills.SlackbotSkillsUpdateRequest;
import com.slack.api.methods.request.team.TeamInfoRequest;
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
    public void slackbotSkillSetsItemsSkillIds() {
        FormBody addition = RequestFormBuilder.toForm(SlackbotSkillSetsItemsAddRequest.builder()
                .skillSetId("F0789IJKL")
                .skillIds(Arrays.asList("F0123ABCD", "F0456EFGH")).build()).build();
        assertThat(addition.size(), is(2));
        assertThat(addition.name(0), is("skill_set_id"));
        assertThat(addition.value(0), is("F0789IJKL"));
        assertThat(addition.name(1), is("skill_ids"));
        assertThat(addition.value(1), is("F0123ABCD,F0456EFGH"));

        FormBody removal = RequestFormBuilder.toForm(SlackbotSkillSetsItemsRemoveRequest.builder()
                .skillSetId("F0789IJKL")
                .skillIds(Collections.singletonList("F0123ABCD")).build()).build();
        assertThat(removal.size(), is(2));
        assertThat(removal.name(1), is("skill_ids"));
        assertThat(removal.value(1), is("F0123ABCD"));
    }

    @Test
    public void slackbotSkillSetsUpdateDescription() {
        FormBody omitted = RequestFormBuilder.toForm(SlackbotSkillSetsUpdateRequest.builder()
                .skillSetId("F0789IJKL").build()).build();
        assertThat(omitted.size(), is(1));
        assertThat(omitted.name(0), is("skill_set_id"));

        FormBody cleared = RequestFormBuilder.toForm(SlackbotSkillSetsUpdateRequest.builder()
                .skillSetId("F0789IJKL").description("").build()).build();
        assertThat(cleared.size(), is(2));
        assertThat(cleared.name(1), is("description"));
        assertThat(cleared.value(1), is(""));
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
        assertThat(form.size(), is(20));
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
        assertThat(form.name(16), is("term_clauses"));
        assertThat(form.value(16), is("[\"launch\",\"milestone\"]"));
        assertThat(form.name(17), is("modifiers"));
        assertThat(form.value(17), is("from:@sergei"));
        assertThat(form.name(18), is("include_archived_channels"));
        assertThat(form.value(18), is("1"));
        assertThat(form.name(19), is("disable_semantic_search"));
        assertThat(form.value(19), is("0"));
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
