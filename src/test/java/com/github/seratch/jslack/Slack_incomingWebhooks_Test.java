package com.github.seratch.jslack;

import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Field;
import com.github.seratch.jslack.api.webhook.Payload;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class Slack_incomingWebhooks_Test {

    Slack slack = Slack.getInstance();

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
                .attachments(new ArrayList<>())
                .build();

        Attachment attachment = Attachment.builder()
                .text("This is an *attachment*.")
                .authorName("Smiling Imp")
                .color("#36a64f")
                .fallback("Required plain-text summary of the attachment.")
                .title("Slack API Documentation")
                .titleLink("https://api.slack.com/")
                .footer("footer")
                .fields(new ArrayList<>())
                .mrkdwnIn(new ArrayList<>())
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
    }

}
