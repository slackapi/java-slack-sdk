package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.SharedChannelInviteAcceptedEvent;
import com.slack.api.model.event.SharedChannelInviteApprovedEvent;
import com.slack.api.model.event.SharedChannelInviteDeclinedEvent;
import com.slack.api.model.event.SharedChannelInviteReceivedEvent;
import lombok.extern.slf4j.Slf4j;
import util.ResourceLoader;
import util.TestSlackAppServer;

@Slf4j
public class EventsSample_SlackConnectInvites {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_SlackConnectReceiver.json");
        App app = new App(config);

        app.event(SharedChannelInviteReceivedEvent.class, (event, ctx) -> {
            ctx.logger.info("received - {}", event);
            return ctx.ack();
        });
        app.event(SharedChannelInviteAcceptedEvent.class, (event, ctx) -> {
            ctx.logger.info("accepted - {}", event);
            return ctx.ack();
        });
        app.event(SharedChannelInviteApprovedEvent.class, (event, ctx) -> {
            ctx.logger.info("approved - {}", event);
            return ctx.ack();
        });
        app.event(SharedChannelInviteDeclinedEvent.class, (event, ctx) -> {
            ctx.logger.info("declined - {}", event);
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
