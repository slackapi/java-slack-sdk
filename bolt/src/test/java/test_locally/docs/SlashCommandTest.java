package test_locally.docs;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import org.junit.Test;

public class SlashCommandTest {

    App app = new App(AppConfig.builder().signingSecret("foo").signingSecret("xoxb-xxx").build());

    @Test
    public void example1() {
        app.command("/echo", (req, ctx) -> {
            String commandArgText = req.getPayload().getText();
            String channelId = req.getPayload().getChannelId();
            String channelName = req.getPayload().getChannelName();
            String text = "You said " + commandArgText + " at <#" + channelId + "|" + channelName + ">";
            return ctx.ack(text); // respond with 200 OK
        });
    }

    String buildMessage(Request req) {
        return null;
    }

    @Test
    public void example2() {
        app.command("/echo", (req, ctx) -> {
            String text = buildMessage(req);
            ctx.respond(text); // perform an HTTP request
            return ctx.ack(); // respond with 200 OK
        });
    }
}
