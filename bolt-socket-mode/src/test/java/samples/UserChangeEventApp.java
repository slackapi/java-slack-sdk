package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.UserChangeEvent;
import com.slack.api.model.event.UserHuddleChangedEvent;
import com.slack.api.model.event.UserProfileChangedEvent;
import com.slack.api.model.event.UserStatusChangedEvent;
import config.Constants;

public class UserChangeEventApp {

    public static void main(String[] args) throws Exception {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN))
                .build());
        app.use((req, resp, chain) -> {
            req.getContext().logger.info(req.getRequestBodyAsString());
            return chain.next(req);
        });

        app.event(UserChangeEvent.class, (payload, ctx) -> {
            return ctx.ack();
        });app.event(UserProfileChangedEvent.class, (payload, ctx) -> {
            return ctx.ack();
        });app.event(UserStatusChangedEvent.class, (payload, ctx) -> {
            return ctx.ack();
        });
        app.event(UserHuddleChangedEvent.class, (payload, ctx) -> {
            return ctx.ack();
        });
        String appToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}
