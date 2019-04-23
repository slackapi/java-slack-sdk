package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsInfoRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostEphemeralRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatUpdateRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostEphemeralResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatUpdateResponse;
import com.github.seratch.jslack.api.model.block.ActionsBlock;
import com.github.seratch.jslack.api.model.block.DividerBlock;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.ButtonElement;
import com.github.seratch.jslack.api.model.block.element.ImageElement;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.github.seratch.jslack.shortcut.model.ChannelId;
import com.github.seratch.jslack.shortcut.model.ChannelName;
import org.junit.Test;
import testing.Constants;
import testing.SlackTestConfig;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class Slack_blockkit_Test {
    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void example() throws IOException, SlackApiException {

        List<LayoutBlock> blocks = exampleBlocks();

        // ephemeral message creation
        {
            Optional<ChannelId> maybeChannelId = slack.shortcut(ApiToken.of(token)).findChannelIdByName(ChannelName.of("general"));
            String channelId = maybeChannelId.get().getValue();
            String userId = slack.methods().channelsInfo(ChannelsInfoRequest.builder()
                    .token(token)
                    .channel(maybeChannelId.get().getValue())
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
                    .channel("general")
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
