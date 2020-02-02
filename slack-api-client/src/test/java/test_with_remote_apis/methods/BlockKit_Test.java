package test_with_remote_apis.methods;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostEphemeralRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatUpdateRequest;
import com.slack.api.methods.request.conversations.ConversationsMembersRequest;
import com.slack.api.methods.response.chat.ChatPostEphemeralResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.chat.ChatUpdateResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ConversationType;
import com.slack.api.model.block.DividerBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.BlockElements;
import config.Constants;
import config.SlackTestConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BlockKit_Test {

    String token = System.getenv(Constants.SLACK_SDK_TEST_USER_TOKEN);

    static SlackTestConfig testConfig = SlackTestConfig.getInstance();
    static Slack slack = Slack.getInstance(testConfig.getConfig());

    @AfterClass
    public static void tearDown() throws InterruptedException {
        SlackTestConfig.awaitCompletion(testConfig);
    }

    String randomChannelId;

    @Before
    public void loadRandomChannel() throws IOException, SlackApiException {
        if (randomChannelId == null) {
            String channelId = null;
            ConversationsListResponse conversations = slack.methods().conversationsList(req -> req
                    .token(token)
                    .types(Arrays.asList(ConversationType.PUBLIC_CHANNEL))
                    .excludeArchived(true)
                    .limit(100));
            assertThat(conversations.getError(), is(nullValue()));
            for (Conversation channel : conversations.getChannels()) {
                if (channel.getName().equals("random")) {
                    channelId = channel.getId();
                    break;
                }
            }
            assertThat(channelId, is(notNullValue()));
            randomChannelId = channelId;
        }
    }

    @Test
    public void example() throws IOException, SlackApiException {

        List<LayoutBlock> blocks = exampleBlocks();

        // ephemeral message creation
        {
            String userId = slack.methods().conversationsMembers(ConversationsMembersRequest.builder()
                    .token(token)
                    .channel(randomChannelId)
                    .build()
            ).getMembers().get(0);

            ChatPostEphemeralRequest request = ChatPostEphemeralRequest.builder()
                    .channel(randomChannelId)
                    .token(token)
                    .user(userId)
                    .text("Example message")
                    .blocks(blocks)
                    .build();

            ChatPostEphemeralResponse response = slack.methods().chatPostEphemeral(request);

            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        // message creation
        ChatPostMessageResponse postResponse;
        {
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel("random")
                    .token(token)
                    .text("Example message")
                    .blocks(blocks)
                    .build();

            postResponse = slack.methods().chatPostMessage(request);

            assertThat(postResponse.getError(), is(nullValue()));
            assertThat(postResponse.isOk(), is(true));
            assertThat(postResponse.getMessage().getBlocks().size(), is(7));
        }

        // message modification
        List<LayoutBlock> newBlocks = new ArrayList<>();
        newBlocks.addAll(blocks);
        newBlocks.add(new DividerBlock());
        newBlocks.add(SectionBlock.builder().text(MarkdownTextObject.builder().text("Added section!").build()).build());
        {
            ChatUpdateRequest request = ChatUpdateRequest.builder()
                    .token(token)
                    .text("Modified text")
                    .channel(postResponse.getChannel())
                    .ts(postResponse.getTs())
                    .blocks(newBlocks).build();

            ChatUpdateResponse response = slack.methods().chatUpdate(request);

            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

    }

    @Test
    public void exampleWithBlocksAsString() throws IOException, SlackApiException {
        // ephemeral message creation
        {
            String userId = slack.methods().conversationsMembers(ConversationsMembersRequest.builder()
                    .token(token)
                    .channel(randomChannelId)
                    .build()
            ).getMembers().get(0);

            ChatPostEphemeralRequest request = ChatPostEphemeralRequest.builder()
                    .channel(randomChannelId)
                    .token(token)
                    .user(userId)
                    .text("Example message")
                    .blocksAsString(blocksAsString)
                    .build();

            ChatPostEphemeralResponse response = slack.methods().chatPostEphemeral(request);

            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

        // message creation
        ChatPostMessageResponse postResponse;
        {
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel("random")
                    .token(token)
                    .text("Example message")
                    .blocksAsString(blocksAsString)
                    .build();

            postResponse = slack.methods().chatPostMessage(request);

            assertThat(postResponse.getError(), is(nullValue()));
            assertThat(postResponse.isOk(), is(true));
            assertThat(postResponse.getMessage().getBlocks().size(), is(7));
        }

        // message modification
        {
            ChatUpdateRequest request = ChatUpdateRequest.builder()
                    .token(token)
                    .text("Modified text")
                    .channel(postResponse.getChannel())
                    .ts(postResponse.getTs())
                    .blocksAsString("[{\n" +
                            "      \"type\": \"section\",\n" +
                            "      \"text\": {\n" +
                            "        \"type\": \"mrkdwn\",\n" +
                            "        \"text\": \"Modified\",\n" +
                            "        \"verbatim\": false\n" +
                            "      }\n" +
                            "    }]")
                    .build();

            ChatUpdateResponse response = slack.methods().chatUpdate(request);

            assertThat(response.getError(), is(nullValue()));
            assertThat(response.isOk(), is(true));
        }

    }

    private List<LayoutBlock> exampleBlocks() {
        return asBlocks(
                section(s -> s.text(markdownText("Hello, Assistant to the Regional Manager Dwight! *Michael Scott* wants to know where you'd like to take the Paper Company investors to dinner tonight.\n\n*Please select a restaurant:*"))),
                divider(),
                section(s -> s.text(markdownText("*Farmhouse Thai Cuisine*\n:star::star::star::star: 1528 reviews\n They do have some vegan options, like the roti and curry, plus they have a ton of salad stuff and noodles can be ordered without meat!! They have something for everyone here"))
                        .accessory(BlockElements.image(i -> i
                                .imageUrl("https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg")
                                .altText("alt text for image")))),
                section(s -> s.text(markdownText("*Kin Khao*\n:star::star::star::star: 1638 reviews\n The sticky rice also goes wonderfully with the caramelized pork belly, which is absolutely melt-in-your-mouth and so soft."))
                        .accessory(BlockElements.image(i -> i
                                .imageUrl("https://s3-media2.fl.yelpcdn.com/bphoto/korel-1YjNtFtJlMTaC26A/o.jpg")
                                .altText("alt text for image")))),
                section(s -> s.text(markdownText("*Ler Ros*\n:star::star::star::star: 2082 reviews\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder."))
                        .accessory(BlockElements.image(i -> i
                                .imageUrl("https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg")
                                .altText("alt text for image")))),
                divider(),
                actions(a -> a.elements(asElements(
                        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Farmhouse"))).value("click_me_123")),
                        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Kin Khao"))).value("click_me_123")),
                        button(b -> b.text(plainText(pt -> pt.emoji(true).text("Ler Ros"))).value("click_me_123"))
                )))
        );
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
    public void useBlockOps() throws IOException, SlackApiException {
        List<LayoutBlock> blocks = asBlocks(actions(asElements(button(b -> b.text(plainText(pt -> pt.text("foo"))).value("v")))));
        ChatPostMessageResponse response = slack.methods(token).chatPostMessage(req -> req
                .channel(randomChannelId)
                .blocks(blocks));
        assertThat(response.getError(), is(nullValue()));
    }

}
