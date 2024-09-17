package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.assistant.SuggestedPrompt;
import com.slack.api.model.event.*;

import java.util.Collections;

public class AssistantEventListenerApp {

    public static void main(String[] args) throws Exception {
        String botToken = System.getenv("SLACK_BOT_TOKEN");
        String appToken = System.getenv("SLACK_APP_TOKEN");

        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());

        app.event(AssistantThreadStartedEvent.class, (req, ctx) -> {
            String channelId = req.getEvent().getAssistantThread().getChannelId();
            String threadTs = req.getEvent().getAssistantThread().getThreadTs();
            app.executorService().submit(() -> {
                try {
                    ctx.client().assistantThreadsSetTitle(r -> r
                            .channelId(channelId)
                            .threadTs(threadTs)
                            .title("New chat")
                    );
                    ctx.client().chatPostMessage(r -> r
                            .channel(channelId)
                            .threadTs(threadTs)
                            .text("Hi, how can I help you today?")
                    );
                    ctx.client().assistantThreadsSetSuggestedPrompts(r -> r
                            .channelId(channelId)
                            .threadTs(threadTs)
                            .prompts(Collections.singletonList(new SuggestedPrompt("What does SLACK stand for?")))
                    );
                } catch (Exception e) {
                    ctx.logger.error("Failed to handle assistant thread started event: {e}", e);
                }
            });
            return ctx.ack();
        });

        app.event(AssistantThreadContextChangedEvent.class, (req, ctx) -> {
            app.executorService().submit(() -> {
                String channelId = req.getEvent().getAssistantThread().getChannelId();
                String threadTs = req.getEvent().getAssistantThread().getThreadTs();
                // TODO: Store req.getEvent().getAssistantThread() for the following conversation
            });
            return ctx.ack();
        });

        app.event(MessageEvent.class, (req, ctx) -> {
            if (req.getEvent().getChannelType().equals("im") && req.getEvent().getThreadTs() != null) {
                String channelId = req.getEvent().getChannel();
                String threadTs = req.getEvent().getThreadTs();
                app.executorService().submit(() -> {
                    try {
                        ctx.client().assistantThreadsSetStatus(r -> r
                                .channelId(channelId)
                                .threadTs(threadTs)
                                .status("is typing...")
                        );
                        Thread.sleep(500L);
                        ctx.client().chatPostMessage(r -> r
                                .channel(channelId)
                                .threadTs(threadTs)
                                .text("Here you are!")
                        );
                    } catch (Exception e) {
                        ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                        try {
                            ctx.client().chatPostMessage(r -> r
                                    .channel(channelId)
                                    .threadTs(threadTs)
                                    .text(":warning: Sorry, something went wrong during processing your request!")
                            );
                        } catch (Exception ee) {
                            ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                        }
                    }

                });
            }
            return ctx.ack();
        });

        app.event(MessageFileShareEvent.class, (req, ctx) -> {
            if (req.getEvent().getChannelType().equals("im") && req.getEvent().getThreadTs() != null) {
                String channelId = req.getEvent().getChannel();
                String threadTs = req.getEvent().getThreadTs();
                app.executorService().submit(() -> {
                    try {
                        ctx.client().assistantThreadsSetStatus(r -> r
                                .channelId(channelId)
                                .threadTs(threadTs)
                                .status("is analyzing the files...")
                        );
                        Thread.sleep(500L);
                        ctx.client().assistantThreadsSetStatus(r -> r
                                .channelId(channelId)
                                .threadTs(threadTs)
                                .status("is still checking the files...")
                        );
                        Thread.sleep(500L);
                        ctx.client().chatPostMessage(r -> r
                                .channel(channelId)
                                .threadTs(threadTs)
                                .text("Your files do not have any issues!")
                        );
                    } catch (Exception e) {
                        ctx.logger.error("Failed to handle assistant user message event: {e}", e);
                        try {
                            ctx.client().chatPostMessage(r -> r
                                    .channel(channelId)
                                    .threadTs(threadTs)
                                    .text(":warning: Sorry, something went wrong during processing your request!")
                            );
                        } catch (Exception ee) {
                            ctx.logger.error("Failed to inform the error to the end-user: {ee}", ee);
                        }
                    }

                });
            }
            return ctx.ack();
        });

        app.event(MessageChangedEvent.class, (req, ctx) -> {
            return ctx.ack();
        });
        app.event(MessageDeletedEvent.class, (payload, ctx) -> {
            return ctx.ack();
        });

        new SocketModeApp(appToken, app).start();
    }
}
