package example.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.http4k.Http4kSlackAppServer;
import com.slack.api.model.event.AppMentionEvent;
import org.http4k.server.SunHttp;

public class ExampleHttp4kBoltServer {

    public static void main(String[] args) {
        App app = new App();
        app.command("/hi", (req, ctx) -> ctx.ack());
        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Hi there!");
            return ctx.ack();
        });
        app.globalShortcut("test-shortcut", (req, ctx) -> ctx.ack());

        // This is the easiest way to start a Bolt App from Java.
        new Http4kSlackAppServer(app, new SunHttp(3000)).start();

        // from kotlin you can just do...
        // Http4kSlackApp(createApp(new AppConfig())).asServer(SunHttp(8080)).start()
    }

}
