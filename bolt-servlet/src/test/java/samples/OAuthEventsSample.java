package samples;

import com.slack.api.SlackConfig;
import com.slack.api.app_backend.events.handler.MessageHandler;
import com.slack.api.app_backend.events.payload.MessagePayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.MessageDeletedEvent;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import util.TestSlackAppServer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class OAuthEventsSample {

    public static void main(String[] args) throws Exception {
        String jsonString = Files.readAllLines(Paths.get("bolt-servlet/src/test/resources/appConfig_oauth.json"))
                .stream().collect(Collectors.joining("\n"));
        AppConfig config = GsonFactory.createCamelCase(SlackConfig.DEFAULT).fromJson(jsonString, AppConfig.class);
        App app = new App(config).asOAuthApp(true);
        app.initialize();

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

        Map<String, App> apps = new HashMap<>();
        apps.put("/slack/events", app);
        apps.put("/slack/oauth/", new App(config).asOAuthApp(true));
        TestSlackAppServer server = new TestSlackAppServer(apps);
        server.start();
    }

}
