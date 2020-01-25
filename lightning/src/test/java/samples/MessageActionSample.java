package samples;

import com.github.seratch.jslack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.github.seratch.jslack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.View;
import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

@Slf4j
public class MessageActionSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        // https://api.slack.com/apps/{your apiAppId}/interactive-messages
        app.messageAction("open-view", (req, ctx) -> {
            // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/views/view3.json
            String view = ResourceLoader.load("views/view3.json");

            ChatGetPermalinkResponse permalinkResponse = ctx.client().chatGetPermalink(r -> r
                    .channel(req.getPayload().getChannel().getId())
                    .messageTs(req.getPayload().getMessageTs()));
            if (!permalinkResponse.isOk()) {
                log.error("Failed to get a permalink - {}", permalinkResponse.getError());
            }

            final String finalizedView = view
                    .replaceFirst("REPLACE_WITH_SOME_DATA", req.getRequestBodyAsString())
                    .replaceFirst("REPLACE_WITH_THE_URL", permalinkResponse.getPermalink())
                    .replaceFirst("REPLACE_WITH_THE_MESSAGE", req.getPayload().getMessage().getText());

            ViewsOpenResponse apiResponse = ctx.client().viewsOpen(r -> r
                    .triggerId(ctx.getTriggerId())
                    .viewAsString(finalizedView));
            if (!apiResponse.isOk()) {
                log.error("Failed to open a view - {}", apiResponse.getError());
            }
            return ctx.ack();
        });

        app.viewSubmission("read-it-later", (req, ctx) -> {
            // save the data
            View view = req.getPayload().getView();
            log.info("state - {}, private_metadata - {}", view.getState(), view.getPrivateMetadata());
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
