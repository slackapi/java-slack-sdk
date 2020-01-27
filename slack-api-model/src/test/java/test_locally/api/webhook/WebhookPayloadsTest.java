package test_locally.api.webhook;

import com.slack.api.webhook.Payload;
import org.junit.Test;

import static com.slack.api.model.Attachments.*;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.webhook.WebhookPayloads.payload;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class WebhookPayloadsTest {

    @Test
    public void test() {
        Payload payload = payload(it -> {
            return it.text("fallback")
                    .blocks(asBlocks(
                            section(s -> s.text(plainText("Hi"))),
                            image(i -> i.imageUrl("https://www.example.com/foo.png"))
                    ))
                    .attachments(asAttachments(
                            attachment(a -> a.actions(asActions(action(ac -> ac.name("foo"))))),
                            attachment(a -> a.fields(asFields(field(f -> f.title("title")))))
                    ));
        });
        assertThat(payload, is(notNullValue()));
    }
}
