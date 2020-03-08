package test_locally.response;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.response.Responder;
import com.slack.api.webhook.WebhookResponse;
import org.junit.Test;
import util.WebhookMockServer;

import static org.junit.Assert.assertEquals;

public class ResponderTest {

    @Test
    public void test() throws Exception {
        WebhookMockServer slackApiServer = new WebhookMockServer();
        slackApiServer.start();
        Slack slack = Slack.getInstance();
        Responder responder = new Responder(slack, slackApiServer.getWebhookUrl());
        try {
            ActionResponse body = ActionResponse.builder().text("Hi").build();
            WebhookResponse response = responder.send(body);
            assertEquals(200L, response.getCode().longValue());

            WebhookResponse actionResponse = responder.sendToAction(r -> r.text("Hi"));
            assertEquals(200L, actionResponse.getCode().longValue());

            WebhookResponse commandResponse = responder.sendToCommand(r -> r.text("Hi"));
            assertEquals(200L, commandResponse.getCode().longValue());

            com.slack.api.app_backend.outgoing_webhooks.response.WebhookResponse outgoing =
                    com.slack.api.app_backend.outgoing_webhooks.response.WebhookResponse.builder().text("Hi").build();
            WebhookResponse outgoingWebhookResponse = responder.send(outgoing);
            assertEquals(200L, outgoingWebhookResponse.getCode().longValue());
        } finally {
            slackApiServer.stop();
        }
    }
}
