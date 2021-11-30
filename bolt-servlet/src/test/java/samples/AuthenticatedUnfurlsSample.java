package samples;

import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.methods.response.chat.ChatUnfurlResponse;
import com.slack.api.model.event.LinkSharedEvent;
import com.slack.api.model.event.MessageEvent;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.concurrent.CompletableFuture;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

public class AuthenticatedUnfurlsSample {

    public static CompletableFuture<ChatUnfurlResponse> displayUserAuthRequired(
            EventsApiPayload<LinkSharedEvent> req,
            EventContext ctx
    ) {
        return ctx.asyncClient().chatUnfurl(r -> r
                .channel(ctx.getChannelId())
                .ts(req.getEvent().getMessageTs())
                .userAuthRequired(true)
        );
    }

    public static CompletableFuture<ChatUnfurlResponse> displayUserAuthMessage(
            EventsApiPayload<LinkSharedEvent> req,
            EventContext ctx
    ) {
        return ctx.asyncClient().chatUnfurl(r -> r
                .channel(ctx.getChannelId())
                .ts(req.getEvent().getMessageTs())
                .userAuthMessage("Loading the preview... <https://www.example.com/auth|Connect your account>")
        );
    }

    public static CompletableFuture<ChatUnfurlResponse> displayUserAuthUrl(
            EventsApiPayload<LinkSharedEvent> req,
            EventContext ctx
    ) {
        return ctx.asyncClient().chatUnfurl(r -> r
                .channel(ctx.getChannelId())
                .ts(req.getEvent().getMessageTs())
                .userAuthUrl("https://www.example.com/auth")
        );
    }

    public static CompletableFuture<ChatUnfurlResponse> displayUserAuthBlocks(
            EventsApiPayload<LinkSharedEvent> req,
            EventContext ctx
    ) {
        return ctx.asyncClient().chatUnfurl(r -> r
                .channel(ctx.getChannelId())
                .ts(req.getEvent().getMessageTs())
                .userAuthBlocks(asBlocks(
                        section(s -> s
                                .blockId("intro-section")
                                .text(plainText("Loading the preview... :eyes:"))
                        ),
                        actions(a -> a.elements(asElements(
                                button(b -> b
                                        .actionId("button-click")
                                        .url("https://www.example.com/auth")
                                        .text(plainText("Connect your account"))
                                )
                        )))
                ))
        );
    }

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);
        app.event(MessageEvent.class, (req, ctx) -> ctx.ack());
        app.event(LinkSharedEvent.class, (req, ctx) -> {
            displayUserAuthRequired(req, ctx);
            displayUserAuthMessage(req, ctx);
            displayUserAuthBlocks(req, ctx);
            displayUserAuthUrl(req, ctx);
            return ctx.ack();
        });
        app.blockAction("button-click", (req, ctx) -> ctx.ack());

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }
}
