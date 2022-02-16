package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.event.ReactionAddedEvent;
import util.ResourceLoader;
import util.TestSlackAppServer;

public class EventsSample_WatchingYou {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        // config.getSlack().getConfig().setRateLimiterBackgroundJobIntervalMillis(3_000L);
        // config.getSlack().getConfig().setLibraryMaintainerMode(true);

        app.event(MessageEvent.class, (req, ctx) -> {
            String channel = req.getEvent().getChannel();
            String ts = req.getEvent().getTs();
            ctx.asyncClient().reactionsAdd(r -> r.channel(channel).timestamp(ts).name("eyes"));
            return ctx.ack();
        });

        app.event(ReactionAddedEvent.class, (req, ctx) -> ctx.ack());

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
