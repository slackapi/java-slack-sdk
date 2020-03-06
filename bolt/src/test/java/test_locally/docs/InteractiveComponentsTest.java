package test_locally.docs;

import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.block.composition.PlainTextObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static java.util.stream.Collectors.toList;


public class InteractiveComponentsTest {

    App app = new App(AppConfig.builder().signingSecret("foo").signingSecret("xoxb-xxx").build());

    @Test
    public void example() {
        // when a user clicks a button in the actions block
        app.blockAction("button-action", (req, ctx) -> {
            String value = req.getPayload().getActions().get(0).getValue(); // "button's value"
            if (req.getPayload().getResponseUrl() != null) {
                // Post a message to the same channel if it's a block in a message
                ctx.respond("You've sent " + value + " by clicking the button!");
            }
            return ctx.ack();
        });

        final List<Option> allOptions = Arrays.asList(
                new Option(plainText("Schedule", true), "schedule"),
                new Option(plainText("Budget", true), "budget"),
                new Option(plainText("Assignment", true), "assignment")
        );

        // when a user enters some word in "Topics"
        app.blockSuggestion("topics-action", (req, ctx) -> {
            String keyword = req.getPayload().getValue();
            List<Option> options = allOptions.stream()
                    .filter(o -> ((PlainTextObject) o.getText()).getText().contains(keyword))
                    .collect(toList());
            return ctx.ack(r -> r.options(options.isEmpty() ? allOptions : options));
        });

// when a user chooses an item from the "Topics"
        app.blockAction("topics-action", (req, ctx) -> {
            return ctx.ack();
        });
    }

}
