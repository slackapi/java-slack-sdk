package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.net.URLDecoder;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.view.Views.*;
import static org.junit.Assert.assertNotNull;

public class ShortcutsSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_shortcuts.json");
        App app = new App(config);
        app.use((req, resp, chain) -> {
            String body = req.getRequestBodyAsString();
            String payload = body.startsWith("payload=") ? URLDecoder.decode(body.split("payload=")[1], "UTF-8") : body;
            req.getContext().logger.info(payload);
            return chain.next(req);
        });

        app.globalShortcut("test-global-shortcut", (req, ctx) -> {
            ChatPostMessageResponse dm = ctx.client().chatPostMessage(r -> r
                    .channel(req.getPayload().getUser().getId())
                    .text("Thanks for invoking the global action (" + req.getPayload().getCallbackId() + ")"));
            ViewsOpenResponse viewsOpen = ctx.client().viewsOpen(r -> r
                    .triggerId(req.getContext().getTriggerId())
                    .view(view(v -> v
                            .type("modal")
                            .callbackId("test-view")
                            .title(viewTitle(vt -> vt.type("plain_text").text("Modal by Global Shortcut")))
                            .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                            .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                            .blocks(asBlocks(input(input -> input
                                    .blockId("agenda-block")
                                    .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
                                    .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                            )))
                    )));
            return ctx.ack();
        });

        app.viewSubmission("test-view", (req, ctx) -> ctx.ack());

        app.messageShortcut("test-message-shortcut", (req, ctx) -> {
            assertNotNull(req.getPayload().getResponseUrl());
            return ctx.ack();
        });
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }
}
