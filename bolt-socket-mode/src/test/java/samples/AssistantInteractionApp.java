package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.builtin.Assistant;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.Message;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageEvent;

import java.security.SecureRandom;
import java.util.*;

import static com.slack.api.model.block.Blocks.actions;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.button;

public class AssistantInteractionApp {

    public static void main(String[] args) throws Exception {
        String botToken = System.getenv("SLACK_BOT_TOKEN");
        String appToken = System.getenv("SLACK_APP_TOKEN");

        App app = new App(AppConfig.builder()
                .singleTeamBotToken(botToken)
                .ignoringSelfAssistantMessageEventsEnabled(false)
                .build()
        );

        Assistant assistant = new Assistant(app.executorService());

        assistant.threadStarted((req, ctx) -> {
            try {
                ctx.say(r -> r
                        .text("Hi, how can I help you today?")
                        .blocks(Arrays.asList(
                                section(s -> s.text(plainText("Hi, how I can I help you today?"))),
                                actions(a -> a.elements(Collections.singletonList(
                                        button(b -> b.actionId("assistant-generate-numbers").text(plainText("Generate numbers")))
                                )))
                        ))
                );
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
            }
        });

        app.blockAction("assistant-generate-numbers", (req, ctx) -> {
            app.executorService().submit(() -> {
                Map<String, Object> eventPayload = new HashMap<>();
                eventPayload.put("num", 20);
                try {
                    ctx.client().chatPostMessage(r -> r
                            .channel(req.getPayload().getChannel().getId())
                            .threadTs(req.getPayload().getMessage().getThreadTs())
                            .text("OK, I will generate numbers for you!")
                            .metadata(Message.Metadata.builder().eventType("assistant-generate-numbers").eventPayload(eventPayload).build())
                    );
                } catch (Exception e) {
                    ctx.logger.error("Failed to post a bot message: {e}", e);
                }
            });
            return ctx.ack();
        });

        assistant.botMessage((req, ctx) -> {
            if (req.getEvent().getMetadata() != null
                    && req.getEvent().getMetadata().getEventType().equals("assistant-generate-numbers")) {
                try {
                    ctx.setStatus("is typing...");
                    Double num = (Double) req.getEvent().getMetadata().getEventPayload().get("num");
                    Set<String> numbers = new HashSet<>();
                    SecureRandom random = new SecureRandom();
                    while (numbers.size() < num) {
                        numbers.add(String.valueOf(random.nextInt(100)));
                    }
                    Thread.sleep(1000L);
                    ctx.say(r -> r.text("Her you are: " + String.join(", ", numbers)));
                } catch (Exception e) {
                    ctx.logger.error("Failed to handle assistant bot message event: {e}", e);
                }
            }
        });

        assistant.userMessage((req, ctx) -> {
            try {
                ctx.setStatus("is typing...");
                ctx.say(r -> r.text("Sorry, I couldn't understand your comment."));
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                try {
                    ctx.say(r -> r.text(":warning: Sorry, something went wrong during processing your request!"));
                } catch (Exception ee) {
                    ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                }
            }
        });


        app.assistant(assistant);

        app.event(MessageEvent.class, (req, ctx) -> {
            return ctx.ack();
        });

        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("You can help you at our 1:1 DM!");
            return ctx.ack();
        });

        new SocketModeApp(appToken, app).start();
    }
}
