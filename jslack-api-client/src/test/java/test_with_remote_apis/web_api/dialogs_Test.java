package test_with_remote_apis.web_api;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.dialog.DialogOpenResponse;
import com.github.seratch.jslack.api.model.dialog.Dialog;
import com.github.seratch.jslack.api.model.dialog.DialogSubType;
import com.github.seratch.jslack.api.model.dialog.DialogTextElement;
import config.Constants;
import config.SlackTestConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class dialogs_Test {

    Slack slack = Slack.getInstance(SlackTestConfig.get());
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void open() throws IOException, SlackApiException {

        DialogTextElement quantityTextElement = DialogTextElement.builder()
                .subtype(DialogSubType.NUMBER)
                .label("Quantity")
                .name("quantity")
                .hint("The number you need")
                .maxLength(3)
                .minLength(1)
                .placeholder("Required quantity")
                .value("1")
                .build();

        Dialog dialog = Dialog.builder()
                .title("Request pens")
                .callbackId("pens-1122")
                .elements(Arrays.asList(quantityTextElement))
                .state("some-state")
                .notifyOnCancel(true)
                .submitLabel("")
                .build();

        /*
         * A dialog in Slack can only be opened in response to a user action such as a slash command or
         * button click (which now include trigger_ids in callbacks). A dialog.open request has to include
         * that same trigger_id in order to succeed. The dialog.open request must also be made within 3
         * seconds of the user action.  Therefore, only an 'invalid trigger' ID response can be tested.
         */
        DialogOpenResponse dialogOpenResponse = slack.methods().dialogOpen(r -> r
                .token(token)
                .triggerId("FAKE_TRIGGER_ID")
                .dialog(dialog));
        assertThat(dialogOpenResponse.isOk(), is(false));
        assertThat(dialogOpenResponse.getError(), is("invalid_trigger"));
    }
}
