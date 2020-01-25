package test_with_remote_apis.interactive_messages;

import com.github.seratch.jslack.Slack;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.ExternalSelectElement;
import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.app_backend.events.servlet.SlackSignatureVerifier;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.PayloadTypeDetector;
import com.github.seratch.jslack.app_backend.interactive_messages.response.ActionResponse;
import com.github.seratch.jslack.app_backend.interactive_messages.response.BlockSuggestionResponse;
import com.github.seratch.jslack.app_backend.interactive_messages.response.Option;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayloadParser;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.PayloadExtractor;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BlockKitBackend {

    @Slf4j
    @WebServlet
    public static class SlackEventsServlet extends HttpServlet {

        // Configure these two env variables to run this servlet
        private final String slackSigningSecret = System.getenv("SLACK_TEST_SIGNING_SECRET");

        private final SlackSignature.Generator signatureGenerator = new SlackSignature.Generator(slackSigningSecret);
        private final SlackSignatureVerifier signatureVerifier = new SlackSignatureVerifier(signatureGenerator);

        private final SlashCommandPayloadParser commandPayloadParser = new SlashCommandPayloadParser();

        private final Gson gson = GsonFactory.createSnakeCase();
        private final PayloadExtractor payloadExtractor = new PayloadExtractor();
        private final PayloadTypeDetector payloadTypeDetector = new PayloadTypeDetector();

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

            if (requestBody.startsWith("payload=")) {
                String json = payloadExtractor.extractPayloadJsonAsString(requestBody);
                String payloadType = payloadTypeDetector.detectType(json);

                if (payloadType.equals("block_actions")) {
                    // extracts trigger_id for views.open
                    BlockActionPayload payload = gson.fromJson(json, BlockActionPayload.class);
                    log.info("block_actions - {}", payload);
                } else if (payloadType.equals("block_suggestion")) {
                    log.info("block_suggestion - {}", json);

                    httpResp.setStatus(200);
                    httpResp.setHeader("Content-Type", "application/json");

                    BlockSuggestionResponse response = BlockSuggestionResponse.builder()
                            .options(Arrays.asList(
                                    Option.builder().text(PlainTextObject.builder().text("label1").build()).value("v1").build(),
                                    Option.builder().text(MarkdownTextObject.builder().text("label2").build()).value("v2").build(),
                                    Option.builder().text(PlainTextObject.builder().text("label3").build()).value("v3").build()
                            ))
                            .build();
                    String responseBody = gson.toJson(response);
                    log.info("response - {}", responseBody);
                    httpResp.getWriter().write(responseBody);

                    return;

                } else {
                    log.info("Unexpected payload - {}", payloadType);
                }
            } else {
                SlashCommandPayload payload = commandPayloadParser.parse(requestBody);
                log.info("command - {}", payload);
//                Payload webhookPayload = Payload.builder()
//                        .blocks(Arrays.asList(
//                                SectionBlock.builder()
//                                        .text(PlainTextObject.builder().text("Hi").build())
//                                        .accessory(ExternalSelectElement.builder().actionId("external_select_id").build())
//                                        .build()))
//                        .build();
//                WebhookResponse response = slack.send(payload.getResponseUrl(), webhookPayload);
//                log.info("response - {}", response);
            }

            // ack
            httpResp.setStatus(200);
            httpResp.setHeader("Content-Type", "application/json");

            ActionResponse response = new ActionResponse();
            response.setResponseType("ephemeral");
            response.setBlocks(Arrays.asList(SectionBlock.builder()
                    .text(PlainTextObject.builder().text("Hi").build())
                    .accessory(ExternalSelectElement.builder().actionId("external_select_id").build()).build()));
            String responseBody = gson.toJson(response);
            httpResp.getWriter().write(responseBody);
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

