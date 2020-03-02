package test_locally.docs;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import org.junit.Test;

import java.time.ZonedDateTime;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.view.Views.*;

public class AppHomeTest {

    App app = new App(AppConfig.builder().signingSecret("foo").signingSecret("xoxb-xxx").build());

    @Test
    public void example() {
        // https://api.slack.com/events/app_home_opened
        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
            // Build a Home tab view
            ZonedDateTime now = ZonedDateTime.now();
            View appHomeView = view(view -> view
                    .type("home")
                    .blocks(asBlocks(
                            section(section -> section.text(markdownText(mt -> mt.text(":wave: Hello, App Home! (Last updated: " + now + ")")))),
                            image(img -> img.imageUrl("https://www.example.com/foo.png"))
                    ))
            );
            // Update the App Home for the given user
            ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                    .userId(payload.getEvent().getUser())
                    .hash(payload.getEvent().getView().getHash()) // To protect against possible race conditions
                    .view(appHomeView)
            );
            return ctx.ack();
        });
    }
}
