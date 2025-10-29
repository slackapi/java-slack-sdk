package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest.UnfurlMetadata;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.response.auth.AuthTestResponse;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.conversations.ConversationsMembersResponse;
import com.slack.api.methods.response.files.FilesUploadResponse;
import com.slack.api.methods.response.files.FilesUploadV2Response;
import com.slack.api.model.*;
import com.slack.api.model.EntityMetadata.EntityPayload.FileFields;
import com.slack.api.model.EntityMetadata.EntityPayload.IncidentFields;
import com.slack.api.model.EntityMetadata.EntityPayload;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.WorkflowObject;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.slack.api.model.Attachments.asAttachments;
import static com.slack.api.model.Attachments.attachment;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class chat_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);
    String teamId = null; // Required if testing in an org environment. eg. "T0123ABC"

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
            ConversationsListResponse channelsListResponse = slack.methods()
                    .conversationsList(r -> {
                        r.token(botToken).excludeArchived(true).limit(100);
                        if (teamId != null) {
                            r.teamId(teamId);
                        }
                        return r;
                    });
            assertThat(channelsListResponse.getError(), is(nullValue()));
            for (Conversation channel : channelsListResponse.getChannels()) {
                if (channel.getName().equals("random")) {
                    randomChannelId = channel.getId();
                    break;
                }
            }
        }
    }

    String blocksAsString = "[\n"
            + "  {\n"
            + "    \"type\": \"section\",\n"
            + "    \"text\": {\n"
            + "      \"type\": \"mrkdwn\",\n"
            + "      \"text\": \"Hello, Assistant to the Regional Manager Dwight! *Michael Scott* wants to know where you'd like to take the Paper Company investors to dinner tonight.\\n\\n *Please select a restaurant:*\"\n"
            + "    }\n"
            + "  },\n"
            + "  {\n"
            + "    \"type\": \"divider\"\n"
            + "  },\n"
            + "  {\n"
            + "    \"type\": \"section\",\n"
            + "    \"text\": {\n"
            + "      \"type\": \"mrkdwn\",\n"
            + "      \"text\": \"*Farmhouse Thai Cuisine*\\n:star::star::star::star: 1528 reviews\\n They do have some vegan options, like the roti and curry, plus they have a ton of salad stuff and noodles can be ordered without meat!! They have something for everyone here\"\n"
            + "    },\n"
            + "    \"accessory\": {\n"
            + "      \"type\": \"image\",\n"
            + "      \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg\",\n"
            + "      \"alt_text\": \"alt text for image\"\n"
            + "    }\n"
            + "  },\n"
            + "  {\n"
            + "    \"type\": \"section\",\n"
            + "    \"text\": {\n"
            + "      \"type\": \"mrkdwn\",\n"
            + "      \"text\": \"*Kin Khao*\\n:star::star::star::star: 1638 reviews\\n The sticky rice also goes wonderfully with the caramelized pork belly, which is absolutely melt-in-your-mouth and so soft.\"\n"
            + "    },\n"
            + "    \"accessory\": {\n"
            + "      \"type\": \"image\",\n"
            + "      \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/korel-1YjNtFtJlMTaC26A/o.jpg\",\n"
            + "      \"alt_text\": \"alt text for image\"\n"
            + "    }\n"
            + "  },\n"
            + "  {\n"
            + "    \"type\": \"section\",\n"
            + "    \"text\": {\n"
            + "      \"type\": \"mrkdwn\",\n"
            + "      \"text\": \"*Ler Ros*\\n:star::star::star::star: 2082 reviews\\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder.\"\n"
            + "    },\n"
            + "    \"accessory\": {\n"
            + "      \"type\": \"image\",\n"
            + "      \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg\",\n"
            + "      \"alt_text\": \"alt text for image\"\n"
            + "    }\n"
            + "  },\n"
            + "  {\n"
            + "    \"type\": \"divider\"\n"
            + "  },\n"
            + "  {\n"
            + "    \"type\": \"actions\",\n"
            + "    \"elements\": [\n"
            + "      {\n"
            + "        \"type\": \"button\",\n"
            + "        \"text\": {\n"
            + "          \"type\": \"plain_text\",\n"
            + "          \"text\": \"Farmhouse\",\n"
            + "          \"emoji\": true\n"
            + "        },\n"
            + "        \"value\": \"click_me_123\"\n"
            + "      },\n"
            + "      {\n"
            + "        \"type\": \"button\",\n"
            + "        \"text\": {\n"
            + "          \"type\": \"plain_text\",\n"
            + "          \"text\": \"Kin Khao\",\n"
            + "          \"emoji\": true\n"
            + "        },\n"
            + "        \"value\": \"click_me_123\"\n"
            + "      },\n"
            + "      {\n"
            + "        \"type\": \"button\",\n"
            + "        \"text\": {\n"
            + "          \"type\": \"plain_text\",\n"
            + "          \"text\": \"Ler Ros\",\n"
            + "          \"emoji\": true\n"
            + "        },\n"
            + "        \"value\": \"click_me_123\"\n"
            + "      }\n"
            + "    ]\n"
            + "  }\n"
            + "]";

    @Test
    public void postMessage_bot() throws Exception {
        loadRandomChannelId();
        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(req -> req
                .channel(randomChannelId)
                .text("You can also do slack.methods(token)")
                .blocks(asBlocks(section(
                        s -> s.text(plainText("You can also do slack.methods(token)"))))));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getMessage().getText(), is("You can also do slack.methods(token)"));

        String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
        assertThat(scopes, is(notNullValue()));
    }

    @Test
    public void postMessage_user() throws Exception {
        loadRandomChannelId();
        ChatPostMessageResponse response = slack.methods(userToken).chatPostMessage(req -> req
                .channel(randomChannelId)
                .text("You can also do slack.methods(token)"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getMessage().getText(), is("You can also do slack.methods(token)"));

        String scopes = response.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
        assertThat(scopes, is(notNullValue()));
    }

    // https://github.com/slackapi/java-slack-sdk/issues/157
    @Test
    public void postMessage_blocksInAttachment_do_not_work_bot() throws Exception {
        loadRandomChannelId();
        ChatPostMessageResponse firstMessageCreation = slack.methods().chatPostMessage(req -> req
                .channel(randomChannelId)
                .token(botToken)
                .attachments(Arrays.asList(
                        Attachment
                                .builder()
                                .id(123)
                                .callbackId("callback")
                                .title("hi")
                                .blocks(Arrays.asList(DividerBlock.builder()
                                        .blockId("123").build()))
                                .build())));
        assertThat(firstMessageCreation.getError(), is("invalid_attachments"));
    }

    @Test
    public void postMessage_blocksInAttachment_do_not_work_user() throws Exception {
        loadRandomChannelId();
        ChatPostMessageResponse firstMessageCreation = slack.methods().chatPostMessage(req -> req
                .channel(randomChannelId)
                .token(userToken)
                .attachments(Arrays.asList(
                        Attachment
                                .builder()
                                .id(123)
                                .callbackId("callback")
                                .title("hi")
                                .blocks(Arrays.asList(DividerBlock.builder()
                                        .blockId("123").build()))
                                .build())));
        assertThat(firstMessageCreation.getError(), is("invalid_attachments"));
    }

    @Test
    public void chat_getPermalink_bot() throws IOException, SlackApiException {
        ConversationsListResponse channels = slack.methods().conversationsList(req -> req
                .token(botToken)
                .excludeArchived(true));
        assertThat(channels.getError(), is(nullValue()));
        assertThat(channels.isOk(), is(true));

        String channelId = channels.getChannels().get(0).getId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(req -> req
                .channel(channelId)
                .token(botToken)
                .text("Hi, this is a test message from Java Slack SDK's unit tests")
                .linkNames(true));
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.isOk(), is(true));
        assertThat(postResponse.getMessage().getText(),
                is("Hi, this is a test message from Java Slack SDK's unit tests"));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(req -> req
                .token(botToken)
                .channel(channelId)
                .messageTs(postResponse.getTs()));
        assertThat(permalink.getError(), is(nullValue()));
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));
    }

    @Test
    public void chat_getPermalink_user() throws IOException, SlackApiException {
        ConversationsListResponse channels = slack.methods().conversationsList(req -> req
                .token(userToken)
                .excludeArchived(true));
        assertThat(channels.getError(), is(nullValue()));
        assertThat(channels.isOk(), is(true));

        String channelId = channels.getChannels().get(0).getId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(req -> req
                .channel(channelId)
                .token(userToken)
                .text("Hi, this is a test message from Java Slack SDK's unit tests")
                .linkNames(true));
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.isOk(), is(true));
        assertThat(postResponse.getMessage().getText(),
                is("Hi, this is a test message from Java Slack SDK's unit tests"));

        ChatGetPermalinkResponse permalink = slack.methods().chatGetPermalink(req -> req
                .token(userToken)
                .channel(channelId)
                .messageTs(postResponse.getTs()));
        assertThat(permalink.getError(), is(nullValue()));
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));
    }

    @Test
    public void chat_getPermalink_bot_async() throws ExecutionException, InterruptedException {
        ConversationsListResponse channels = slack.methodsAsync().conversationsList(req -> req
                .token(botToken)
                .excludeArchived(true))
                .get();
        assertThat(channels.getError(), is(nullValue()));
        assertThat(channels.isOk(), is(true));

        String channelId = channels.getChannels().get(0).getId();

        ChatPostMessageResponse postResponse = slack.methodsAsync().chatPostMessage(req -> req
                .channel(channelId)
                .token(botToken)
                .text("Hi, this is a test message from Java Slack SDK's unit tests")
                .linkNames(true))
                .get();
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.isOk(), is(true));
        assertThat(postResponse.getMessage().getText(),
                is("Hi, this is a test message from Java Slack SDK's unit tests"));

        String scopes = postResponse.getHttpResponseHeaders().get("x-oauth-scopes").get(0);
        assertThat(scopes, is(notNullValue()));

        ChatGetPermalinkResponse permalink = slack.methodsAsync().chatGetPermalink(req -> req
                .token(botToken)
                .channel(channelId)
                .messageTs(postResponse.getTs()))
                .get();
        assertThat(permalink.getError(), is(nullValue()));
        assertThat(permalink.isOk(), is(true));
        assertThat(permalink.getPermalink(), is(notNullValue()));
    }

    // It's also possible to post a message by giving the name of a channel.
    //
    // https://docs.slack.dev/reference/methods/chat.postMessage#channels
    // You can either pass the channel's name (#general) or encoded ID (C024BE91L),
    // and the message will be posted to that channel.
    // The channel's ID can be retrieved through the channels.list API method.
    //
    // https://github.com/slackapi/python-slackclient/blob/master/README.md#sending-a-message-to-slack
    // In our examples, we specify the channel name, however it is recommended to
    // use the channel_id where possible.
    @Test
    public void postingWithChannelName() throws IOException, SlackApiException {
        makeSureIfGivingChannelNameWorks("random");
        makeSureIfGivingChannelNameWorks("#random");
    }

    private void makeSureIfGivingChannelNameWorks(String channelName) throws IOException, SlackApiException {
        ChatPostMessageResponse response = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(channelName)
                .text("Hello!")
                .build());

        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getChannel(), is(startsWith("C")));
        assertThat(response.getMessage().getText(), is(startsWith("Hello!")));
    }

    // NOTE: You need to add "youtube.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_raw_json_bot() throws Exception {
        loadRandomChannelId();

        String url = "https://www.youtube.com/watch?v=wq1R93UMqlk";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(userToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(true)
                .unfurlMedia(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setText("Every day is the test.");
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .rawUnfurls(GsonFactory.createSnakeCase().toJson(unfurls))
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));
    }

    // NOTE: You need to add "youtube.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_with_preview() throws Exception {
        loadRandomChannelId();

        String url = "https://www.youtube.com/watch?v=wq1R93UMqlk";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(userToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(false)
                .unfurlMedia(false)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setTitle("The top-level title (set by chat.unfurl)");
        detail.setPreview(ChatUnfurlRequest.UnfurlDetailPreview.builder()
                .title(plainText("The title in the preview set (set by chat.unfurl)"))
                .subtitle(plainText("The subtitle in the preview (set by chat.unfurl)"))
                .iconUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                .build());
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .unfurls(unfurls)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));

        // verify if the message can be parsed by the JSON parser
        ConversationsHistoryResponse history = slack.methods(botToken).conversationsHistory(r -> r
                .channel(randomChannelId)
                .oldest(ts)
                .inclusive(true));
        assertThat(history.getError(), is(nullValue()));
    }

    // NOTE: You need to add "myappdomain.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_with_work_object_from_json() throws Exception {
        loadRandomChannelId();

        // Post the URL to be unfurled
        String appUnfurlURL = "https://myappdomain.com/id/123?url-posted-by=user";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(appUnfurlURL)
                .unfurlLinks(false)
                .unfurlMedia(false)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + appUnfurlURL + ">"));

        String ts = postResponse.getTs();

        // Prepare the work object metadata for a sample Task entity
        String iconURL = "https://example.com/icon/high-priority.png"; // Provide the URL for a hosted image to
        // see it in the unfurl
        String userID = findUser();
        String jsonString = "{\"entities\":[{\"entity_type\":\"slack#/entities/task\",\"external_ref\":{\"id\":\"94\"},\"url\":\"https://myappdomain.com/admin/slack/workobject/94/change/\",\"entity_payload\":{\"attributes\":{\"title\":{\"text\":\"Default title\"},\"display_type\":\"Assignment\",\"product_icon\":{\"url\":\"https://play-lh.googleusercontent.com/DG-zbXPr8LItYD8F2nD4aR_SK_jpkipLBK77YWY-F0cdJt67VFgCHZtRtjsakzTw3EM=w480-h960-rw\",\"alt_text\":\"The product's icon\"},\"product_name\":\"My Product\"},\"fields\":{\"description\":{\"value\":\"Description of the task\",\"format\":\"markdown\"},\"date_created\":{\"value\":1741164235},\"date_updated\":{\"value\":1741164235},\"assignee\":{\"type\":\"slack#/types/user_id\",\"value\":\""
                + userID
                + "\"},\"status\":{\"value\":\"open\",\"tag_color\":\"blue\",\"link\":\"https://example.com/tasks?status=open\"},\"due_date\":{\"value\":\"2025-06-10\",\"type\":\"slack#/types/date\"},\"priority\":{\"value\":\"high\",\"icon\":{\"alt_text\":\"Icon to indicate a high priority item\",\"url\":\""
                + iconURL
                + "\"},\"link\":\"https://example.com/tasks?priority=high\"}},\"custom_fields\":[{\"key\":\"channels\",\"label\":\"channels\",\"item_type\":\"slack#/types/channel_id\",\"type\":\"array\",\"value\":[{\"value\":\""
                + randomChannelId + "\",\"type\":\"slack#/types/channel_id\"},{\"value\":\""
                + randomChannelId
                + "\",\"type\":\"slack#/types/channel_id\"}]},{\"key\":\"channel\",\"label\":\"Channel\",\"value\":\""
                + randomChannelId
                + "\",\"type\":\"slack#/types/channel_id\"},{\"key\":\"img\",\"label\":\"image\",\"type\":\"slack#/types/image\",\"image_url\":\"https://previews.us-east-1.widencdn.net/preview/48045879/assets/asset-view/8588de84-f8ed-4488-a456-45ba986280ee/thumbnail/eyJ3IjoyMDQ4LCJoIjoyMDQ4LCJzY29wZSI6ImFwcCJ9?sig.ver=1&sig.keyId=us-east-1.20240821&sig.expires=1759892400&sig=UgMe4SFiG6i3OP7mWd-ZX61Kx4uobjTmOuZqHjCV2QY\"},{\"type\":\"string\",\"key\":\"item_static_sel\",\"label\":\"Static Select\",\"value\":\"Red\"},{\"type\":\"array\",\"key\":\"multi_static_select_key\",\"label\":\"Multi Static Select\",\"value\":[{\"value\":\"Green\",\"type\":\"string\",\"tag_color\":\"gray\"}],\"item_type\":\"string\"},{\"type\":\"slack#/types/user_id\",\"key\":\"user_select_key\",\"label\":\"User select\",\"value\":\"USLACKBOT\",\"edit\":{\"enabled\":true}},{\"type\":\"array\",\"key\":\"string_array_key\",\"label\":\"Array of markdown strings\",\"item_type\":\"string\",\"value\":[{\"value\":\"__Thing 1__\",\"format\":\"markdown\"},{\"value\":\"*Thing 2*\",\"format\":\"markdown\"}]},{\"type\":\"array\",\"key\":\"string_array_2_key\",\"label\":\"Array of plain strings\",\"item_type\":\"string\",\"value\":[{\"value\":\"Plain string 1\",\"type\":\"string\"},{\"value\":\"Plain string 2\",\"type\":\"string\"}]},{\"type\":\"array\",\"key\":\"multi_user_select_key\",\"label\":\"Multi-user select\",\"item_type\":\"slack#/types/user_id\",\"value\":[{\"value\":\"USLACKBOT\",\"type\":\"slack#/types/user_id\"},{\"value\":\"U014KLZE350\",\"type\":\"slack#/types/user_id\"}]},{\"type\":\"array\",\"key\":\"multi_external_select_key\",\"label\":\"Multi External Select\",\"item_type\":\"string\",\"value\":[{\"value\":\"Jose Allen\",\"type\":\"string\",\"tag_color\":\"gray\"},{\"value\":\"Cristian Santos\",\"type\":\"string\",\"tag_color\":\"gray\"},{\"value\":\"Wayne Morgan\",\"type\":\"string\",\"tag_color\":\"gray\"}],\"edit\":{\"enabled\":true,\"select\":{\"fetch_options_dynamically\":true,\"current_values\":[\"helen-jones\",\"cristian-santos\",\"wayne-morgan\"]}}},{\"type\":\"string\",\"key\":\"external_select_key\",\"label\":\"External Select\",\"value\":\"Kevin Walters\",\"tag_color\":\"gray\",\"edit\":{\"enabled\":true,\"select\":{\"current_value\":\"kevin-walters\",\"fetch_options_dynamically\":true}}},{\"type\":\"slack#/types/timestamp\",\"key\":\"timestamp_key\",\"label\":\"Timestamp\",\"value\":\"1747496700\",\"edit\":{\"enabled\":true}}],\"actions\":{\"primary_actions\":[{\"text\":\"URL action\",\"action_id\":\"url_button_action_id\",\"value\":\"some_val\",\"style\":\"primary\",\"url\":\"https://example.com\",\"accessibility_label\":\"Goes to example.com\"},{\"text\":\"Block action\",\"action_id\":\"block_action_id\",\"value\":\"some_val\",\"style\":\"danger\",\"accessibility_label\":\"No URL so this should be invoked via blocks.actions\"}],\"overflow_actions\":[{\"text\":\"First overflow action\",\"action_id\":\"overflow_block_action_id\",\"value\":\"overflow\",\"style\":\"primary\",\"accessibility_label\":\"No URL so this should be invoked via blocks.actions\"},{\"text\":\"Second overflow action\",\"action_id\":\"second_block_action_id\",\"value\":\"overflow\",\"style\":\"danger\",\"url\":\"https://example.com\",\"accessibility_label\":\" URL \"}]}},\"app_unfurl_url\":\"https://myappdomain.com/id/123?url-posted-by=user\"}]}";

        // Unfurl the URL
        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .rawMetadata(jsonString)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));

        // Verify if the message can be parsed by the JSON parser
        ConversationsHistoryResponse history = slack.methods(botToken).conversationsHistory(r -> r
                .channel(randomChannelId)
                .oldest(ts)
                .inclusive(true));
        assertThat(history.getError(), is(nullValue()));

        // Verify that an attachment was added
        List<Attachment> attachments = history.getMessages().get(0).getAttachments();
        assertThat(attachments, is(not(nullValue())));
        assertThat(attachments.get(0), is(not(nullValue())));
        assertThat(attachments.get(0).getAppUnfurlUrl(), is(appUnfurlURL));
    }

    // NOTE: You need to add "myappdomain.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_with_work_object_from_object() throws Exception {
        loadRandomChannelId();

        // Post the URL to be unfurled
        String appUnfurlURL = "https://myappdomain.com/id/F123456?url-posted-by=user";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(appUnfurlURL)
                .unfurlLinks(false)
                .unfurlMedia(false)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + appUnfurlURL + ">"));
        String ts = postResponse.getTs();

        // Prepare the work object metadata for a sample File entity
        EntityPayload.Attributes.Title title = EntityPayload.Attributes.Title.builder().text("My File")
                .build();
        EntityPayload.Attributes attributes = EntityPayload.Attributes
                .builder().title(title).build();
        EntityPayload.TypedField createdBy = EntityPayload.TypedField.builder().value("Joe Smith")
                .type("string")
                .build();
        EntityPayload.Timestamp dateCreated = EntityPayload.Timestamp.builder().value(1756166400).build();
        FileFields fields = FileFields.builder().createdBy(createdBy).dateCreated(dateCreated).build();

        EntityPayload.StringField[] stringArray = {
            EntityPayload.StringField.builder().value("Hello").tagColor("red").build(),
            EntityPayload.StringField.builder().value("World").tagColor("green").build()};
        EntityPayload.CustomField[] customFields = {
            EntityPayload.CustomField.builder().type("string").key("hello_world").label("Message")
            .value("Hello World").build(),
            EntityPayload.CustomField.builder().type("array").key("array_field")
            .label("Array Field").itemType("string")
            .value(stringArray).build()};
        EntityPayload payload = EntityPayload.builder()
                .attributes(attributes)
                .fileFields(fields)
                .customFields(customFields)
                .build();
        EntityMetadata.ExternalRef externalRef = EntityMetadata.ExternalRef.builder()
                .id("F123456").build();
        EntityMetadata entity = EntityMetadata.builder()
                .entityType("slack#/entities/file")
                .appUnfurlUrl(appUnfurlURL)
                .url("https://myappdomain.com/id/F123456")
                .externalRef(externalRef)
                .entityPayload(payload)
                .build();
        EntityMetadata[] entities = {entity};
        UnfurlMetadata metadata = UnfurlMetadata.builder().entities(entities).build();

        // Unfurl the URL
        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .metadata(metadata)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));

        // Verify if the message can be parsed by the JSON parser
        ConversationsHistoryResponse history = slack.methods(botToken).conversationsHistory(r -> r
                .channel(randomChannelId)
                .oldest(ts)
                .inclusive(true));
        assertThat(history.getError(), is(nullValue()));

        // Verify that an attachment was added
        List<Attachment> attachments = history.getMessages().get(0).getAttachments();
        assertThat(attachments, is(not(nullValue())));
        assertThat(attachments.get(0), is(not(nullValue())));
        assertThat(attachments.get(0).getAppUnfurlUrl(), is(appUnfurlURL));
    }

    // NOTE: You need to add "youtube.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_raw_json_user() throws Exception {
        loadRandomChannelId();

        String url = "https://www.youtube.com/watch?v=wq1R93UMqlk";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(userToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(true)
                .unfurlMedia(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setText("Every day is the test.");
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(userToken)
                .channel(randomChannelId)
                .ts(ts)
                .rawUnfurls(GsonFactory.createSnakeCase().toJson(unfurls))
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));
    }

    // NOTE: You need to add "youtube.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_text() throws Exception {
        loadRandomChannelId();

        String url = "https://www.youtube.com/watch?v=wq1R93UMqlk";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(true)
                .unfurlMedia(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setText("Every day is the test.");
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .unfurls(unfurls)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));
    }

    @Test
    public void unfurl_blocks() throws Exception {
        loadRandomChannelId();

        String url = "https://www.youtube.com/watch?v=wq1R93UMqlk";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(true)
                .unfurlMedia(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setBlocks(Arrays.asList(
                DividerBlock.builder().blockId("b1").build(),
                DividerBlock.builder().blockId("b2").build()));
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .unfurls(unfurls)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));
    }

    // NOTE: You need to add "example.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_issue_399() throws Exception {

        loadRandomChannelId();

        String url = "https://www.example.com/test-issue-399";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(true)
                .unfurlMedia(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();

        // https://docs.slack.dev/messaging/unfurling-links-in-messages
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setTitle("Let's pretend we're on a rocket ship to Neptune");
        detail.setText("The planet Neptune looms near. What do you want to do?");
        detail.setFallback("imagine_001");
        detail.setAttachmentType("default");
        detail.setCallbackId("callback-id");
        detail.setActions(Arrays.asList(
                Action.builder().name("decision").value("orbit").style("primary").text("Orbit")
                        .type(Action.Type.BUTTON).build(),
                Action.builder().name("decision").value("land").text("Attempt to land")
                        .type(Action.Type.BUTTON).build(),
                Action.builder().name("decision").value("self_destruct").style("danger")
                        .text("Self destruct").type(Action.Type.BUTTON)
                        .confirm(Confirmation.builder()
                                .title("Are you sure you want to self destruct?")
                                .text("Maybe you should attempt to land instead. You might crash.")
                                .okText("Yes, self destruct")
                                .dismissText("No thanks")
                                .build())
                        .build()));
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .unfurls(unfurls)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));
    }

    // NOTE: You need to add "upload.wikimedia.org" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void unfurl_issue_399_flickr_example() throws Exception {

        loadRandomChannelId();

        String url = "https://www.example.com/test-issue-399-flickr";
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(url)
                .unfurlLinks(true)
                .unfurlMedia(true)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("<" + url + ">"));

        String ts = postResponse.getTs();
        Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();

        // https://docs.slack.dev/messaging/unfurling-links-in-messages
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setImageUrl(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/San_Francisco_Chronicle_Building.jpg/640px-San_Francisco_Chronicle_Building.jpg?download");
        detail.setColor("#ff0084");
        detail.setTitle("Chronicle Building");
        detail.setTitleLink("https://www.example.com/title-link");
        detail.setFields(Arrays.asList(
                Field.builder().title("Tags").value(
                        "chronicle building, flickrhq, san francisco, sf, sky, soma, California, United States")
                        .build(),
                Field.builder().title("Taken").value("Mon, 30 Jun 2014 15:50:53 GMT").build(),
                Field.builder().title("Posted").value("Tue, 01 Jul 2014 03:37:27 GMT").build()));

        detail.setText("This is an awesome picture taken in SF");
        detail.setFallback("chronicle_building.png");
        detail.setAttachmentType("default");
        detail.setCallbackId("callback-id");
        detail.setActions(Arrays.asList(
                Action.builder().name("buttons").value("Albums").text("Albums")
                        .url("https://www.example.com/buttons/albums").type(Action.Type.BUTTON)
                        .build(),
                Action.builder().name("buttons").value("Groups").text("Groups")
                        .url("https://www.example.com/buttons/groups").type(Action.Type.BUTTON)
                        .build()));
        detail.setAuthorName("Phil Dokas");
        detail.setAuthorLink("https://www.example.com/author-link");
        detail.setAuthorIcon("https://secure.gravatar.com/avatar/132fe0f031849e12eea7ce74f99b90f0");
        unfurls.put(url, detail);

        ChatUnfurlResponse unfurlResponse = slack.methods().chatUnfurl(ChatUnfurlRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .ts(ts)
                .unfurls(unfurls)
                .build());
        assertThat(unfurlResponse.getError(), is(nullValue()));
    }

    @Test
    public void chatUpdateWithBotToken_issue_372() throws IOException, SlackApiException {
        loadRandomChannelId();
        ChatPostMessageResponse creation = slack.methods(botToken)
                .chatPostMessage(r -> r.channel(randomChannelId).text("This is original"));
        assertThat(creation.getError(), is(nullValue()));
        assertThat(creation.getMessage().getText(), is("This is original"));
        ChatUpdateResponse modified = slack.methods(botToken)
                .chatUpdate(r -> r.channel(randomChannelId).text("modified").ts(creation.getTs()));
        assertThat(modified.getError(), is(nullValue()));
        assertThat(modified.getMessage().getText(), is("modified"));
    }

    @Test
    public void postMessage_blocksAsString_bot() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text("test")
                .blocksAsString(blocksAsString)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("test"));

        ChatUpdateResponse updateMessage = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                .channel(randomChannelId)
                .token(botToken)
                .ts(postResponse.getTs())
                .text("modified")
                .blocksAsString(blocksAsString)
                .build());
        assertThat(updateMessage.getError(), is(nullValue()));
        assertThat(updateMessage.getMessage().getText(), is("modified"));

        // To show the text instead of blocks
        ChatUpdateResponse updateMessage2 = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                .channel(randomChannelId)
                .token(botToken)
                .ts(postResponse.getTs())
                .blocksAsString("[]")
                .text("modified2")
                .build());
        assertThat(updateMessage2.getError(), is(nullValue()));
        assertThat(updateMessage2.getMessage().getText(), is("modified2"));
    }

    @Test
    public void postMessage_blocksAsString_user() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(userToken)
                .channel(randomChannelId)
                .text("test")
                .blocksAsString(blocksAsString)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));
        assertThat(postResponse.getMessage().getText(), is("test"));

        ChatUpdateResponse updateMessage = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                .channel(randomChannelId)
                .token(userToken)
                .ts(postResponse.getTs())
                .text("modified")
                .blocksAsString(blocksAsString)
                .build());
        assertThat(updateMessage.getError(), is(nullValue()));
        assertThat(updateMessage.getMessage().getText(), is("modified"));

        // To show the text instead of blocks
        ChatUpdateResponse updateMessage2 = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                .channel(randomChannelId)
                .token(userToken)
                .ts(postResponse.getTs())
                .blocksAsString("[]")
                .text("modified2")
                .build());
        assertThat(updateMessage2.getError(), is(nullValue()));
        assertThat(updateMessage2.getMessage().getText(), is("modified2"));
    }

    // NOTE: You need to add "myappdomain.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void postMessage_with_work_object_from_json() throws Exception {
        loadRandomChannelId();

        // Post a message with work object metadata
        String userID = findUser();
        String jsonString = "{\"entities\":[{\"entity_type\":\"slack#/entities/task\",\"external_ref\":{\"id\":\"94\"},\"url\":\"https://myappdomain.com/admin/slack/workobject/94/change/\",\"entity_payload\":{\"attributes\":{\"title\":{\"text\":\"Default title\"},\"display_type\":\"Assignment\",\"product_icon\":{\"url\":\"https://play-lh.googleusercontent.com/DG-zbXPr8LItYD8F2nD4aR_SK_jpkipLBK77YWY-F0cdJt67VFgCHZtRtjsakzTw3EM=w480-h960-rw\",\"alt_text\":\"The product's icon\"},\"product_name\":\"My Product\"},\"fields\":{\"description\":{\"value\":\"Description of the task\",\"format\":\"markdown\"},\"created_by\":{\"value\":\""
                + userID
                + "\",\"type\":\"slack#/types/user_id\"},\"date_created\":{\"value\":1741164235},\"date_updated\":{\"value\":1741164235},\"assignee\":{\"type\":\"slack#/types/user_id\",\"value\":\""
                + userID
                + "\"},\"status\":{\"value\":\"open\",\"tag_color\":\"blue\",\"link\":\"https://example.com/tasks?status=open\"},\"due_date\":{\"value\":\"2025-06-10\",\"type\":\"slack#/types/date\"},\"link\":\"https://example.com/tasks?priority=high\"},\"custom_fields\":[{\"key\":\"channel\",\"label\":\"Channel\",\"value\":\""
                + randomChannelId
                + "\",\"type\":\"slack#/types/channel_id\"},{\"key\":\"img\",\"label\":\"image\",\"type\":\"slack#/types/image\",\"image_url\":\"https://www.bhg.com/thmb/dlKLbrzxqX_H2viaMAwCdq8bo20=/1280x0/filters:no_upscale():strip_icc()/endless-summer-blue-hydrangea-macrophylla-c38f20fe-da0331cb73c94b9db10b6bf74e098356.jpg\"},{\"type\":\"string\",\"key\":\"item_static_sel\",\"label\":\"Static Select\",\"value\":\"Red\"},{\"type\":\"array\",\"key\":\"multi_static_select_key\",\"label\":\"Multi Static Select\",\"value\":[{\"value\":\"Green\",\"type\":\"string\",\"tag_color\":\"gray\"}],\"item_type\":\"string\"},{\"type\":\"slack#/types/user_id\",\"key\":\"user_select_key\",\"label\":\"User select\",\"value\":\"USLACKBOT\",\"edit\":{\"enabled\":true}},{\"type\":\"array\",\"key\":\"string_array_key\",\"label\":\"Array of markdown strings\",\"item_type\":\"string\",\"value\":[{\"value\":\"__Thing 1__\",\"type\":\"string\",\"format\":\"markdown\"},{\"value\":\"*Thing 2*\",\"type\":\"string\",\"format\":\"markdown\"}]},{\"type\":\"array\",\"key\":\"string_array_2_key\",\"label\":\"Array of plain strings\",\"item_type\":\"string\",\"value\":[{\"value\":\"Plain string 1\",\"type\":\"string\"},{\"value\":\"Plain string 2\",\"type\":\"string\"}]},{\"type\":\"array\",\"key\":\"multi_user_select_key\",\"label\":\"Multi-user select\",\"item_type\":\"slack#/types/user_id\",\"value\":[{\"value\":\"USLACKBOT\",\"type\":\"slack#/types/user_id\"},{\"value\":\"U014KLZE350\",\"type\":\"slack#/types/user_id\"}]},{\"type\":\"array\",\"key\":\"multi_external_select_key\",\"label\":\"Multi External Select\",\"item_type\":\"string\",\"value\":[{\"value\":\"Jose Allen\",\"type\":\"string\",\"tag_color\":\"gray\"},{\"value\":\"Cristian Santos\",\"type\":\"string\",\"tag_color\":\"gray\"},{\"value\":\"Wayne Morgan\",\"type\":\"string\",\"tag_color\":\"gray\"}],\"edit\":{\"enabled\":true,\"select\":{\"fetch_options_dynamically\":true,\"current_values\":[\"helen-jones\",\"cristian-santos\",\"wayne-morgan\"]}}},{\"type\":\"string\",\"key\":\"external_select_key\",\"label\":\"External Select\",\"value\":\"Kevin Walters\",\"tag_color\":\"gray\",\"edit\":{\"enabled\":true,\"select\":{\"current_value\":\"kevin-walters\",\"fetch_options_dynamically\":true}}},{\"type\":\"slack#/types/timestamp\",\"key\":\"timestamp_key\",\"label\":\"Timestamp\",\"value\":\"1747496700\",\"edit\":{\"enabled\":true}}],\"actions\":{\"primary_actions\":[{\"text\":\"URL action\",\"action_id\":\"url_button_action_id\",\"value\":\"some_val\",\"style\":\"primary\",\"url\":\"https://example.com\",\"accessibility_label\":\"Goes to example.com\"},{\"text\":\"Block action\",\"action_id\":\"block_action_id\",\"value\":\"some_val\",\"style\":\"danger\",\"accessibility_label\":\"No URL so this should be invoked via blocks.actions\"}],\"overflow_actions\":[{\"text\":\"First overflow action\",\"action_id\":\"overflow_block_action_id\",\"value\":\"overflow\",\"style\":\"primary\",\"accessibility_label\":\"No URL so this should be invoked via blocks.actions\"},{\"text\":\"Second overflow action\",\"action_id\":\"second_block_action_id\",\"value\":\"overflow\",\"style\":\"danger\",\"url\":\"https://example.com\",\"accessibility_label\":\" URL \"}]}},\"app_unfurl_url\":\"https://myappdomain.com/id/123?url-posted-by=user\"}]}";

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text("I have important information to share :wave:")
                .metadataAsString(jsonString)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));

        // Verify that an attachment was added
        List<Attachment> attachments = postResponse.getMessage().getAttachments();
        assertThat(attachments, is(not(nullValue())));
        assertThat(attachments.get(0), is(not(nullValue())));
        assertThat(attachments.get(0).getFromUrl(),
                is("https://myappdomain.com/admin/slack/workobject/94/change/"));
    }

    // NOTE: You need to add "myappdomain.com" at
    // Features > Event Subscriptions > App Unfurl Domains
    @Test
    public void postMessage_with_work_object_from_object() throws Exception {
        loadRandomChannelId();

        // Prepare the work object metadata
        EntityPayload.Attributes.Title title = EntityPayload.Attributes.Title.builder().text("The Incident")
                .build();
        EntityPayload.Attributes attributes = EntityPayload.Attributes
                .builder().title(title).build();
        String userID = findUser();
        EntityPayload.TypedField assignedTo = EntityPayload.TypedField.builder().value(userID)
                .type("slack#/types/user_id")
                .build();
        IncidentFields fields = IncidentFields.builder().assignedTo(assignedTo).build();
        EntityPayload.CustomField[] customFields = {
            EntityPayload.CustomField.builder().type("slack#/types/timestamp").key("important_date")
            .label("Important Date")
            .value(1756166400).build()};
        EntityPayload payload = EntityPayload.builder()
                .attributes(attributes)
                .incidentFields(fields)
                .customFields(customFields)
                .build();
        EntityMetadata.ExternalRef externalRef = EntityMetadata.ExternalRef.builder()
                .id("1234").build();
        EntityMetadata entity = EntityMetadata.builder()
                .entityType("slack#/entities/incident")
                .url("https://myappdomain.com/id/F123456")
                .externalRef(externalRef)
                .entityPayload(payload)
                .build();
        EntityMetadata[] entities = {entity};
        Message.EventAndEntityMetadata metadata = Message.EventAndEntityMetadata.builder().entities(entities)
                .build();

        // Post the message
        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text("I have important information to share :wave:")
                .eventAndEntityMetadata(metadata)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));

        List<Attachment> attachments = postResponse.getMessage().getAttachments();
        assertThat(attachments, is(not(nullValue())));
        assertThat(attachments.get(0), is(not(nullValue())));
        assertThat(attachments.get(0).getFromUrl(),
                is("https://myappdomain.com/id/F123456"));
    }

    @Test
    public void postEphemeral_thread() throws Exception {
        loadRandomChannelId();
        String userId = findUser();
        ChatPostMessageResponse first = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .text("first message"));
        assertThat(first.getError(), is(nullValue()));
        assertThat(first.getMessage().getText(), is("first message"));

        ChatPostMessageResponse second = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .threadTs(first.getTs())
                .text("reply to create an active thread"));
        assertThat(second.getError(), is(nullValue()));
        assertThat(second.getMessage().getText(), is("reply to create an active thread"));

        ChatPostEphemeralResponse third = slack.methods(botToken).chatPostEphemeral(r -> r
                .user(userId)
                .channel(randomChannelId)
                .text("ephemeral reply in thread")
                .threadTs(first.getTs()));
        assertThat(third.getError(), is(nullValue()));
    }

    @Test
    public void postEphemeral_authorship() throws Exception {
        loadRandomChannelId();

        String userId = findUser();
        ChatPostEphemeralResponse response = slack.methods(botToken).chatPostEphemeral(r -> r
                .channel(randomChannelId)
                .user(userId)
                .iconEmoji(":wave:")
                .username("given name")
                .text(":wave: Hi there!"));
        assertThat(response.getError(), is(nullValue()));
    }

    private String findUser() throws IOException, SlackApiException {

        String userId = null;

        ConversationsMembersResponse membersResponse = slack.methods(botToken)
                .conversationsMembers(r -> r.channel(randomChannelId).limit(100));
        assertThat(membersResponse.getError(), is(nullValue()));
        List<String> userIds = membersResponse.getMembers();
        for (String id : userIds) {
            User user = slack.methods(botToken).usersInfo(r -> r.user(id)).getUser();
            if (user.isBot() || user.isAppUser() || user.isDeleted() || user.isWorkflowBot()
                    || user.isStranger()) {
                continue;
            }
            userId = id;
            break;
        }
        assertThat(userId, is(notNullValue()));
        return userId;
    }

    @Test
    public void scheduleMessages() throws IOException, SlackApiException {
        loadRandomChannelId();
        int postAt = (int) ((new Date().getTime() / 1000) + 180);

        ChatScheduledMessagesListResponse before = slack.methods(botToken)
                .chatScheduledMessagesList(r -> r.limit(100));
        assertNull(before.getError());

        ChatScheduleMessageResponse message1 = slack.methods(botToken)
                .chatScheduleMessage(r -> r.channel(randomChannelId).postAt(postAt).text("hello"));
        assertNull(message1.getError());

        List<LayoutBlock> blocks = Arrays.asList(
                DividerBlock.builder().build(),
                SectionBlock.builder().text(PlainTextObject.builder().text("foo").build()).build());
        // This request no longer fails starting on Feb 3, 2025
        // ChatScheduleMessageResponse message2 =
        // slack.methods(botToken).chatScheduleMessage(r ->
        // r.channel(randomChannelId).postAt(postAt)
        // // the `text` field is required since May 2021
        // //.text("fallback")
        // .blocks(blocks));
        // assertEquals("invalid_arguments", message2.getError());
        // assertEquals("[ERROR] missing required field: text",
        // message2.getResponseMetadata().getMessages().get(0));

        ChatScheduleMessageResponse message3 = slack.methods(botToken)
                .chatScheduleMessage(r -> r.channel(randomChannelId).postAt(postAt)
                // the `text` field is required since May 2021
                .text("fallback")
                .blocks(blocks));
        assertNull(message3.getError());

        ChatScheduledMessagesListResponse after = slack.methods(botToken)
                .chatScheduledMessagesList(r -> r.limit(100));
        assertNull(after.getError());
        assertTrue(after.getScheduledMessages().size() - before.getScheduledMessages().size() == 2);
    }

    @Test
    public void streamMessages() throws IOException, SlackApiException {
        AuthTestResponse auth = slack.methods(botToken).authTest(req -> req);
        assertThat(auth.getError(), is(nullValue()));
        loadRandomChannelId();
        String userId = findUser();
        ChatPostMessageResponse topMessage = slack.methods(botToken).chatPostMessage(req -> req
                .channel(randomChannelId)
                .text("Get ready to stream a response in thread!"));
        assertThat(topMessage.getError(), is(nullValue()));
        ChatStartStreamResponse streamer = slack.methods(botToken).chatStartStream(r -> r
                .channel(randomChannelId)
                .threadTs(topMessage.getTs())
                .recipientUserId(userId)
                .recipientTeamId(auth.getTeamId()));
        assertThat(streamer.isOk(), is(true));
        assertThat(streamer.getError(), is(nullValue()));
        ChatAppendStreamResponse appends = slack.methods(botToken).chatAppendStream(r -> r
                .channel(randomChannelId)
                .ts(streamer.getTs())
                .markdownText("hello"));
        assertThat(appends.isOk(), is(true));
        assertThat(appends.getError(), is(nullValue()));
        ChatStopStreamResponse stops = slack.methods(botToken).chatStopStream(r -> r
                .channel(randomChannelId)
                .ts(streamer.getTs()));
        assertThat(stops.isOk(), is(true));
        assertThat(stops.getError(), is(nullValue()));
    }

    // https://github.com/slackapi/java-slack-sdk/issues/415
    @Test
    public void attachmentsWithBlocks_issue_415() throws IOException, SlackApiException {
        loadRandomChannelId();
        /*
                 * {
                 * "attachments": [
                 * {
                 * "color": "#00FF00",
                 * "blocks": [
                 * {
                 * "type": "section",
                 * "text": {
                 * "type": "mrkdwn",
                 * "text": "*I would expect this text to show*"
                 * }
                 * }
                 * ]
                 * }
                 * ]
                 * }
         */
        List<Attachment> attachments = asAttachments(
                attachment(a -> a.color("#00FF00").blocks(asBlocks(
                section(s -> s.text(markdownText(
                "*I would expect this text to show*")))))));
        ChatPostMessageResponse result = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .attachments(attachments));
        assertThat(result.getError(), is(nullValue()));
    }

    // Just in case, this test verifies the issue doesn't exist in MethodsClient
    // https://github.com/slackapi/java-slack-sdk/issues/429
    @Test
    public void post_messages_in_Chinese_issue_429() throws IOException, SlackApiException {
        loadRandomChannelId();

        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .text(":wave: Hello! 哈囉"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getMessage().getText(), is(":wave: Hello! 哈囉"));
    }

    @Test
    public void post_messages_timepicker() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .blocks(Arrays.asList(section(s -> s.text(plainText("test")).blockId("b").accessory(
                timePicker(t -> t
                .actionId("a")
                .initialTime("09:10")
                .placeholder(plainText("It's time to start!"))))))));
        // invalid_blocks means you haven't turned the beta feature on
        // Go to https://api.slack.com/apps/{api_app_id}/developer-beta
        assertThat(response.getError(), is(nullValue()));
    }

    // https://github.com/slackapi/java-slack-sdk/issues/647
    @Test
    public void share_message_with_files_issue_647() throws Exception {
        loadRandomChannelId();

        FilesUploadResponse fileUpload = slack.methods(botToken).filesUpload(r -> r
                .content("This is a test")
                .initialComment("Uploading a file...")
                .channels(Arrays.asList(randomChannelId)));
        assertThat(fileUpload.isOk(), is(true));

        File.ShareDetail share = fileUpload.getFile().getShares().getPublicChannels().get(randomChannelId)
                .get(0);
        String permalink = slack.methods(botToken).chatGetPermalink(r -> r
                .channel(randomChannelId)
                .messageTs(share.getTs())).getPermalink();

        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .unfurlLinks(true)
                .text("Here is the uploaded file: " + permalink));
        assertThat(message.isOk(), is(true));
    }

    @Test
    public void share_message_with_files_issue_647_v2() throws Exception {
        loadRandomChannelId();

        FilesUploadV2Response fileUpload = slack.methods(botToken).filesUploadV2(r -> r
                .content("This is a test")
                .initialComment("Uploading a file...")
                .channel(randomChannelId));
        assertThat(fileUpload.isOk(), is(true));

        Thread.sleep(10000L); // for test stability
        File.ShareDetail share = slack.methods(botToken).filesInfo(r -> r.file(fileUpload.getFile().getId()))
                .getFile().getShares().getPublicChannels().get(randomChannelId).get(0);
        String permalink = slack.methods(botToken).chatGetPermalink(r -> r
                .channel(randomChannelId)
                .messageTs(share.getTs())).getPermalink();

        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .unfurlLinks(true)
                .text("Here is the uploaded file: " + permalink));
        assertThat(message.isOk(), is(true));
    }

    // 2022-05-06 14:07:28,327 WARN [main] com.slack.api.methods.RequestFormBuilder
    // The top-level `text` argument is missing in the request payload for a
    // chat.postMessage call - It's a best practice to always provide a `text`
    // argument when posting a message. The `text` is used in places where the
    // content cannot be rendered such as: system push notifications, assistive
    // technology such as screen readers, etc.
    // 2022-05-06 14:07:28,327 WARN [main] com.slack.api.methods.RequestFormBuilder
    // Additionally, the attachment-level `fallback` argument is missing in the
    // request payload for a chat.postMessage call - To avoid this warning, it is
    // recommended to always provide a top-level `text` argument when posting a
    // message. Alternatively, you can provide an attachment-level `fallback`
    // argument, though this is now considered a legacy field (see
    // https://docs.slack.dev/legacy/legacy-messaging/legacy-secondary-message-attachments#legacy_fields
    // for more details).
    @Test
    public void textWarnings() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse postResponse = slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                .token(botToken)
                .channel(randomChannelId)
                .text(null)
                .blocksAsString(blocksAsString)
                .build());
        assertThat(postResponse.getError(), is(nullValue()));

        ChatUpdateResponse updateMessage = slack.methods().chatUpdate(ChatUpdateRequest.builder()
                .channel(randomChannelId)
                .token(botToken)
                .ts(postResponse.getTs())
                .text(null)
                .blocksAsString(blocksAsString)
                .build());
        assertThat(updateMessage.getError(), is(nullValue()));
    }

    @Test
    public void issue_785() throws Exception {
        loadRandomChannelId();

        String ts = null;
        try {
            ChatPostMessageResponse newMessage = slack.methods(botToken).chatPostMessage(r -> r
                    .text("Here is the link: https://i.imgur.com/brgYmPX.gifv")
                    .unfurlLinks(true)
                    .unfurlMedia(true)
                    .channel(randomChannelId));
            assertThat(newMessage.getError(), is(nullValue()));
            ts = newMessage.getTs();
            Thread.sleep(3_000L);

            ConversationsHistoryResponse history = slack.methods(botToken).conversationsHistory(r -> r
                    .channel(randomChannelId).limit(1));
            assertThat(history.getError(), is(nullValue()));
            List<Attachment> attachments = history.getMessages().get(0).getAttachments();
            // the attachments can be null
            if (attachments != null && attachments.get(0) != null) {
                Attachment.VideoHtml videoHtml = attachments.get(0).getVideoHtml();
                assertThat(videoHtml.getHtml(), is(
                        "<video muted loop controls autoplay preload=\"auto\" playsinline height=\"540\" width=\"960\" poster=\"https://i.imgur.com/brgYmPXh.jpg\" src=\"https://i.imgur.com/brgYmPX.mp4\"></video>"));
                assertThat(videoHtml.getSource(), is(nullValue()));
            }
        } finally {
            if (ts != null) {
                final String _ts = ts;
                slack.methods(botToken).chatDelete(r -> r.channel(randomChannelId).ts(_ts));
            }
        }
    }

    @Test
    public void post_messages_video() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .blocks(asBlocks(video(v -> v
                .blockId("b")
                .title(plainText("Video title"))
                .titleUrl("https://www.youtube.com/watch?v=q19RtuCHt1Q")
                .videoUrl("https://www.youtube.com/embed/q19RtuCHt1Q")
                .description(plainText("Video description"))
                .thumbnailUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                .altText("Video Alt text")
                .authorName("Slack Java SDK Unit Test")
                .providerIconUrl(
                        "https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                .providerName("Slack Java SDK Video Provider")))));
        // invalid_blocks means you do not have links.embed:write permission
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void post_messages_video_required_params_only() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .blocks(asBlocks(video(v -> v
                .blockId("b")
                .title(plainText("Video title"))
                // .titleUrl("https://www.youtube.com/watch?v=q19RtuCHt1Q")
                .videoUrl("https://www.youtube.com/embed/q19RtuCHt1Q")
                // .description(plainText("Video description"))
                .thumbnailUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                .altText("Video Alt text")
                // .authorName("Slack Java SDK Unit Test")
                // .providerIconUrl("https://assets.brandfolder.com/pmix53-32t4so-a6439g/original/slackbot.png")
                // .providerName("Slack Java SDK Video Provider")
                ))));
        // invalid_blocks means you do not have links.embed:write permission
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void post_message_with_datetimepicker() throws Exception {
        loadRandomChannelId();

        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .blocks(asBlocks(
                        actions(a -> a.blockId("b").elements(asElements(
                        datetimePicker(dt -> dt.actionId("a"))))))));
        assertThat(response.getError(), is(nullValue()));
    }

    @Test
    public void post_message_with_workflow_button() throws Exception {
        loadRandomChannelId();

        String triggerUrl = System.getenv("SLACK_SDK_TEST_WORKFLOW_TRIGGER_URL");
        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .blocks(asBlocks(
                        actions(a -> a.blockId("b").elements(asElements(
                        workflowButton(w -> w
                        .actionId("workflow")
                        .style("danger")
                        .accessibilityLabel("test")
                        .text(plainText("Start the workflow"))
                        .workflow(WorkflowObject.builder()
                                .trigger(WorkflowObject.Trigger
                                        .builder()
                                        .url(triggerUrl)
                                        // .customizableInputParameters(Arrays.asList(
                                        // WorkflowObject.Trigger.InputParam.builder()
                                        // .name("foo")
                                        // .value("bar")
                                        // .build()
                                        // ))
                                        .build())
                                .build()))))))));
        assertThat(response.getError(), is(nullValue()));
    }

}
