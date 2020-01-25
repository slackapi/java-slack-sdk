package samples;

import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewState;
import com.slack.api.app_backend.interactive_components.response.Option;
import com.slack.api.util.json.GsonFactory;
import com.slack.api.lightning.App;
import com.slack.api.lightning.AppConfig;
import com.slack.api.lightning.util.JsonOps;
import lombok.extern.slf4j.Slf4j;
import samples.util.ResourceLoader;
import samples.util.TestSlackAppServer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@Slf4j
public class ViewSample {

    static final List<Option> allOptions = Arrays.asList(
            new Option(PlainTextObject.builder().text("Schedule").build(), "schedule"),
            new Option(PlainTextObject.builder().text("Budget").build(), "budget"),
            new Option(PlainTextObject.builder().text("Assignment").build(), "assignment")
    );

    public static void main(String[] args) throws Exception {
        AppConfig config = ResourceLoader.loadAppConfig();
        App app = new App(config);

        // https://github.com/seratch/jslack/blob/master/jslack-lightning/src/test/resources/
        String view1 = ResourceLoader.load("views/view1.json");
        app.command("/view", (req, ctx) -> {
            ViewsOpenResponse apiResponse = ctx.client().viewsOpen(r -> r
                    .triggerId(ctx.getTriggerId())
                    .viewAsString(view1)
            );
            if (apiResponse.isOk()) {
                return ctx.ack();
            } else {
                return ctx.ackWithJson(apiResponse);
            }
        });

        // block_id: (any) action_id: topics-input
        app.blockSuggestion("topics-input", (req, ctx) -> {
            String keyword = req.getPayload().getValue();
            List<Option> options = allOptions.stream()
                    .filter(o -> ((PlainTextObject) o.getText()).getText().contains(keyword))
                    .collect(toList());
            if (options.size() == 0) {
                return ctx.ack(r -> r.options(allOptions));
            } else {
                return ctx.ack(r -> r.options(options));
            }
        });

        // block_id: (any) action_id: topics-input
        app.blockAction("topics-input", (req, ctx) -> {
            return ctx.ack();
        });
        // block_id: (any) action_id: rating-input
        app.blockAction("rating-input", (req, ctx) -> {
            return ctx.ack();
        });

        String view2 = ResourceLoader.load("views/view2.json");

        // callback_id: view-callback-id
        app.viewSubmission("view-callback-id", (req, ctx) -> {
            log.info("state - {}", req.getPayload().getView().getState());
            ViewState state = req.getPayload().getView().getState();
            String agenda = state.getValues().get("agenda").get("agenda-input").getValue();
            Map<String, String> errors = new HashMap<>();
            if (agenda.length() < 10) {
                errors.put("agenda", "The agenda needs to be longer than 10 characters.");
            }
            if (errors.size() > 0) {
                return ctx.ack(r -> r.responseAction("errors").errors(errors));
            } else {
                View view = GsonFactory.createSnakeCase().fromJson(view2, View.class);
                view.setPrivateMetadata(JsonOps.toJsonString(req.getPayload().getView().getState()));
                return ctx.ack(r -> r.responseAction("update").view(view));
            }
        });

        // callback_id: app-satisfaction-survey (view2)
        app.viewSubmission("app-satisfaction-survey", (req, ctx) -> {
            View view = req.getPayload().getView();
            log.info("state - {}, private_metadata - {}", view.getState(), view.getPrivateMetadata());
            return ctx.ack(); // just close the view
        });

        // callback_id: view-callback-id
        app.viewClosed("view-callback-id", (req, ctx) -> {
            return ctx.ack();
        });
        // callback_id: app-satisfaction-survey (view2)
        app.viewClosed("app-satisfaction-survey", (req, ctx) -> {
            return ctx.ack();
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
