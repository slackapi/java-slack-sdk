package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.MessageChangedEvent;
import com.slack.api.model.event.MessageDeletedEvent;
import com.slack.api.model.event.MessageEvent;
import config.Constants;

public class ConcurrencyTestApp {

    public static void main(String[] args) throws Exception {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN))
                .build());

        app.event(MessageEvent.class, (req, ctx) -> {
            // Without concurrency option, this time-consuming task slows the whole message processing mechanism down
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ctx.asyncClient().reactionsAdd(r -> r
                    .channel(req.getEvent().getChannel())
                    .name("eyes")
                    .timestamp(req.getEvent().getTs())
            );
            return ctx.ack();
        });
        app.event(MessageChangedEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageDeletedEvent.class, (req, ctx) -> ctx.ack());

        String appToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);
//        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app, 10);
        socketModeApp.start();
    }
}
