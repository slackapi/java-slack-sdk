package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.model.view.View;
import lombok.extern.slf4j.Slf4j;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.util.Arrays;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.timePicker;
import static com.slack.api.model.view.Views.*;

@Slf4j
public class TimepickerSample {

    public static void main(String[] args) throws Exception {
        AppConfig appConfig = ResourceLoader.loadAppConfig();
        App app = new App(appConfig);
        app.command("/open-modal", (req, ctx) -> {
            View view = view(v -> v
                    .type("modal")
                    .callbackId("view-id")
                    .title(viewTitle(vt -> vt.type("plain_text").text("Org App")))
                    .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                    .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                    .blocks(asBlocks(
                            actions(a -> a.blockId("b1").elements(Arrays.asList(timePicker(t -> t.actionId("time1").initialTime("01:02"))))),
                            input(i -> i.blockId("b2").label(plainText("Time")).element(timePicker(t -> t.actionId("time2").initialTime("02:03"))))
                    )));
            ctx.client().viewsOpen(r -> r.triggerId(req.getPayload().getTriggerId()).view(view));
            return ctx.ack();
        });
        app.blockAction("time1", (req, ctx) -> {
            ctx.logger.info("action: {}", req.getPayload().getActions().get(0));
           return ctx.ack();
        });
        app.viewSubmission("view-id", (req, ctx) -> {
            ctx.logger.info("values: {}", req.getPayload().getView().getState().getValues());
            return ctx.ack();
        });
        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }

}
