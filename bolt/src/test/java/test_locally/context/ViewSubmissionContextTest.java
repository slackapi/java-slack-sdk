package test_locally.context;

import com.slack.api.Slack;
import com.slack.api.app_backend.views.InputBlockResponseSender;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.view.View;
import com.slack.api.webhook.WebhookResponse;
import org.junit.Test;
import util.WebhookMockServer;

import java.util.Arrays;
import java.util.Collections;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.divider;
import static com.slack.api.model.view.Views.view;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ViewSubmissionContextTest {

    View view = view(r -> r.type("modal").callbackId("callback"));

    @Test
    public void ack_viewAsString() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        Response response = context.ack("update", "{}");
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void ack_view() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        Response response = context.ack("update", view);
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void ackWithUpdate() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        {
            Response response = context.ackWithUpdate("{}");
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            Response response = context.ackWithUpdate(view);
            assertEquals(200L, response.getStatusCode().longValue());
        }
    }

    @Test
    public void ackWithPush() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        {
            Response response = context.ackWithPush("{}");
            assertEquals(200L, response.getStatusCode().longValue());
        }
        {
            Response response = context.ackWithPush(view);
            assertEquals(200L, response.getStatusCode().longValue());
        }
    }

    @Test
    public void ackWithErrors() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        Response response = context.ackWithErrors(Collections.emptyMap());
        assertEquals(200L, response.getStatusCode().longValue());
    }

    @Test
    public void respond_no_url() throws Exception {
        ViewSubmissionContext context = new ViewSubmissionContext();
        context.setSlack(Slack.getInstance());
        context.setResponseUrls(null);
        try {
            context.respond("Test");
            fail();
        } catch (IllegalStateException e) {
            assertEquals("This payload doesn't have response_urls. " +
                    "The response_urls are available only when your modal has input block elements with response_url_enabled = true.", e.getMessage());
        }
    }

    @Test
    public void respond() throws Exception {
        WebhookMockServer webhookMockServer = new WebhookMockServer();
        webhookMockServer.start();
        try {
            ViewSubmissionContext context = new ViewSubmissionContext();
            context.setSlack(Slack.getInstance());
            ViewSubmissionPayload.ResponseUrl responseUrl = new ViewSubmissionPayload.ResponseUrl();
            responseUrl.setBlockId("b1");
            responseUrl.setActionId("a");
            responseUrl.setChannelId("C123");
            responseUrl.setResponseUrl(webhookMockServer.getWebhookUrl());
            context.setResponseUrls(Arrays.asList(responseUrl));
            WebhookResponse response = context.respond("Test");
            assertEquals(200L, response.getCode().longValue());

        } finally {
            webhookMockServer.stop();
        }
    }

    @Test
    public void respond_blocks() throws Exception {
        WebhookMockServer webhookMockServer = new WebhookMockServer();
        webhookMockServer.start();
        try {
            ViewSubmissionContext context = new ViewSubmissionContext();
            context.setSlack(Slack.getInstance());
            ViewSubmissionPayload.ResponseUrl responseUrl = new ViewSubmissionPayload.ResponseUrl();
            responseUrl.setBlockId("b1");
            responseUrl.setActionId("a");
            responseUrl.setChannelId("C123");
            responseUrl.setResponseUrl(webhookMockServer.getWebhookUrl());
            context.setResponseUrls(Arrays.asList(responseUrl));
            WebhookResponse response = context.respond(asBlocks(divider()));
            assertEquals(200L, response.getCode().longValue());

        } finally {
            webhookMockServer.stop();
        }
    }

    @Test
    public void respond_lambda() throws Exception {
        WebhookMockServer webhookMockServer = new WebhookMockServer();
        webhookMockServer.start();
        try {
            ViewSubmissionContext context = new ViewSubmissionContext();
            context.setSlack(Slack.getInstance());
            ViewSubmissionPayload.ResponseUrl responseUrl = new ViewSubmissionPayload.ResponseUrl();
            responseUrl.setBlockId("b1");
            responseUrl.setActionId("a");
            responseUrl.setChannelId("C123");
            responseUrl.setResponseUrl(webhookMockServer.getWebhookUrl());
            context.setResponseUrls(Arrays.asList(responseUrl));
            WebhookResponse response = context.respond(r -> r.blocks(asBlocks(divider())).responseType("in_channel"));
            assertEquals(200L, response.getCode().longValue());

        } finally {
            webhookMockServer.stop();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResponder_absent() {
        ViewSubmissionContext context = new ViewSubmissionContext();
        context.getResponder("blockId", "actionId");
    }

    @Test
    public void getResponder() throws Exception {
        WebhookMockServer webhookMockServer = new WebhookMockServer();
        webhookMockServer.start();
        try {
            ViewSubmissionContext context = new ViewSubmissionContext();
            context.setSlack(Slack.getInstance());
            ViewSubmissionPayload.ResponseUrl responseUrl = new ViewSubmissionPayload.ResponseUrl();
            responseUrl.setBlockId("blockId");
            responseUrl.setActionId("actionId");
            responseUrl.setChannelId("C123");
            responseUrl.setResponseUrl(webhookMockServer.getWebhookUrl());
            context.setResponseUrls(Arrays.asList(responseUrl));
            InputBlockResponseSender responder = context.getResponder("blockId", "actionId");
            WebhookResponse response = responder.send("Hi");
            assertEquals(200L, response.getCode().longValue());
        } finally {
            webhookMockServer.stop();
        }
    }

}
