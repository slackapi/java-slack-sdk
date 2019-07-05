package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsInfoRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostEphemeralRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatUpdateRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsListResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostEphemeralResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatUpdateResponse;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.block.ActionsBlock;
import com.github.seratch.jslack.api.model.block.DividerBlock;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.ButtonElement;
import com.github.seratch.jslack.api.model.block.element.ImageElement;
import config.Constants;
import config.SlackTestConfig;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class BlockKit_Test {
    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void example() throws IOException, SlackApiException {

        List<LayoutBlock> blocks = exampleBlocks();

        // ephemeral message creation
        {
            String channelId = null;
            ChannelsListResponse channelsListResponse = slack.methods().channelsList(req -> req
                    .token(token)
                    .excludeArchived(true)
                    .limit(100));
            assertThat(channelsListResponse.getError(), is(nullValue()));
            for (Channel channel : channelsListResponse.getChannels()) {
                if (channel.getName().equals("random")) {
                    channelId = channel.getId();
                    break;
                }
            }
            assertThat(channelId, is(notNullValue()));

            String userId = slack.methods().channelsInfo(ChannelsInfoRequest.builder()
                    .token(token)
                    .channel(channelId)
                    .build()
            ).getChannel().getMembers().get(0);

            ChatPostEphemeralRequest request = ChatPostEphemeralRequest.builder()
                    .channel(channelId)
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
        Collections.copy(blocks, newBlocks);
        newBlocks.add(new DividerBlock());
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

    private List<LayoutBlock> exampleBlocks() {
        return Arrays.asList(
                SectionBlock.builder()
                        .text(MarkdownTextObject.builder()
                                .text("Hello, Assistant to the Regional Manager Dwight! *Michael Scott* wants to know where you'd like to take the Paper Company investors to dinner tonight.\n\n*Please select a restaurant:*")
                                .build())
                        .build(),

                new DividerBlock(),

                SectionBlock.builder()
                        .text(MarkdownTextObject.builder()
                                .text("*Farmhouse Thai Cuisine*\n:star::star::star::star: 1528 reviews\n They do have some vegan options, like the roti and curry, plus they have a ton of salad stuff and noodles can be ordered without meat!! They have something for everyone here")
                                .build())
                        .accessory(ImageElement.builder()
                                .imageUrl("https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg")
                                .altText("alt text for image")
                                .build())
                        .build(),

                SectionBlock.builder()
                        .text(MarkdownTextObject.builder()
                                .text("*Kin Khao*\n:star::star::star::star: 1638 reviews\n The sticky rice also goes wonderfully with the caramelized pork belly, which is absolutely melt-in-your-mouth and so soft.")
                                .build())
                        .accessory(ImageElement.builder()
                                .imageUrl("https://s3-media2.fl.yelpcdn.com/bphoto/korel-1YjNtFtJlMTaC26A/o.jpg")
                                .altText("alt text for image")
                                .build())
                        .build(),

                SectionBlock.builder()
                        .text(MarkdownTextObject.builder()
                                .text("*Ler Ros*\n:star::star::star::star: 2082 reviews\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder.")
                                .build())
                        .accessory(ImageElement.builder()
                                .imageUrl("https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg")
                                .altText("alt text for image")
                                .build())
                        .build(),

                new DividerBlock(),

                ActionsBlock.builder().elements(
                        Arrays.asList(
                                ButtonElement.builder()
                                        .text(PlainTextObject.builder()
                                                .emoji(true)
                                                .text("Farmhouse")
                                                .build())
                                        .value("click_me_123")
                                        .build(),
                                ButtonElement.builder()
                                        .text(PlainTextObject.builder()
                                                .emoji(true)
                                                .text("Kin Khao")
                                                .build())
                                        .value("click_me_123")
                                        .build(),
                                ButtonElement.builder()
                                        .text(PlainTextObject.builder()
                                                .emoji(true)
                                                .text("Ler Ros")
                                                .build())
                                        .value("click_me_123")
                                        .build()
                        )).build()
        );
    }
}
