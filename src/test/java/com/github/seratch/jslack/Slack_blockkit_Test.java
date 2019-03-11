package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.model.block.ActionsBlock;
import com.github.seratch.jslack.api.model.block.DividerBlock;
import com.github.seratch.jslack.api.model.block.SectionBlock;
import com.github.seratch.jslack.api.model.block.composition.MarkdownTextObject;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.ButtonElement;
import com.github.seratch.jslack.api.model.block.element.ImageElement;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

public class Slack_blockkit_Test {
    Slack slack = Slack.getInstance();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void example() throws IOException, SlackApiException {
        ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                .channel("general")
                .token(token)
                .text("Example message")
                .blocks(Arrays.asList(SectionBlock.builder()
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
                                        .imageUrl(
                                                "https://s3-media3.fl.yelpcdn.com/bphoto/c7ed05m9lC2EmA3Aruue7A/o.jpg")
                                        .altText("alt text for image")
                                        .build())
                                .build(),
                        SectionBlock.builder()
                                .text(MarkdownTextObject.builder()
                                        .text("*Kin Khao*\n:star::star::star::star: 1638 reviews\n The sticky rice also goes wonderfully with the caramelized pork belly, which is absolutely melt-in-your-mouth and so soft.")
                                        .build())
                                .accessory(ImageElement.builder()
                                        .imageUrl(
                                                "https://s3-media2.fl.yelpcdn.com/bphoto/korel-1YjNtFtJlMTaC26A/o.jpg")
                                        .altText("alt text for image")
                                        .build())
                                .build(),
                        SectionBlock.builder()
                                .text(MarkdownTextObject.builder()
                                        .text("*Ler Ros*\n:star::star::star::star: 2082 reviews\n I would really recommend the  Yum Koh Moo Yang - Spicy lime dressing and roasted quick marinated pork shoulder, basil leaves, chili & rice powder.")
                                        .build())
                                .accessory(ImageElement.builder()
                                        .imageUrl(
                                                "https://s3-media2.fl.yelpcdn.com/bphoto/DawwNigKJ2ckPeDeDM7jAg/o.jpg")
                                        .altText("alt text for image")
                                        .build())
                                .build(),
                        new DividerBlock(),
                        ActionsBlock.builder()
                                .elements(Arrays.asList(ButtonElement.builder()
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
                                                .build()))
                                .build()))
                .build();

        ChatPostMessageResponse response = slack.methods()
                .chatPostMessage(request);
        Assert.assertThat(response.getError(), is(nullValue()));
        Assert.assertThat(response.isOk(), is(true));
        Assert.assertThat(response.getMessage().getBlocks().size(), is(7));
    }
}
