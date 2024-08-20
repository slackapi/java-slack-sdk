package samples;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.Message;
import com.slack.api.model.block.element.RichTextSectionElement;
import com.slack.api.model.event.*;
import com.slack.api.model.view.ViewState;
import config.Constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.dispatchActionConfig;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;

public class SimpleApp {

    public static void main(String[] args) throws Exception {
        App app = new App(AppConfig.builder()
                .singleTeamBotToken(System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN))
                .build());
        app.use((req, resp, chain) -> {
            req.getContext().logger.info(req.getRequestBodyAsString());
            return chain.next(req);
        });

        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Hi there!");
            return ctx.ack();
        });

        app.event(MessageEvent.class, (req, ctx) -> {
            ctx.asyncClient().reactionsAdd(r -> r
                    .channel(req.getEvent().getChannel())
                    .name("eyes")
                    .timestamp(req.getEvent().getTs())
            );
            return ctx.ack();
        });

        app.event(MessageChangedEvent.class, (req, ctx) -> ctx.ack());
        app.event(MessageDeletedEvent.class, (req, ctx) -> ctx.ack());

        app.command("/hello-socket-mode", (req, ctx) -> {
            Map<String, Object> eventPayload = new HashMap<>();
            eventPayload.put("something", "great");
            Message.Metadata metadata = Message.Metadata.builder()
                    .eventType("foo")
                    .eventPayload(eventPayload)
                    .build();
            ctx.respond(r -> r.responseType("in_channel").text("hi!").metadata(metadata));
            ctx.asyncClient().viewsOpen(r -> r
                    .triggerId(req.getContext().getTriggerId())
                    .view(view(v -> v
                            .type("modal")
                            .callbackId("test-view")
                            .title(viewTitle(vt -> vt.type("plain_text").text("Modal by Global Shortcut")))
                            .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                            .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                            .blocks(asBlocks(
                                    input(input -> input
                                            .blockId("agenda-block")
                                            .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
                                            .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                                    ),
                                    // Note that this block element requires files:read scope
                                    input(input -> input
                                            .blockId("files-block")
                                            .element(fileInput(fi -> fi.actionId("files-action")))
                                            .label(plainText(pt -> pt.text("Attached files").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("email-block")
                                            .element(emailTextInput(pti -> pti.actionId("email-action")))
                                            .label(plainText(pt -> pt.text("Email Address").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("url-block")
                                            .element(urlTextInput(pti -> pti.actionId("url-action")))
                                            .label(plainText(pt -> pt.text("URL").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("number-block")
                                            .element(numberInput(pti -> pti.actionId("number-action")))
                                            .label(plainText(pt -> pt.text("Budget").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("date-block")
                                            .element(datePicker(pti -> pti.actionId("date-action")))
                                            .label(plainText(pt -> pt.text("Date").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("time-block")
                                            .element(timePicker(pti -> pti.actionId("time-action").timezone("America/Los_Angeles")))
                                            .label(plainText(pt -> pt.text("Time").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("date-time-block")
                                            .element(datetimePicker(pti -> pti.actionId("date-time-action")))
                                            .label(plainText(pt -> pt.text("Date Time").emoji(true)))
                                    ),
                                    input(input -> input
                                            .blockId("rich-text-block")
                                            .element(richTextInput(pti -> pti.actionId("rich-text-action")
                                                    .initialValue(richText(rt -> rt.blockId("b").elements(Arrays.asList(
                                                            richTextList(rtl -> rtl.style("bullet").elements(Arrays.asList(
                                                                    richTextSection(rtl1 -> rtl1.elements(Arrays.asList(
                                                                            RichTextSectionElement.Text.builder()
                                                                                    .text("Hey!")
                                                                                    .style(RichTextSectionElement.TextStyle.builder().bold(true).build())
                                                                                    .build()
                                                                    ))),
                                                                    richTextSection(rtl2 -> rtl2.elements(Arrays.asList(
                                                                            RichTextSectionElement.Text.builder().text("What's up?").build()
                                                                    )))
                                                            )))
                                                    ))))
                                                    .dispatchActionConfig(dispatchActionConfig(dc -> dc.triggerActionsOn(Arrays.asList("on_character_entered"))))
                                            ))
                                            .dispatchAction(true)
                                            .label(plainText(pt -> pt.text("Rich Text").emoji(true)))
                                    )
                            ))
                    )));
            return ctx.ack();
        });

        app.blockAction("rich-text-action", (req, ctx) -> {
            ctx.logger.info("state values: {}", req.getPayload().getView().getState().getValues());
            ctx.logger.info("action[0]: {}", req.getPayload().getActions().get(0));
            return ctx.ack();
        });

        app.viewSubmission("test-view", (req, ctx) -> {
            ViewState.Value time = req.getPayload().getView().getState().getValues().get("time-block").get("time-action");
            assert time.getTimezone().equals("America/Los_Angeles");
            ViewState.Value richText = req.getPayload().getView().getState().getValues().get("rich-text-block").get("rich-text-action");
            assert richText.getRichTextValue().getElements().size() > 0;
            return ctx.ack();
        });

        app.messageShortcut("socket-mode-message-shortcut", (req, ctx) -> {
            ctx.respond("It works!");
            return ctx.ack();
        });

        // Note that this is still in beta as of Nov 2023
        // app.event(FunctionExecutedEvent.class, (req, ctx) -> {
        // app.function("hello", (req, ctx) -> {
        app.function(Pattern.compile("^he.+$"), (req, ctx) -> {
            ctx.logger.info("req: {}", req);
            ctx.client().chatPostMessage(r -> r
                    .channel(req.getEvent().getInputs().get("user_id").asString())
                    .text("hey!")
                    .blocks(asBlocks(actions(a -> a.blockId("b").elements(asElements(
                            button(b -> b.actionId("remote-function-button-success").value("clicked").text(plainText("block_actions success"))),
                            button(b -> b.actionId("remote-function-button-error").value("clicked").text(plainText("block_actions error"))),
                            button(b -> b.actionId("remote-function-modal").value("clicked").text(plainText("modal view")))
                    )))))
            );
            return ctx.ack();
        });

        app.blockAction("remote-function-button-success", (req, ctx) -> {
            Map<String, Object> outputs = new HashMap<>();
            outputs.put("user_id", req.getPayload().getFunctionData().getInputs().get("user_id").asString());
            ctx.client().functionsCompleteSuccess(r -> r
                    .functionExecutionId(req.getPayload().getFunctionData().getExecutionId())
                    .outputs(outputs)
            );
            ctx.client().chatUpdate(r -> r
                    .channel(req.getPayload().getContainer().getChannelId())
                    .ts(req.getPayload().getContainer().getMessageTs())
                    .text("Thank you!")
            );
            return ctx.ack();
        });
        app.blockAction("remote-function-button-error", (req, ctx) -> {
            ctx.client().functionsCompleteError(r -> r
                    .functionExecutionId(req.getPayload().getFunctionData().getExecutionId())
                    .error("test error!")
            );
            ctx.client().chatUpdate(r -> r
                    .channel(req.getPayload().getContainer().getChannelId())
                    .ts(req.getPayload().getContainer().getMessageTs())
                    .text("Thank you!")
            );
            return ctx.ack();
        });
        app.blockAction("remote-function-modal", (req, ctx) -> {
            ctx.client().viewsOpen(r -> r
                    .triggerId(req.getPayload().getInteractivity().getInteractivityPointer())
                    .view(view(v -> v
                            .type("modal")
                            .callbackId("remote-function-view")
                            .title(viewTitle(vt -> vt.type("plain_text").text("Remote Function test")))
                            .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                            .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                            .notifyOnClose(true)
                            .blocks(asBlocks(input(input -> input
                                    .blockId("text-block")
                                    .element(plainTextInput(pti -> pti.actionId("text-action").multiline(true)))
                                    .label(plainText(pt -> pt.text("Text").emoji(true)))
                            )))
                    )));
            ctx.client().chatUpdate(r -> r
                    .channel(req.getPayload().getContainer().getChannelId())
                    .ts(req.getPayload().getContainer().getMessageTs())
                    .text("Thank you!")
            );
            return ctx.ack();
        });

        app.viewSubmission("remote-function-view", (req, ctx) -> {
            Map<String, Object> outputs = new HashMap<>();
            outputs.put("user_id", ctx.getRequestUserId());
            ctx.client().functionsCompleteSuccess(r -> r
                    .token(req.getPayload().getBotAccessToken())
                    .functionExecutionId(req.getPayload().getFunctionData().getExecutionId())
                    .outputs(outputs)
            );
            return ctx.ack();
        });
        app.viewClosed("remote-function-view", (req, ctx) -> {
            Map<String, Object> outputs = new HashMap<>();
            outputs.put("user_id", ctx.getRequestUserId());
            ctx.client().functionsCompleteSuccess(r -> r
                    .token(req.getPayload().getBotAccessToken())
                    .functionExecutionId(req.getPayload().getFunctionData().getExecutionId())
                    .outputs(outputs)
            );
            return ctx.ack();
        });

        String appToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}
