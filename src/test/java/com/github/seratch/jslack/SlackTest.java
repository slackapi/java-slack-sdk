package com.github.seratch.jslack;

import com.github.seratch.jslack.rtm.RTMClient;
import com.github.seratch.jslack.rtm.RTMMessageHandler;
import com.github.seratch.jslack.webhook.Attachment;
import com.github.seratch.jslack.webhook.Field;
import com.github.seratch.jslack.webhook.Payload;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

@Slf4j
public class SlackTest {

    @Test
    public void incomingWebhook() throws IOException {
        // String url = "https://hooks.slack.com/services/T00000000/B00000000/XXXXXXXXXXXXXXXXXXXXXXXX";
        String url = System.getenv("SLACK_WEBHOOK_TEST_URL");

        Slack slack = new Slack();
        Payload payload = new Payload();
        payload.setText("Hello World!");
        payload.setIconEmoji(":smile_cat:");
        payload.setUsername("jSlack");
        // payload.setChannel("@seratch");
        payload.setChannel("#random");
        Attachment attachment = new Attachment();
        attachment.setText("This is an attachment.");
        attachment.setAuthorName("Smiling Imp");
        attachment.setColor("#36a64f");
        attachment.setFallback("Required plain-text summary of the attachment.");
        attachment.setTitle("Slack API Documentation");
        attachment.setTitleLink("https://api.slack.com/");
        attachment.setFooter("footer");

        {
            Field field = new Field();
            field.setTitle("Long Title");
            field.setValue("Long Value........................................................");
            field.setValueShortEnough(false);
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        {
            Field field = new Field();
            field.setTitle("Short Title");
            field.setValue("Short Value");
            field.setValueShortEnough(true);
            attachment.getFields().add(field);
            attachment.getFields().add(field);
        }
        payload.getAttachments().add(attachment);

        Response response = slack.send(url, payload);
        log.info(response.toString());
    }

    @Test
    public void rtm() throws Exception {
        JsonParser jsonParser = new JsonParser();
        String token = System.getenv("SLACK_BOT_TEST_API_TOKEN");
        Slack slack = new Slack(token);
        try (RTMClient rtm = slack.createRTMClient()) {
            RTMMessageHandler handler1 = (message) -> {
                JsonObject json = jsonParser.parse(message).getAsJsonObject();
                if (json.get("type") != null) {
                    log.info("Handled type: {}", json.get("type").getAsString());
                }
            };
            RTMMessageHandler handler2 = (message) -> {
                log.info("Hello!");
            };
            rtm.addMessageHandler(handler1);
            rtm.addMessageHandler(handler1);
            rtm.addMessageHandler(handler2);
            rtm.connect();

            Thread.sleep(10000);
            // Try anything on the channel...

            rtm.removeMessageHandler(handler2);

            Thread.sleep(10000);
            // Try anything on the channel...

        }
    }

}