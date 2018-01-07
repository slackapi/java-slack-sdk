package com.github.seratch.jslack;

import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.dialog.DialogOpenRequest;
import com.github.seratch.jslack.api.methods.response.dialog.DialogOpenResponse;
import com.github.seratch.jslack.api.model.dialog.Dialog;
import com.github.seratch.jslack.api.model.dialog.DialogSubType;
import com.github.seratch.jslack.api.model.dialog.DialogTextElement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@Slf4j
public class Slack_dialogs_Test {

    Slack slack = new Slack();
    String token = System.getenv(Constants.SLACK_TEST_OAUTH_ACCESS_TOKEN);

    @Test
    public void open() throws IOException, SlackApiException {

        DialogTextElement quanityTextElement = DialogTextElement.builder()
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
                .elements(Arrays.asList(quanityTextElement))
                .submitLabel("")
                .build();

        /*
         * A dialog in Slack can only be opened in response to a user action such as a slash command or
         * button click (which now include trigger_ids in callbacks). A dialog.open request has to include
         * that same trigger_id in order to succeed. The dialog.open request must also be made within 3
         * seconds of the user action.  Therefore, only an 'invalid trigger' ID response can be tested.
         */
        DialogOpenResponse dialogOpenResponse = slack.methods().dialogOpen(DialogOpenRequest.builder()
                .token(token)
                .triggerId("FAKE_TRIGGER_ID")
                .dialog(dialog)
                .build());
        assertThat(dialogOpenResponse.isOk(), is(false));
        assertThat(dialogOpenResponse.getError(), is("invalid_trigger"));
    }
}
