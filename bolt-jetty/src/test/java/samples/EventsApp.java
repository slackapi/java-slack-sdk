package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.jetty.SlackAppServer;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.MessageDeletedEvent;
import com.slack.api.model.event.MessageEvent;

import java.util.HashMap;
import java.util.Map;

public class EventsApp {

    public static void main(String[] args) throws Exception {
        App app = new App();

        app.event(AppMentionEvent.class, (event, ctx) -> {
            ctx.logger.info("new mention by a user - {}", event);
            ctx.say("Hi there!");
            return ctx.ack();
        });
        app.event(MessageEvent.class, (event, ctx) -> {
            ctx.logger.info("new message by a user - {}", event);
            ctx.say("Hi hi!");
            return ctx.ack();
        });
        app.event(MessageBotEvent.class, (event, ctx) -> {
            ctx.logger.info("bot message - {}", event);
            return ctx.ack();
        });
        app.event(MessageDeletedEvent.class, (event, ctx) -> {
            ctx.logger.info("message deleted - {}", event);
            return ctx.ack();
        });

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", app);
        apps.put("/slack/oauth/", new App().asOAuthApp(true));
        SlackAppServer server = new SlackAppServer(apps);
        server.start();
    }

}
