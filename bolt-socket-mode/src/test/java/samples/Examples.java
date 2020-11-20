package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageEvent;
import config.Constants;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.view.Views.*;

public class Examples {

    public static void main(String[] args) throws Exception {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN))
                .build());
        app.use((req, resp, chain) -> {
            req.getContext().logger.info(req.getRequestBodyAsString());
            return chain.next(req);
        });

        app.command("/hi-socket-mode", (req, ctx) -> {
            return ctx.ack("Yes, I'm running in the Socket Mode!");
        });

        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Hi there!");
            return ctx.ack();
        });

        app.event(MessageEvent.class, (req, ctx) -> {
            ctx.asyncClient().reactionsAdd(r -> r
                    .channel(req.getEvent().getChannel())
                    .name("eyes")
                    .timestamp(req.getEvent().getTs())
            );
            return ctx.ack();
        });

        app.globalShortcut("socket-mode-global-shortcut", (req, ctx) -> {
            ctx.asyncClient().viewsOpen(r -> r
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

        app.messageShortcut("socket-mode-message-shortcut", (req, ctx) -> {
            ctx.respond("It works!");
            return ctx.ack();
        });

        String appToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}
