package test_locally;

import com.slack.api.Slack;
import com.slack.api.bolt.context.ActionRespondUtility;
import com.slack.api.bolt.response.Responder;
import com.slack.api.webhook.WebhookResponse;
import org.junit.Test;
import util.WebhookMockServer;

import static org.junit.Assert.assertEquals;

public class ActionRespondUtilityTest {

    Slack slackInstance = Slack.getInstance();

    @Test
    public void test() throws Exception {
        WebhookMockServer server = new WebhookMockServer();
        server.start();
        try {
            ActionRespondUtility util = new ActionRespondUtility() {
                private Responder responder;

                @Override
                public Slack getSlack() {
                    return slackInstance;
                }

                @Override
                public String getResponseUrl() {
                    return server.getWebhookUrl();
                }

                @Override
                public Responder getResponder() {
                    return responder;
                }

                @Override
                public void setResponder(Responder responder) {
                    this.responder = responder;
                }
            };
            WebhookResponse result = util.respond("hi");
            assertEquals("OK", result.getBody());
        } finally {
            server.stop();
        }
    }
}
