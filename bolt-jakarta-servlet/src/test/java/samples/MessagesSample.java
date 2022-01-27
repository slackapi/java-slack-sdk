package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import lombok.extern.slf4j.Slf4j;
import util.ResourceLoader;
import util.TestSlackAppServer;

@Slf4j
public class MessagesSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.message("posted by a user", (event, ctx) -> {
            ctx.say("You're right. This is a message by a user <@" + event.getEvent().getUser() + "> !");
            return ctx.ack();
        });
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
