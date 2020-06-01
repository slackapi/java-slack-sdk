package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.View;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.net.URLDecoder;

public class ViewDefaultToCurrentConversationSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_defaultToCurrentConversation.json");
        App app = new App(config);
        app.use((req, resp, chain) -> {
            String body = req.getRequestBodyAsString();
            String payload = body.startsWith("payload=") ? URLDecoder.decode(body.split("payload=")[1], "UTF-8") : body;
            req.getContext().logger.info(payload);
            return chain.next(req);
        });

        // src/test/resources
        app.command("/view", (req, ctx) -> {
            String view = ResourceLoader.load("views/view5.json");
            ViewsOpenResponse apiResponse = ctx.client().viewsOpen(r ->
                    r.triggerId(ctx.getTriggerId()).viewAsString(view));
            if (apiResponse.isOk()) {
                return ctx.ack();
            } else {
                return ctx.ackWithJson(apiResponse);
            }
        });

        app.viewSubmission("view-callback-id", (req, ctx) -> {
            View view = req.getPayload().getView();
            ctx.logger.info("state - {}, private_metadata - {}", view.getState(), view.getPrivateMetadata());
            String conversationId = view.getState().getValues().get("b1").get("a").getSelectedConversation();
            ctx.say(r -> r.channel(conversationId).text("Thank you for submitting the data!"));
            return ctx.ack(); // just close the view
        });

        app.viewClosed("view-callback-id", (req, ctx) -> ctx.ack());

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
