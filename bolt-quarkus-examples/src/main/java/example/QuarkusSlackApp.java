package example;

import com.slack.api.bolt.App;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.servlet.SlackAppServlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/slack/events")
public class QuarkusSlackApp extends SlackAppServlet {

    public QuarkusSlackApp() throws IOException {
        super(initSlackApp());
    }

    public QuarkusSlackApp(App app) {
        super(app);
    }

    private static App initSlackApp() throws IOException {
        // 1) Loading credentials from env variables
        // $ export SLACK_BOT_TOKEN=xoxb-xxx-yyy
        // $ export SLACK_SIGNING_SECRET=zzz
        App app = new App();

        // 2) Loading credentials from a resource file
        // src/main/resources/appConfig.json
        app = new App(AppConfigLoader.load());

        app.command("/hello", (ctx, req) -> {
            return Response.ok(":wave: Hi there!");
        });
        return app;
    }
}