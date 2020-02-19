package samples;

import com.slack.api.bolt.App;
import lombok.extern.slf4j.Slf4j;
import samples.util.TestSlackAppServer;

@Slf4j
public class OAuthSample {

    public static void main(String[] args) throws Exception {
        App app = new App().asOAuthApp(true);

        app.oauthCallbackError((req, resp) -> {
            log.error("query string: {}", req.getQueryString());
           return resp;
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
