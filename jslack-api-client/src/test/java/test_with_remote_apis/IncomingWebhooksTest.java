package test_with_remote_apis;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Field;
import com.github.seratch.jslack.api.model.block.ActionsBlock;
import com.github.seratch.jslack.api.model.block.LayoutBlock;
import com.github.seratch.jslack.api.model.block.composition.PlainTextObject;
import com.github.seratch.jslack.api.model.block.element.BlockElement;
import com.github.seratch.jslack.api.model.block.element.ButtonElement;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@Slf4j
public class IncomingWebhooksTest {

    Slack slack = Slack.getInstance(SlackTestConfig.get());

    @Test
    public void incomingWebhook() throws IOException {
        // String url = "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX";
        String url = System.getenv("SLACK_WEBHOOK_TEST_URL");
        if (url == null) {
            throw new IllegalStateException("Environment variable SLACK_WEBHOOK_TEST_URL must be defined");
        }

        Payload payload = Payload.builder()
                .channel("#random")
                .text("Hello World!")
                .iconEmoji(":smile_cat:")
                .username("jSlack")
                .attachments(new ArrayList<Attachment>())
                .build();

        Attachment attachment = Attachment.builder()
                .text("This is an *attachment*.")
                .authorName("Smiling Imp")
                .color("#36a64f")
                .fallback("Required plain-text summary of the attachment.")
                .title("Slack API Documentation")
                .titleLink("https://api.slack.com/")
                .footer("footer")
                .fields(new ArrayList<Field>())
                .mrkdwnIn(new ArrayList<String>())
                .build();

        attachment.getMrkdwnIn().add("text");

        {
            Field field = Field.builder()
                    .title("Long Title")
                    .value("Long Value........................................................")
                    .valueShortEnough(false).build();
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        {
            Field field = Field.builder()
                    .title("Short Title")
                    .value("Short Value")
                    .valueShortEnough(true).build();
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        payload.getAttachments().add(attachment);

        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getMessage(), is("OK"));
        assertThat(response.getCode(), is(200));
    }

    @Test
    public void incomingWebhook_BlockKit() throws IOException {
        // String url = "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX";
        String url = System.getenv("SLACK_WEBHOOK_TEST_URL");
        if (url == null) {
            throw new IllegalStateException("Environment variable SLACK_WEBHOOK_TEST_URL must be defined");
        }

        Payload payload = Payload.builder()
                .channel("#random")
                .text("Hello World!")
                .iconEmoji(":smile_cat:")
                .username("jSlack")
                .blocks(new ArrayList<LayoutBlock>())
                .build();

        ButtonElement button = ButtonElement.builder()
                .text(PlainTextObject.builder().emoji(true).text("Farmhouse").build())
                .value("click_me_123")
                .build();

        LayoutBlock block = ActionsBlock.builder().elements(Arrays.asList((BlockElement) button)).build();

        payload.getBlocks().add(block);

        WebhookResponse response = slack.send(url, payload);
        log.info(response.toString());

        assertThat(response.getBody(), is("ok"));
        assertThat(response.getMessage(), is("OK"));
        assertThat(response.getCode(), is(200));
    }

}
