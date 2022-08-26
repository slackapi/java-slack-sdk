package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.methods.request.chat.ChatUnfurlRequest;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.event.LinkSharedEvent;
import com.slack.api.model.event.MessageChangedEvent;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.util.json.GsonFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.slack.api.model.block.Blocks.actions;
import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

public class UnfurlingApp {

    public static void main(String[] args) throws Exception {
        String appToken = System.getenv("SLACK_APP_TOKEN");
        String botToken = System.getenv("SLACK_BOT_TOKEN");
        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());
        app.use((req, resp, chain) -> {
            req.getContext().logger.info(req.getRequestBodyAsString());
            return chain.next(req);
        });

        app.event(MessageEvent.class, (payload, ctx) -> ctx.ack());
        app.event(MessageChangedEvent.class, (payload, ctx) -> ctx.ack());

        app.event(LinkSharedEvent.class, (payload, ctx) -> {
            app.executorService().submit(() -> {
                try {
                    Map<String, ChatUnfurlRequest.UnfurlDetail> unfurls = new HashMap<>();
                    for (LinkSharedEvent.Link link : payload.getEvent().getLinks()) {
                        ChatUnfurlRequest.UnfurlDetail unfurl = new ChatUnfurlRequest.UnfurlDetail();
                        // unfurl.setTitle("Collaborate & Create Amazing Graphic Design for Free");
                        // unfurl.setText("text text text");
                        List<LayoutBlock> blocks = asBlocks(actions(a -> a.blockId("b").elements(asElements(
                                button(b -> b.actionId("a").value("clicked").text(plainText("Click this!")))
                        ))));
                        unfurl.setBlocks(blocks);
                        ctx.logger.info(GsonFactory.createSnakeCase().toJson(unfurl));
                        unfurls.put(link.getUrl(), unfurl);
                    }
                    ctx.client().chatUnfurl(r -> r
                            .channel(payload.getEvent().getChannel())
                            .ts(payload.getEvent().getMessageTs())
                            .source(payload.getEvent().getSource())
                            .unfurls(unfurls)
                    );
                } catch (Exception e) {
                    ctx.logger.error("Failed to unfurl the links", e);
                }
            });
            return ctx.ack();
        });

        app.blockAction("a", (req, ctx) -> {
            ctx.logger.info(req.getRequestBodyAsString());
            return ctx.ack();
        });

        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}
