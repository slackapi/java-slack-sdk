package samples;

import com.slack.api.app_backend.dialogs.response.Error;
import com.slack.api.app_backend.dialogs.response.Option;
import com.slack.api.lightning.App;
import com.slack.api.lightning.AppConfig;
import com.slack.api.methods.response.dialog.DialogOpenResponse;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public class DialogSample {

    static final List<Option> allOptions = Arrays.asList(
            new Option("Product", "prd"),
            new Option("Design", "des"),
            new Option("Engineering", "eng"),
            new Option("Sales", "sls"),
            new Option("Marketing", "mrk")
    );

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        app.command("/dialog", (req, ctx) -> {
            // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/dialogs/dialog.json
            String dialog = ResourceLoader.load("dialogs/dialog.json");
            DialogOpenResponse apiResponse = ctx.client().dialogOpen(r -> r
                    .triggerId(req.getPayload().getTriggerId())
                    .dialogAsString(dialog)
            );
            log.info("dialog.open - {}", apiResponse);
            if (apiResponse.isOk()) {
                return ctx.ack();
            } else {
                return ctx.ackWithJson(apiResponse);
            }
        });

        app.dialogSubmission("dialog-callback-id", (req, ctx) -> {
            if (req.getPayload().getSubmission().get("comment").length() < 10) {
                List<Error> errors = Arrays.asList(
                        new Error("comment", "must be longer than 10 characters"));
                return ctx.ack(r -> r.errors(errors));
            } else {
                ctx.respond(r -> r.text("Thanks!!"));
                return ctx.ack();
            }
        });

        app.dialogSuggestion("dialog-callback-id", (req, ctx) -> {
            String keyword = req.getPayload().getValue();
            List<Option> options = allOptions.stream()
                    .filter(o -> o.getLabel().contains(keyword))
                    .collect(toList());
            return ctx.ack(r -> r.options(options));
        });

        app.dialogCancellation("dialog-callback-id", (req, ctx) -> {
            ctx.respond(r -> r.text("Next time :smile:"));
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app, "/dialog");
        server.start();
    }

}
