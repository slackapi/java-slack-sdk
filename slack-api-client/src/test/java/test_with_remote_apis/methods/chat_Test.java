package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.response.chat.*;
import com.slack.api.methods.response.chat.scheduled_messages.ChatScheduledMessagesListResponse;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.conversations.ConversationsMembersResponse;
import com.slack.api.methods.response.files.FilesUploadResponse;
import com.slack.api.model.*;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.util.json.GsonFactory;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static com.slack.api.model.Attachments.asAttachments;
import static com.slack.api.model.Attachments.attachment;
import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.timePicker;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@Slf4j
public class chat_Test {

    String botToken = System.getenv(Constants.SLACK_SDK_TEST_BOT_TOKEN);
    String userToken = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

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

    String blocksAsString = "[\n" +
            "  {\n" +
            "    \"type\": \"section\",\n" +
            "    \"text\": {\n" +
            "      \"type\": \"mrkdwn\",\n" +
            "      \"text\": \"Hello, Assistant to the Regional Manager Dwight! *Michael Scott* wants to know where you'd like to take the Paper Company investors to dinner tonight.\\n\\n *Please select a restaurant:*\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"divider\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"section\",\n" +
            "    \"text\": {\n" +
            "      \"type\": \"mrkdwn\",\n" +
            "      \"text\": \"*Farmhouse Thai Cuisine*\\n:star::star::star::star: 1528 reviews\\n They do have some vegan options, like the roti and curry, plus they have a ton of salad stuff and noodles can be ordered without meat!! They have something for everyone here\"\n" +
            "    },\n" +
            "    \"accessory\": {\n" +
            "      \"type\": \"image\",\n" +
            "      \"image_url\": \"https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg\",\n" +
            "      \"alt_text\": \"alt text for image\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"section\",\n" +
            "    \"text\": {\n" +
            "      \"type\": \"mrkdwn\",\n" +
            "      \"text\": \"*Kin Khao*\\n:star::star::star::star: 1638 reviews\\n The sticky rice also goes wonderfully with the caramelized pork belly, which is absolutely melt-in-your-mouth and so soft.\"\n" +
            "    },\n" +
            "    \"accessory\": {\n" +
            "      \"type\": \"image\",\n" +
            "      \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/korel-1YjNtFtJlMTaC26A/o.jpg\",\n" +
            "      \"alt_text\": \"alt text for image\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"section\",\n" +
            "    \"text\": {\n" +
            "      \"type\": \"mrkdwn\",\n" +
            "      \"text\": \"*Ler Ros*\\n:star::star::star::star: 2082 reviews\\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder.\"\n" +
            "    },\n" +
            "    \"accessory\": {\n" +
            "      \"type\": \"image\",\n" +
            "      \"image_url\": \"https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg\",\n" +
            "      \"alt_text\": \"alt text for image\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"divider\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"type\": \"actions\",\n" +
            "    \"elements\": [\n" +
            "      {\n" +
            "        \"type\": \"button\",\n" +
            "        \"text\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Farmhouse\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"value\": \"click_me_123\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"button\",\n" +
            "        \"text\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Kin Khao\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"value\": \"click_me_123\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"type\": \"button\",\n" +
            "        \"text\": {\n" +
            "          \"type\": \"plain_text\",\n" +
            "          \"text\": \"Ler Ros\",\n" +
            "          \"emoji\": true\n" +
            "        },\n" +
            "        \"value\": \"click_me_123\"\n" +
            "      }\n" +
            "    ]\n" +
            "  }\n" +
            "]";

    @Test
    public void postMessage_bot() throws Exception {
        loadRandomChannelId();
        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(req -> req
                .channel(randomChannelId)
                .text("You can also do slack.methods(token)"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getMessage().getText(), is("You can also do slack.methods(token)"));
    }

    @Test
    public void postMessage_user() throws Exception {
        loadRandomChannelId();
        ChatPostMessageResponse response = slack.methods(userToken).chatPostMessage(req -> req
                .channel(randomChannelId)
                .text("You can also do slack.methods(token)"));
        assertThat(response.getError(), is(nullValue()));
        assertThat(response.getMessage().getText(), is("You can also do slack.methods(token)"));
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
                                .blocks(Arrays.asList(DividerBlock.builder().blockId("123").build()))
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
                                .blocks(Arrays.asList(DividerBlock.builder().blockId("123").build()))
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
    // https://api.slack.com/methods/chat.postMessage#channels
    // You can either pass the channel's name (#general) or encoded ID (C024BE91L),
    // and the message will be posted to that channel.
    // The channel's ID can be retrieved through the channels.list API method.
    //
    // https://github.com/slackapi/python-slackclient/blob/master/README.md#sending-a-message-to-slack
    //  In our examples, we specify the channel name, however it is recommended to use the channel_id where possible.
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
                DividerBlock.builder().blockId("b2").build()
        ));
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

        // https://api.slack.com/docs/message-link-unfurling#slack_app_unfurling
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setTitle("Let's pretend we're on a rocket ship to Neptune");
        detail.setText("The planet Neptune looms near. What do you want to do?");
        detail.setFallback("imagine_001");
        detail.setAttachmentType("default");
        detail.setCallbackId("callback-id");
        detail.setActions(Arrays.asList(
                Action.builder().name("decision").value("orbit").style("primary").text("Orbit").type(Action.Type.BUTTON).build(),
                Action.builder().name("decision").value("land").text("Attempt to land").type(Action.Type.BUTTON).build(),
                Action.builder().name("decision").value("self_destruct").style("danger").text("Self destruct").type(Action.Type.BUTTON)
                        .confirm(Confirmation.builder()
                                .title("Are you sure you want to self destruct?")
                                .text("Maybe you should attempt to land instead. You might crash.")
                                .okText("Yes, self destruct")
                                .dismissText("No thanks")
                                .build()
                        ).build()
        ));
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

        // https://api.slack.com/docs/message-link-unfurling#slack_app_unfurling
        ChatUnfurlRequest.UnfurlDetail detail = new ChatUnfurlRequest.UnfurlDetail();
        detail.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/b/b8/San_Francisco_Chronicle_Building.jpg/640px-San_Francisco_Chronicle_Building.jpg?download");
        detail.setColor("#ff0084");
        detail.setTitle("Chronicle Building");
        detail.setTitleLink("https://www.example.com/title-link");
        detail.setFields(Arrays.asList(
                Field.builder().title("Tags").value("chronicle building, flickrhq, san francisco, sf, sky, soma, California, United States").build(),
                Field.builder().title("Taken").value("Mon, 30 Jun 2014 15:50:53 GMT").build(),
                Field.builder().title("Posted").value("Tue, 01 Jul 2014 03:37:27 GMT").build()
        ));

        detail.setText("This is an awesome picture taken in SF");
        detail.setFallback("chronicle_building.png");
        detail.setAttachmentType("default");
        detail.setCallbackId("callback-id");
        detail.setActions(Arrays.asList(
                Action.builder().name("buttons").value("Albums").text("Albums").url("https://www.example.com/buttons/albums").type(Action.Type.BUTTON).build(),
                Action.builder().name("buttons").value("Groups").text("Groups").url("https://www.example.com/buttons/groups").type(Action.Type.BUTTON).build()
        ));
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
        ChatPostMessageResponse creation = slack.methods(botToken).chatPostMessage(r -> r.channel(randomChannelId).text("This is original"));
        assertThat(creation.getError(), is(nullValue()));
        assertThat(creation.getMessage().getText(), is("This is original"));
        ChatUpdateResponse modified = slack.methods(botToken).chatUpdate(r -> r.channel(randomChannelId).text("modified").ts(creation.getTs()));
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
            if (user.isBot() || user.isAppUser() || user.isDeleted() || user.isWorkflowBot() || user.isStranger()) {
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

        ChatScheduledMessagesListResponse before = slack.methods(botToken).chatScheduledMessagesList(r -> r.limit(100));
        assertNull(before.getError());

        ChatScheduleMessageResponse message1 = slack.methods(botToken).chatScheduleMessage(r ->
                r.channel(randomChannelId).postAt(postAt).text("hello"));
        assertNull(message1.getError());

        List<LayoutBlock> blocks = Arrays.asList(
                DividerBlock.builder().build(),
                SectionBlock.builder().text(PlainTextObject.builder().text("foo").build()).build()
        );
        ChatScheduleMessageResponse message2 = slack.methods(botToken).chatScheduleMessage(r ->
                r.channel(randomChannelId).postAt(postAt)
                        // the `text` field is required since May 2021
                        //.text("fallback")
                        .blocks(blocks));
        assertEquals("invalid_arguments", message2.getError());
        assertEquals("[ERROR] missing required field: text", message2.getResponseMetadata().getMessages().get(0));

        ChatScheduleMessageResponse message3 = slack.methods(botToken).chatScheduleMessage(r ->
                r.channel(randomChannelId).postAt(postAt)
                        // the `text` field is required since May 2021
                        .text("fallback")
                        .blocks(blocks));
        assertNull(message3.getError());

        ChatScheduledMessagesListResponse after = slack.methods(botToken).chatScheduledMessagesList(r -> r.limit(100));
        assertNull(after.getError());
        assertTrue(after.getScheduledMessages().size() - before.getScheduledMessages().size() == 2);
    }

    // https://github.com/slackapi/java-slack-sdk/issues/415
    @Test
    public void attachmentsWithBlocks_issue_415() throws IOException, SlackApiException {
        loadRandomChannelId();
        /*
         * {
         *   "attachments": [
         *     {
         *       "color": "#00FF00",
         *       "blocks": [
         *         {
         *           "type": "section",
         *           "text": {
         *             "type": "mrkdwn",
         *             "text": "*I would expect this text to show*"
         *           }
         *         }
         *       ]
         *     }
         *   ]
         * }
         */
        List<Attachment> attachments = asAttachments(
                attachment(a -> a.color("#00FF00").blocks(asBlocks(
                        section(s -> s.text(markdownText("*I would expect this text to show*")))
                )))
        );
        ChatPostMessageResponse result = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .attachments(attachments)
        );
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

        ChatPostMessageResponse response = slack.methods(botToken).chatPostMessage(r -> r.channel(randomChannelId)
                .blocks(Arrays.asList(section(s -> s.text(plainText("test")).blockId("b").accessory(
                        timePicker(t -> t
                                .actionId("a")
                                .initialTime("09:10")
                                .placeholder(plainText("It's time to start!")))
                        ))
                ))
        );
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
                .channels(Arrays.asList(randomChannelId))
        );
        assertThat(fileUpload.isOk(), is(true));

        File.ShareDetail share = fileUpload.getFile().getShares().getPublicChannels().get(randomChannelId).get(0);
        String permalink = slack.methods(botToken).chatGetPermalink(r -> r
                .channel(randomChannelId)
                .messageTs(share.getTs())
        ).getPermalink();

        ChatPostMessageResponse message = slack.methods(botToken).chatPostMessage(r -> r
                .channel(randomChannelId)
                .unfurlLinks(true)
                .text("Here is the uploaded file: " + permalink)
        );
        assertThat(message.isOk(), is(true));
    }

    // 2021-01-05 14:38:54,767 WARN [main] com.slack.api.methods.RequestFormBuilder The `text` argument is missing in the request payload for a chat.postMessage call - It's a best practice to always provide a text argument when posting a message. The `text` is used in places where `blocks` cannot be rendered such as: system push notifications, assistive technology such as screen readers, etc.
    // 2021-01-05 14:38:55,055 WARN [main] com.slack.api.methods.RequestFormBuilder The `text` argument is missing in the request payload for a chat.update call - It's a best practice to always provide a text argument when posting a message. The `text` is used in places where `blocks` cannot be rendered such as: system push notifications, assistive technology such as screen readers, etc.
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
                    .channel(randomChannelId)
            );
            assertThat(newMessage.getError(), is(nullValue()));
            ts = newMessage.getTs();
            Thread.sleep(3_000L);

            ConversationsHistoryResponse history = slack.methods(botToken).conversationsHistory(r -> r
                    .channel(randomChannelId).limit(1));
            assertThat(history.getError(), is(nullValue()));
            Attachment.VideoHtml videoHtml = history.getMessages().get(0).getAttachments().get(0).getVideoHtml();
            assertThat(videoHtml.getHtml(), is(nullValue()));
            assertThat(videoHtml.getSource(), is(notNullValue()));
        } finally {
            if (ts != null) {
                final String _ts = ts;
                slack.methods(botToken).chatDelete(r -> r.channel(randomChannelId).ts(_ts));
            }
        }
    }

}
