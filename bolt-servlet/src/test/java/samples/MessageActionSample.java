package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.response.Responder;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.View;
import util.ResourceLoader;
import util.TestSlackAppServer;

public class MessageActionSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        // https://api.slack.com/apps/{your apiAppId}/interactive-messages
        app.messageAction("open-view", (req, ctx) -> {
            // https://github.com/slackapi/java-slack-sdk/blob/master/bolt/src/test/resources/views/view3.json
            String view = ResourceLoader.load("views/view3.json");

            ChatGetPermalinkResponse permalinkResponse = ctx.client().chatGetPermalink(r -> r
                    .channel(req.getPayload().getChannel().getId())
                    .messageTs(req.getPayload().getMessageTs()));
            if (!permalinkResponse.isOk()) {
                ctx.logger.error("Failed to get a permalink - {}", permalinkResponse.getError());
            }

            final String finalizedView = view
                    .replaceFirst("REPLACE_WITH_SOME_DATA", req.getPayload().getResponseUrl())
                    .replaceFirst("REPLACE_WITH_THE_URL", permalinkResponse.getPermalink())
                    .replaceFirst("REPLACE_WITH_THE_MESSAGE", req.getPayload().getMessage().getText());

            ViewsOpenResponse apiResponse = ctx.client().viewsOpen(r -> r
                    .triggerId(ctx.getTriggerId())
                    .viewAsString(finalizedView));
            if (!apiResponse.isOk()) {
                ctx.logger.error("Failed to open a view - {}", apiResponse.getError());
            }
            return ctx.ack();
        });

        app.viewSubmission("read-it-later", (req, ctx) -> {
            // save the data
            View view = req.getPayload().getView();
            ctx.logger.info("state - {}, private_metadata - {}", view.getState(), view.getPrivateMetadata());

            String responseUrl = view.getPrivateMetadata();
            new Responder(ctx.getSlack(), responseUrl).sendToAction(r -> r.text("Thanks!"));

            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
