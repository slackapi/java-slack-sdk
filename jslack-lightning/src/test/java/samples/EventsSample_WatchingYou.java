package samples;

import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.model.event.MessageEvent;
import com.github.seratch.jslack.api.model.event.ReactionAddedEvent;
import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.AppConfig;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

@Slf4j
public class EventsSample_WatchingYou {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.event(MessageEvent.class, (req, ctx) -> {
            String channel = req.getEvent().getChannel();
            String ts = req.getEvent().getTs();
            ReactionsAddResponse res = ctx.client().reactionsAdd(r -> r.channel(channel).timestamp(ts).name("eyes"));
            log.info("reactions.add - {}", res);
            return ctx.ack();
        });

        app.event(ReactionAddedEvent.class, (req, ctx) -> ctx.ack());

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
