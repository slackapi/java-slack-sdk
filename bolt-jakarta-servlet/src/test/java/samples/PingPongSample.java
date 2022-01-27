package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.block.LayoutBlock;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

public class PingPongSample {

    public static void main(String[] args) throws Exception {
        List<LayoutBlock> blocks = asBlocks(
                section(section -> section.text(markdownText(":wave: pong"))),
                actions(actions -> actions
                        .elements(asElements(
                                button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                        ))
                )
        );

        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);
        app.command("/ping", (req, ctx) -> ctx.ack(blocks));
        app.blockAction("ping-again", (req, ctx) -> {
            ctx.respond(blocks);
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }
}
