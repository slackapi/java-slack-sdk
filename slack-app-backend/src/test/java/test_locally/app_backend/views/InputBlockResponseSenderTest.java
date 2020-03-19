package test_locally.app_backend.views;

import com.slack.api.Slack;
import com.slack.api.app_backend.views.InputBlockResponseSender;
import com.slack.api.app_backend.views.response.InputBlockResponse;
import com.slack.api.webhook.WebhookResponse;
import config.SlackTestConfig;
import org.junit.Test;
import util.MockWebhookServer;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.divider;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InputBlockResponseSenderTest {

    MockWebhookServer slackApiServer = new MockWebhookServer();

    @Test
    public void test() throws Exception {
        slackApiServer.start();
        try {
            Slack slack = Slack.getInstance(SlackTestConfig.get());
            InputBlockResponseSender sender = new InputBlockResponseSender(slack);

            InputBlockResponse data = InputBlockResponse.builder()
                    .text("Hi there!")
                    .build();
            WebhookResponse response = sender.send(slackApiServer.getWebhookUrl(), data);
            assertNotNull(response);
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void test2() throws Exception {
        slackApiServer.start();
        try {
            Slack slack = Slack.getInstance(SlackTestConfig.get());
            InputBlockResponseSender sender = new InputBlockResponseSender(slack, slackApiServer.getWebhookUrl());
            WebhookResponse response = sender.send(r -> r.text("Hi"));
            assertNotNull(response);
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            slackApiServer.stop();
        }
    }

    @Test
    public void text() throws Exception {
        slackApiServer.start();
        try {
            Slack slack = Slack.getInstance(SlackTestConfig.get());
            InputBlockResponseSender sender = new InputBlockResponseSender(slack, slackApiServer.getWebhookUrl());
            WebhookResponse response = sender.send("Hello");
            assertNotNull(response);
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            slackApiServer.stop();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void text_error() throws Exception {
        Slack slack = Slack.getInstance(SlackTestConfig.get());
        InputBlockResponseSender sender = new InputBlockResponseSender(slack);
        sender.send("Hello");
    }

    @Test
    public void blocks() throws Exception {
        slackApiServer.start();
        try {
            Slack slack = Slack.getInstance(SlackTestConfig.get());
            InputBlockResponseSender sender = new InputBlockResponseSender(slack, slackApiServer.getWebhookUrl());
            WebhookResponse response = sender.send(asBlocks(divider()));
            assertNotNull(response);
            assertEquals(200L, response.getCode().longValue());
            assertEquals("OK", response.getBody());
        } finally {
            slackApiServer.stop();
        }
    }

    @Test(expected = IllegalStateException.class)
    public void blocks_error() throws Exception {
        Slack slack = Slack.getInstance(SlackTestConfig.get());
        InputBlockResponseSender sender = new InputBlockResponseSender(slack);
        sender.send(asBlocks(divider()));
    }
}

