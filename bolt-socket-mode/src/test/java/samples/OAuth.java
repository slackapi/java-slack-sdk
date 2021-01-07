package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.servlet.SlackAppServer;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppMentionEvent;

import java.util.HashMap;
import java.util.Map;

public class OAuth {

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = AppConfig.builder()
                .clientId("111.222")
                .clientSecret("xxx")
                .scope("app_mentions:read,chat:write,commands")
                .oauthInstallPath("install")
                .oauthRedirectUriPath("oauth_redirect")
                .build();
        App app = new App(appConfig);

        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Hi there!");
            return ctx.ack();
        });

        String appToken = "xapp-1-A111-111-xxx";
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.startAsync();

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/", new App(appConfig).asOAuthApp(true));
        SlackAppServer oauthSever = new SlackAppServer(apps);
        oauthSever.start();
    }
}
