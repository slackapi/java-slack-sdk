package test_locally.docs;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatGetPermalinkResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.event.ReactionAddedEvent;
import org.junit.Test;

import java.util.regex.Pattern;

public class EventsApiTest {

    App app = new App(AppConfig.builder().signingSecret("foo").signingSecret("xoxb-xxx").build());

    @Test
    public void example1() {
        app.event(ReactionAddedEvent.class, (payload, ctx) -> {
            ReactionAddedEvent event = payload.getEvent();
            if (event.getReaction().equals("white_check_mark")) {
                ChatPostMessageResponse message = ctx.client().chatPostMessage(r -> r
                        .channel(event.getItem().getChannel())
                        .threadTs(event.getItem().getTs())
                        .text("<@" + event.getUser() + "> Thank you! We greatly appreciate your efforts :two_hearts:"));
                if (!message.isOk()) {
                    ctx.logger.error("chat.postMessage failed: {}", message.getError());
                }
            }
            return ctx.ack();
        });

        String notificationChannelId = "D1234567";
        Pattern sdk = Pattern.compile(".*[(Java SDK)|(Bolt)|(slack\\-java\\-sdk)].*", Pattern.CASE_INSENSITIVE);
        Pattern issues = Pattern.compile(".*[(bug)|(t work)|(issue)|(support)].*", Pattern.CASE_INSENSITIVE);

        app.event(MessageEvent.class, (payload, ctx) -> {
            MessageEvent event = payload.getEvent();
            String text = event.getText();
            // check if the message contains some monitoring keywords
            if (sdk.matcher(text).matches() && issues.matcher(text).matches()) {

                MethodsClient client = ctx.client();

                // Add 👀reacji to the message
                String channelId = event.getChannel();
                String ts = event.getTs();
                ReactionsAddResponse reaction = client.reactionsAdd(r -> r.channel(channelId).timestamp(ts).name("eyes"));
                if (!reaction.isOk()) {
                    ctx.logger.error("reactions.add failed: {}", reaction.getError());
                }

                // Send the message to the SDK author
                ChatGetPermalinkResponse permalink = client.chatGetPermalink(r -> r.channel(channelId).messageTs(ts));
                if (permalink.isOk()) {
                    ChatPostMessageResponse message = client.chatPostMessage(r -> r
                            .channel(notificationChannelId)
                            .text("An issue with the Java SDK might be reported:\n" + permalink.getPermalink())
                            .unfurlLinks(true));
                    if (!message.isOk()) {
                        ctx.logger.error("chat.postMessage failed: {}", message.getError());
                    }
                } else {
                    ctx.logger.error("chat.getPermalink failed: {}", permalink.getError());
                }
            }
            return ctx.ack();
        });
    }

}
