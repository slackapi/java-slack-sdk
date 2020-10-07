package samples;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.util.json.GsonFactory;
import lombok.extern.slf4j.Slf4j;
import util.ResourceLoader;
import util.TestSlackAppServer;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.regex.Pattern;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.dispatchActionConfig;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.view.Views.*;

@Slf4j
public class ViewDispatchActionSample {

    public static void main(String[] args) throws Exception {
        SlackConfig slackConfig = new SlackConfig();
        slackConfig.setPrettyResponseLoggingEnabled(true);
        Slack slack = Slack.getInstance(slackConfig);
        AppConfig appConfig = ResourceLoader.loadAppConfig();
        appConfig.setSlack(slack);
        App app = new App(appConfig);

        app.command("/open-modal", (req, ctx) -> {
            ctx.client().viewsOpen(r -> r
                    .triggerId(req.getPayload().getTriggerId())
                    .view(view(v -> v
                            .type("modal")
                            .callbackId("test-view")
                            .title(viewTitle(vt -> vt.type("plain_text").text("Modal by Global Shortcut")))
                            .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                            .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                            .blocks(asBlocks(
                                    input(i -> i
                                            .blockId("b-1")
                                            .dispatchAction(true)
                                            .element(plainTextInput(e -> e
                                                    .actionId("a-1")
                                                    .dispatchActionConfig(dispatchActionConfig(d -> d
                                                            .triggerActionsOn(Arrays.asList("on_character_entered"))))
                                            ))
                                            .label(plainText("Label"))
                                    ),
                                    input(i -> i
                                            .blockId("b-2")
                                            .dispatchAction(true)
                                            .element(plainTextInput(e -> e
                                                    .actionId("a-2")
                                                    .dispatchActionConfig(dispatchActionConfig(d -> d
                                                            .triggerActionsOn(Arrays.asList("on_enter_pressed"))))
                                            ))
                                            .label(plainText("Label"))
                                    ),
                                    input(i -> i
                                            .blockId("b-3")
                                            .dispatchAction(false)
                                            .element(plainTextInput(e -> e
                                                    .actionId("a-3")
                                                    .dispatchActionConfig(dispatchActionConfig(d -> d
                                                            .triggerActionsOn(Arrays.asList("on_enter_pressed"))))
                                            ))
                                            .label(plainText("Label"))
                                    )
                            )))
                    ));
            return ctx.ack();
        });

        app.blockAction(Pattern.compile("a-\\d+"), (req, ctx) -> ctx.ack());

        app.viewSubmission("test-view", (req, ctx) -> ctx.ack());

        app.use((req, resp, chain) -> {
            Gson gson = GsonFactory.createSnakeCase(slackConfig);
            String body = req.getRequestBodyAsString();
            String payload = body.startsWith("payload=") ? URLDecoder.decode(body.split("payload=")[1], "UTF-8") : body;
            try {
                payload = gson.toJson(gson.fromJson(payload, JsonElement.class));
            } catch (JsonSyntaxException ignore) {
            }
            req.getContext().logger.info("Payload:\n{}", payload);
            return chain.next(req);
        });

        TestSlackAppServer server = new TestSlackAppServer(app);
        server.start();
    }
}