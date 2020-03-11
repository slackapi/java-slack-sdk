package samples;

import com.slack.api.app_backend.events.handler.MessageHandler;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.MessageDeletedEvent;
import com.slack.api.model.event.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import util.ResourceLoader;
import util.TestSlackAppServer;

@Slf4j
public class EventsSample {

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.event(MessageEvent.class, (event, ctx) -> {
            ctx.logger.info("new message by a user - {}", event);
            ctx.say("Hi there!");
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

        app.event(new MessageHandler() {
            @Override
            public void handle(MessagePayload payload) {
                log.info("message event (MessageHandler) - {}", payload);
            }
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
