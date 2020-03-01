package test_locally.context;

import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.response.Responder;
import com.slack.api.webhook.WebhookResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.WebhookMockServer;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ActionContextTest {

    WebhookMockServer server = new WebhookMockServer();
    SlackConfig config = new SlackConfig();
    Slack slack = Slack.getInstance(config);
    Responder responder = new Responder(slack, server.getWebhookUrl());

    @Before
    public void setup() throws Exception {
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void respond() throws IOException {
        ActionContext context = new ActionContext();
        context.setResponder(responder);
        WebhookResponse response = context.respond("Hello");
        assertEquals(200L, response.getCode().longValue());
        assertEquals("OK", response.getBody());
    }

    @Test
    public void respond_blocks() throws IOException {
        ActionContext context = new ActionContext();
        context.setResponder(responder);
        WebhookResponse response = context.respond(Arrays.asList());
        assertEquals(200L, response.getCode().longValue());
        assertEquals("OK", response.getBody());
    }

    @Test
    public void respond_lambda() throws IOException {
        ActionContext context = new ActionContext();
        context.setResponder(responder);
        WebhookResponse response = context.respond(r -> r.text("Thanks!"));
        assertEquals(200L, response.getCode().longValue());
        assertEquals("OK", response.getBody());
    }

    @Test
    public void delayedInit() {
        ActionContext context = new ActionContext();
        context.setSlack(Slack.getInstance());
        context.setResponseUrl("http://localhost/foo/bar");
        try {
            context.respond(":wave: hello!");
            fail("respond should have failed.");
        } catch (IOException e) {
        }
    }
}
