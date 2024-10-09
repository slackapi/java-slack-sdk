package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.middleware.builtin.Assistant;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.assistant.SuggestedPrompt;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageEvent;

import java.util.Collections;

public class AssistantSimpleApp {

    public static void main(String[] args) throws Exception {
        String botToken = System.getenv("SLACK_BOT_TOKEN");
        String appToken = System.getenv("SLACK_APP_TOKEN");

        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());

        Assistant assistant = new Assistant(app.executorService());

        assistant.threadStarted((req, ctx) -> {
            try {
                ctx.say(r -> r.text("Hi, how can I help you today?"));
                ctx.setSuggestedPrompts(Collections.singletonList(
                        SuggestedPrompt.create("What does SLACK stand for?")
                ));
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
            }
        });

        assistant.userMessage((req, ctx) -> {
            try {
                ctx.setStatus("is typing...");
                Thread.sleep(500L);
                if (ctx.getThreadContext() != null) {
                    String contextChannel = ctx.getThreadContext().getChannelId();
                    ctx.say(r -> r.text("I am ware of the channel context: <#" + contextChannel + ">"));
                } else {
                    ctx.say(r -> r.text("Here you are!"));
                }
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                try {
                    ctx.say(r -> r.text(":warning: Sorry, something went wrong during processing your request!"));
                } catch (Exception ee) {
                    ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                }
            }
        });

        assistant.userMessageWithFiles((req, ctx) -> {
            try {
                ctx.setStatus("is analyzing the files...");
                Thread.sleep(500L);
                ctx.setStatus("is still checking the files...");
                Thread.sleep(500L);
                ctx.say(r -> r.text("Your files do not have any issues!"));
            } catch (Exception e) {
                ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                try {
                    ctx.say(r -> r.text(":warning: Sorry, something went wrong during processing your request!"));
                } catch (Exception ee) {
                    ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                }
            }
        });

        app.use(assistant);

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
