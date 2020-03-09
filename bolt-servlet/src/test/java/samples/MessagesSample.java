package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

@Slf4j
public class MessagesSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.message("posted by a user", (event, ctx) -> {
            ctx.say("You're right. This is a message by a user <@" + event.getEvent().getUser() + "> !");
            return ctx.ack();
        });
        app.botMessage("posted by a bot", (event, ctx) -> {
            String botId = event.getEvent().getBotId();
            String userId = ctx.client().botsInfo(r -> r.bot(botId)).getBot().getUserId();
            ctx.say("You're right. This is a message by a bot user <@" + userId + "> !");
            return ctx.ack();
        });
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
