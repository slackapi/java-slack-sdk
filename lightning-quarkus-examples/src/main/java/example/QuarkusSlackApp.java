package example;

import com.github.seratch.jslack.lightning.App;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.servlet.SlackAppServlet;

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
//        // export SLACK_BOT_TOKEN=xoxb-xxx-yyy
//        // export SLACK_SIGNING_SECRET=zzz
//        App app = new App();
        // src/main/resources/appConfig.json
        App app = new App(AppConfigLoader.load());
        app.command("/hello", (ctx, req) -> {
            return Response.ok("Thanks!");
        });
        return app;
    }
}
