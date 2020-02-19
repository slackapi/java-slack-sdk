package samples;

import com.slack.api.bolt.App;
import samples.util.TestSlackAppServer;

public class OAuthSample {

    public static void main(String[] args) throws Exception {
        App app = new App().asOAuthApp(true);

        app.oauthCallbackError((req, resp) -> {
            req.getContext().logger.error("query string: {}", req.getQueryString());
            return resp;
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
