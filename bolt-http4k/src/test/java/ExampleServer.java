import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.http4k.Http4kSlackAppServer;
import org.http4k.server.SunHttp;

import java.util.regex.Pattern;

public class ExampleServer {

    public static void main(String[] args) {
        // This is the easiest way to start a Bolt App from Java.
        new Http4kSlackAppServer(createApp(new AppConfig()), new SunHttp(8080)).start();

        // from kotlin you can just do...
        // Http4kSlackApp(createApp(new AppConfig())).asServer(SunHttp(8080)).start()
    }

    private static App createApp(AppConfig config) {
        App app = new App(config);
        app.command("/hello", (req, ctx) -> ctx.ack(r -> r.text("Thanks!")));
        app.command(Pattern.compile("/submission-no.\\d+"), (req, ctx) -> ctx.ack(r -> r.text(req.getPayload().getCommand())));
        return app;
    }
}
