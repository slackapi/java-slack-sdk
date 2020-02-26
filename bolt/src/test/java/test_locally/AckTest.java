package test_locally;

import com.slack.api.app_backend.dialogs.response.DialogSubmissionErrorResponse;
import com.slack.api.app_backend.dialogs.response.DialogSuggestionResponse;
import com.slack.api.app_backend.dialogs.response.Error;
import com.slack.api.bolt.App;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.model.view.View;
import org.junit.Test;

import java.util.*;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.view.Views.*;

public class AckTest {

    App app = new App();

    @Test
    public void event() {
        // only ack with an empty body supported
        app.event(MessageEvent.class, (req, ctx) -> ctx.ack());
    }

    @Test
    public void command() {
        app.command("/foo", (req, ctx) -> ctx.ack());
        app.command("/foo", (req, ctx) -> ctx.ack("bar"));
        app.command("/foo", (req, ctx) -> ctx.ack(asBlocks(section(s -> s.text(markdownText("*bold* _italic_"))), divider())));
        app.command("/foo", (req, ctx) -> ctx.ack(r -> r.text("bar")));
    }

    @Test
    public void messageAction() {
        // TODO
        app.messageAction("callback-id", (req, ctx) -> ctx.ack());
    }

    @Test
    public void blockAction() {
        // only ack with an empty body supported
        app.blockAction("action-id", (req, ctx) -> ctx.ack());
    }

    @Test
    public void blockSuggestion() {
        // only ack with an empty body supported
        app.blockSuggestion("action-id", (req, ctx) -> ctx.ack());
    }

    @Test
    public void viewSubmission() {
        Map<String, String> errors = new HashMap<>();
        errors.put("block_id", "error message");

        View view = view(v -> v
                .callbackId("callback-id")
                .title(viewTitle(vt -> vt.text("title")))
                .submit(viewSubmit(vs -> vs.text("submit")))
                .close(viewClose(vc -> vc.text("cancel")))
                .notifyOnClose(true)
                .blocks(asBlocks(divider())));

        String viewAsString = "{\n" +
                "  \"callback_id\": \"meeting-arrangement\",\n" +
                "  \"type\": \"modal\",\n" +
                "  \"notify_on_close\": true,\n" +
                "  \"title\": { \"type\": \"plain_text\", \"text\": \"Meeting Arrangement\", \"emoji\": true },\n" +
                "  \"submit\": { \"type\": \"plain_text\", \"text\": \"Submit\", \"emoji\": true },\n" +
                "  \"close\": { \"type\": \"plain_text\", \"text\": \"Cancel\", \"emoji\": true },\n" +
                "  \"blocks\": [\n" +
                "    {\n" +
                "      \"block_id\": \"title-block\",\n" +
                "      \"type\": \"input\",\n" +
                "      \"element\": { \"action_id\": \"title-action\", \"type\": \"plain_text_input\", \"initial_value\": \"${commandArg}\" },\n" +
                "      \"label\": { \"type\": \"plain_text\", \"text\": \"Meeting Title\", \"emoji\": true }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // close
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ack());

        // update
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ack(r -> r.responseAction("update").view(view)));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ack(r -> r.responseAction("update").viewAsString(viewAsString)));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ackWithUpdate(view));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ackWithUpdate(viewAsString));

        // push
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ack(r -> r.responseAction("push").view(view)));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ack(r -> r.responseAction("push").viewAsString(viewAsString)));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ackWithPush(view));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ackWithPush(viewAsString));

        // errors
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ack(r -> r.responseAction("errors").errors(errors)));
        app.viewSubmission("callback-id", (req, ctx) -> ctx.ackWithErrors(errors));
    }

    @Test
    public void viewClosed() {
        // only ack with an empty body supported
        app.viewClosed("callback-id", (req, ctx) -> ctx.ack());
    }

    @Test
    public void dialogSubmission() {
        // ok
        app.dialogSubmission("callback-id", (req, ctx) -> ctx.ack());

        List<Error> errors = new ArrayList<>();
        errors.add(Error.builder().name("name").error("something wrong").build());
        app.dialogSubmission("callback-id", (req, ctx) -> ctx.ack(errors));

        DialogSubmissionErrorResponse error = DialogSubmissionErrorResponse.builder().errors(errors).build();
        app.dialogSubmission("callback-id", (req, ctx) -> ctx.ack(error));
    }

    @Test
    public void dialogSuggestion() {
        app.dialogSuggestion("callback-id", (req, ctx) -> ctx.ack());

        app.dialogSuggestion("callback-id", (req, ctx) -> ctx.ack(res ->
                res.options(Collections.emptyList()).optionGroups(Collections.emptyList())));

        DialogSuggestionResponse response = DialogSuggestionResponse.builder()
                .options(Collections.emptyList())
                .optionGroups(Collections.emptyList())
                .build();
        app.dialogSuggestion("callback-id", (req, ctx) -> ctx.ack(response));
    }

    @Test
    public void dialogCancellation() {
        // only ack with an empty body supported
        app.dialogCancellation("callback-id", (req, ctx) -> ctx.ack());
    }

    @Test
    public void attachmentAction() {
        app.attachmentAction("callback-id", (req, ctx) -> ctx.ack());
    }

    @Test
    public void webhook() {
        app.webhook("trigger word", (req, ctx) -> ctx.ack());
        app.webhook("trigger word", (req, ctx) -> ctx.ack("Thanks!"));
    }
}
