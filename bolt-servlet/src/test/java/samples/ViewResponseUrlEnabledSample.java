package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Responder;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.View;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.net.URLDecoder;

public class ViewResponseUrlEnabledSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_responseUrlEnabled.json");
        App app = new App(config);
        app.use((req, resp, chain) -> {
            String body = req.getRequestBodyAsString();
            String payload = body.startsWith("payload=") ? URLDecoder.decode(body.split("payload=")[1], "UTF-8") : body;
            req.getContext().logger.info(payload);
            return chain.next(req);
        });

        // src/test/resources
        String view1 = ResourceLoader.load("views/view4.json");
        app.command("/view", (req, ctx) -> {
            ViewsOpenResponse apiResponse = ctx.client().viewsOpen(r -> r
                    .triggerId(ctx.getTriggerId())
                    .viewAsString(view1)
            );
            if (apiResponse.isOk()) {
                return ctx.ack();
            } else {
                return ctx.ackWithJson(apiResponse);
            }
        });

        app.viewSubmission("view-callback-id", (req, ctx) -> {
            View view = req.getPayload().getView();
            ctx.logger.info("state - {}, private_metadata - {}", view.getState(), view.getPrivateMetadata());

            ctx.respond("[ephemeral] Thank you for submitting the data!");
            ctx.respond(r -> r.responseType("in_channel").text("[in_channel] Thank you for submitting the data!"));

            ctx.getResponder("b1", "a").send("This message was sent via response_url given by channels_select block element (block_id: b1, action_id: a)");

            String responseUrl = ctx.getResponseUrls().get(0).getResponseUrl();
            new Responder(ctx.getSlack(), responseUrl).sendFromModal(r -> r.text("Sent using a manually created responder"));

            return ctx.ack(); // just close the view
        });

        app.viewClosed("view-callback-id", (req, ctx) -> ctx.ack());

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
