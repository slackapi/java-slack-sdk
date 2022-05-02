package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;
import com.slack.api.model.event.*;
import lombok.extern.slf4j.Slf4j;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MessageMetadataSample {
    /*
settings:
  event_subscriptions:
    request_url: https://{your domain}/slack/events
    bot_events:
      - message_metadata_deleted
      - message_metadata_posted
      - message_metadata_updated
    metadata_subscriptions:
      - app_id: "*"
        event_type: java-sdk-test-example
     */

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.event(MessageMetadataPostedEvent.class, (event, ctx) -> {
            ctx.logger.info("MessageMetadataPostedEvent - {}", event);
            return ctx.ack();
        });
        app.event(MessageMetadataUpdatedEvent.class, (event, ctx) -> {
            ctx.logger.info("MessageMetadataUpdatedEvent - {}", event);
            return ctx.ack();
        });
        app.event(MessageMetadataDeletedEvent.class, (event, ctx) -> {
            ctx.logger.info("MessageMetadataDeletedEvent - {}", event);
            return ctx.ack();
        });

        app.event(AppHomeOpenedEvent.class, (event, ctx) -> ctx.ack());
        app.event(AppMentionEvent.class, (event, ctx) -> ctx.ack());
        app.event(MessageEvent.class, (event, ctx) -> ctx.ack());
        app.event(MessageChangedEvent.class, (event, ctx) -> ctx.ack());
        app.event(MessageDeletedEvent.class, (event, ctx) -> ctx.ack());

        app.executorService().submit(() -> {
            Map<String, Object> metadataPayload = new HashMap<>();
            metadataPayload.put("state", "initial");
            try {
                Thread.sleep(5000L);
                MethodsClient client = app.client();
                ChatPostMessageResponse newMessage = client.chatPostMessage(r -> r
                        .channel("#random")
                        .text("message with metadata")
                        .metadata(Message.Metadata.builder()
                                .eventType("java-sdk-test-example")
                                .eventPayload(metadataPayload)
                                .build())
                );

                Thread.sleep(3000L);
                metadataPayload.put("state", "modified");
                client.chatUpdate(r -> r
                        .channel(newMessage.getChannel())
                        .ts(newMessage.getTs())
                        .text("message with metadata (modified)")
                        .metadata(Message.Metadata.builder()
                                .eventType("java-sdk-test-example")
                                .eventPayload(metadataPayload)
                                .build())
                );

                Thread.sleep(3000L);
                client.chatDelete(r -> r.channel(newMessage.getChannel()).ts(newMessage.getTs()));

            } catch (Exception e) {
                log.error("Failed", e);
            }
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
