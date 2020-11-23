package test_locally.response;

import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.response.ActionResponse;
import com.slack.api.bolt.util.Responder;
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

            WebhookResponse modalResponse = responder.sendFromModal(r -> r.text("Hi"));
            assertEquals(200L, modalResponse.getCode().longValue());

        } finally {
            slackApiServer.stop();
        }
    }
}
