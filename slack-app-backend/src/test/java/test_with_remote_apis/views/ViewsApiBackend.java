package test_with_remote_apis.views;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.methods.response.views.ViewsUpdateResponse;
import com.slack.api.model.block.InputBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ButtonElement;
import com.slack.api.model.block.element.PlainTextInputElement;
import com.slack.api.model.view.View;
import com.slack.api.model.view.ViewSubmit;
import com.slack.api.model.view.ViewTitle;
import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.events.servlet.SlackSignatureVerifier;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.util.JsonPayloadTypeDetector;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import com.slack.api.app_backend.vendor.aws.lambda.request.PayloadExtractor;
import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.util.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ViewsApiBackend {

    @Slf4j
    @WebServlet
    public static class SlackEventsServlet extends HttpServlet {

        // Configure these two env variables to run this servlet
        private final String slackSigningSecret = System.getenv("SLACK_TEST_SIGNING_SECRET");
        private final String token = System.getenv("SLACK_BOT_USER_TEST_OAUTH_ACCESS_TOKEN");

        private final SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(slackSigningSecret);
        private final SlackSignatureVerifier signatureVerifier = new SlackSignatureVerifier(signatureGenerator);

        private final SlashCommandPayloadParser commandPayloadParser = new SlashCommandPayloadParser();

        private final Gson gson = GsonFactory.createSnakeCase();
        private final PayloadExtractor payloadExtractor = new PayloadExtractor();
        private final JsonPayloadTypeDetector payloadTypeDetector = new JsonPayloadTypeDetector();

        private final Slack slack = Slack.getInstance();

        protected String doReadRequestBodyAsString(HttpServletRequest httpReq) throws IOException {
            return httpReq.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }

        protected void doPost(HttpServletRequest httpReq, HttpServletResponse httpResp) throws IOException {
            // any command to invoke
            String requestBody = doReadRequestBodyAsString(httpReq);
            log.info("requestBody - {}", requestBody);

            boolean validSignature = this.signatureVerifier.isValid(httpReq, requestBody);
            if (!validSignature) { // invalid signature
                if (log.isDebugEnabled()) {
                    String signature = httpReq.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
                    log.debug("An invalid X-Slack-Signature detected - {}", signature);
                }
                httpResp.setStatus(401);
                return;
            }

            String triggerId;
            if (requestBody.startsWith("payload=")) {
                String json = payloadExtractor.extractPayloadJsonAsString(requestBody);
                String payloadType = payloadTypeDetector.detectType(json);

                if (payloadType.equals("view_submission")) {
                    // just receives view_submission requests
                    ViewSubmissionPayload payload = gson.fromJson(json, ViewSubmissionPayload.class);
                    log.info("view_submission - {}", payload);
                    httpResp.setStatus(200);
                    return;

                } else if (payloadType.equals("view_closed")) {
                    // just receives view_closed requests when notifyOnClose is true
                    ViewClosedPayload payload = gson.fromJson(json, ViewClosedPayload.class);
                    log.info("view_closed - {}", payload);
                    httpResp.setStatus(200);
                    return;

                } else if (payloadType.equals("block_actions")) {
                    // extracts trigger_id for views.open
                    BlockActionPayload payload = gson.fromJson(json, BlockActionPayload.class);
                    if (payload.getView() != null) {
                        try {
                            View newView = View.builder()
                                    .type("modal")
                                    .callbackId("callback_id2")
                                    .title(ViewTitle.builder().type("plain_text").text("Title2").build())
                                    .submit(ViewSubmit.builder().type("plain_text").text("Submit2").build())
                                    .blocks(Arrays.asList(InputBlock.builder()
                                            .blockId("text_input")
                                            .label(PlainTextObject.builder().text("text").build())
                                            .element(PlainTextInputElement.builder().actionId("single").multiline(true).build())
                                            .build()))
                                    .build();
                            ViewsUpdateResponse apiResponse = slack.methods(token).viewsUpdate(req -> req
                                    .viewId(payload.getView().getId())
                                    .view(newView));
                            log.info("views.update - {}", apiResponse);
                        } catch (SlackApiException e) {
                            log.error(e.getResponseBody(), e);
                        }
                        return;
                    } else {
                        triggerId = payload.getTriggerId();
                    }
                } else {
                    log.info("Unexpected payload - {}", payloadType);
                    return;
                }
            } else {
                // extracts trigger_id for views.open
                SlashCommandPayload payload = commandPayloadParser.parse(requestBody);
                triggerId = payload.getTriggerId();
            }

            List<LayoutBlock> blocks = new ArrayList<>();
            blocks.add(InputBlock.builder()
                    .blockId("text_input")
                    .label(PlainTextObject.builder().text("text").build())
                    .element(PlainTextInputElement.builder().actionId("single").multiline(true).build())
                    .build());
            blocks.add(SectionBlock.builder()
                    .text(PlainTextObject.builder().text("button").build())
                    .accessory(ButtonElement.builder().text(PlainTextObject.builder().text("submit").build()).actionId("click").value("1").build())
                    .build());

            View view = View.builder()
                    .type("modal")
                    .callbackId("callback_id")
                    .title(ViewTitle.builder().type("plain_text").text("Title").build())
                    .submit(ViewSubmit.builder().type("plain_text").text("Submit").build())
                    .notifyOnClose(true)
                    .blocks(blocks).build();
            log.info(triggerId);
            log.info("view: {}", view);
            try {
                ViewsOpenResponse apiResponse = slack.methods(token).viewsOpen(req -> req
                        .view(view)
                        .triggerId(triggerId));
                log.info("views.open - {}", apiResponse);

                if (!apiResponse.isOk()) {
                    httpResp.setStatus(500);
                    httpResp.getWriter().write(apiResponse.getError());
                    return;
                }
            } catch (SlackApiException e) {
                httpResp.setStatus(500);
                httpResp.getWriter().write(e.getMessage());
                return;
            }

            // ack
            httpResp.setStatus(200);
        }
    }

    // https://www.eclipse.org/jetty/documentation/current/embedding-jetty.html

    public static void main(String[] args) throws Exception {
        Server server = new Server(3000);
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(SlackEventsServlet.class, "/slack/events");
        server.start();
        server.join();
    }
}

