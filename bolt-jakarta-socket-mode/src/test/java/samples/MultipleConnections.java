package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.jakarta_socket_mode.SocketModeApp;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageEvent;
import config.Constants;

public class MultipleConnections {

    public static void main(String[] args) throws Exception {
        String appToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);
        for (int i = 1; i <= 3; i++) {
            final int num = i;
            App app = new App(AppConfig.builder()
                    .singleTeamBotToken(System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN))
                    .build());
            app.event(AppMentionEvent.class, (req, ctx) -> {
                ctx.asyncClient().reactionsAdd(r -> r
                        .channel(req.getEvent().getChannel())
                        .name(reaction(num))
                        .timestamp(req.getEvent().getTs())
                );
                return ctx.ack();
            });
            app.event(MessageEvent.class, (req, ctx) -> ctx.ack());

            SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
            socketModeApp.startAsync();
            socketModeApp.stop();
            socketModeApp.startAsync();
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    private static String reaction(int num) {
        switch (num) {
            case 1:
                return "one";
            case 2:
                return "two";
            case 3:
                return "three";
            case 4:
                return "four";
            case 5:
                return "five";
            default:
                return "eyes";
        }
    }
}
