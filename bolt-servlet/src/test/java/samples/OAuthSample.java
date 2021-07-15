package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.AppMentionEvent;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.HashMap;
import java.util.Map;

public class OAuthSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig("appConfig_oauth.json");
        config.setTokenRotationExpirationMillis(1000 * 60 * 60 * 24 * 365); // for testing
        App app = new App(config).asOAuthApp(true)
                // Enable built-in tokens_revoked / app_uninstalled event handlers
                .enableTokenRevocationHandlers();

        // NOTE: the following also works in the same way:
        // app.event(TokensRevokedEvent.class, app.defaultTokensRevokedEventHandler());
        // app.event(AppUninstalledEvent.class, app.defaultAppUninstalledEventHandler());

        app.event(AppMentionEvent.class, (req, ctx) -> {
            app.executorService().submit(() -> {
                try {
                    ctx.say("Hi there, <@" + req.getEvent().getUser() + ">!");
                } catch (Exception e) {
                    ctx.logger.error("Failed to post a message", e);
                }
            });
            return ctx.ack();
        });

        app.command("/token-rotation-modal", (req, ctx) -> ctx.ack("Hi!"));

        app.oauthCallbackError((req, resp) -> {
            req.getContext().logger.error("query string: {}", req.getQueryString());
            resp.setBody("<html><body>error</body></html>");
            resp.setContentType("text/html; charset=utf-8");
            return resp;
        });

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", app);
        apps.put("/slack/oauth/", app);
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }

}
