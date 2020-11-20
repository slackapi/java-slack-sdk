package samples;

import com.slack.api.bolt.App;
import util.TestSlackAppServer;

import java.util.HashMap;
import java.util.Map;

public class OAuthSample {

    public static void main(String[] args) throws Exception {
        App app = new App().asOAuthApp(true);

        app.oauthCallbackError((req, resp) -> {
            req.getContext().logger.error("query string: {}", req.getQueryString());
            return resp;
        });

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", app);
        apps.put("/slack/oauth/", new App().asOAuthApp(true));
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }

}
