package test_with_remote_apis.jakarta_socket_mode;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.Slack;
import com.slack.api.jakarta_socket_mode.JakartaSocketModeClientFactory;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.socket_mode.SocketModeClient;
import com.slack.api.socket_mode.response.AckResponse;
import com.slack.api.socket_mode.response.MapResponse;
import com.slack.api.socket_mode.response.MessagePayload;
import com.slack.api.socket_mode.response.MessageResponse;
import config.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;

@Slf4j
public class SimpleSocketModeApp {

    public static void main(String[] args) throws Exception {
        String botToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_BOT_TOKEN);
        Slack slack = Slack.getInstance();
        MethodsClient apiClient = slack.methods(botToken);
        ChatPostMessageResponse chatPostMessageResponse = apiClient.chatPostMessage(r -> r
                // Invite this app to #random beforehand
                .channel("#random")
                .blocks(asBlocks(
                        actions(a -> a.blockId("b").elements(asElements(
                                button(b -> b.actionId("a").text(plainText("Click Me!")).value("underlying"))
                        )))
                )));
        log.info("chat.postMessage: {}", chatPostMessageResponse);

        String appToken = System.getenv(Constants.SLACK_SDK_TEST_SOCKET_MODE_APP_TOKEN);
        SocketModeClient socketModeClient = JakartaSocketModeClientFactory.create(slack, appToken);
        socketModeClient.addEventsApiEnvelopeListener(req -> {
            socketModeClient.sendSocketModeResponse(new AckResponse(req.getEnvelopeId()));
        });
        socketModeClient.addSlashCommandsEnvelopeListener(req -> {
            socketModeClient.sendSocketModeResponse(MessageResponse.builder()
                    .envelopeId(req.getEnvelopeId())
                    .payload(MessagePayload.builder().text("Hi!").build())
                    .build()
            );

            try {
                ViewsOpenResponse newModal = apiClient.viewsOpen(r -> r
                        .triggerId(req.getPayload().getAsJsonObject().get("trigger_id").getAsString())
                        .view(view(v -> v
                                .type("modal")
                                .callbackId("modal-id")
                                .title(viewTitle(vt -> vt.type("plain_text").text("My App")))
                                .close(viewClose(vc -> vc.type("plain_text").text("Close")))
                                .submit(viewSubmit(vs -> vs.type("plain_text").text("Submit")))
                                .blocks(asBlocks(input(i -> i
                                        .blockId("agenda-block")
                                        .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
                                        .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                                )))
                        ))
                );
            } catch (Exception ex) {
                log.error("Failed to open a modal: {}", ex.getMessage());
            }
        });
        socketModeClient.addInteractiveEnvelopeListener(req -> {
            JsonObject requestPayload = req.getPayload().getAsJsonObject();
            if (requestPayload.get("view") != null) {
                JsonObject view = requestPayload.get("view").getAsJsonObject();
                JsonElement callbackId = view.get("callback_id");
                if (callbackId != null && callbackId.getAsString().equals("modal-id")) {
                    Map<String, Object> responsePayload = new HashMap<>();
                    responsePayload.put("response_action", "errors");
                    Map<String, Object> errors = new HashMap<>();
                    errors.put("agenda-block", "Something is wrong!");
                    responsePayload.put("errors", errors);
                    socketModeClient.sendSocketModeResponse(new MapResponse(req.getEnvelopeId(), responsePayload));
                    return;
                }
            }
            socketModeClient.sendSocketModeResponse(new AckResponse(req.getEnvelopeId()));
        });
        socketModeClient.connect();
        Thread.sleep(Long.MAX_VALUE);
    }
}
