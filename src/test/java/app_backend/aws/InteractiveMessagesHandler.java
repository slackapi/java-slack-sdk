package app_backend.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.webhook.WebhookResponse;
import com.github.seratch.jslack.app_backend.interactive_messages.ResponseSender;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.AttachmentActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.PayloadTypeDetector;
import com.github.seratch.jslack.app_backend.interactive_messages.response.ActionResponse;
import com.github.seratch.jslack.app_backend.util.RequestTokenVerifier;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.PayloadExtractor;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.response.ApiGatewayResponse;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class InteractiveMessagesHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    // To make sure if the verification token is correct
    // When you go with the no arg constructor, the existence of env variable "SLACK_VERIFICATION_TOKEN" would be expected.
    private final RequestTokenVerifier tokenVerifier = new RequestTokenVerifier();

    // To extract and url decode "payload" parameter from the "body" property
    private final PayloadExtractor payloadExtractor = new PayloadExtractor();

    // To extract the "type" property from the payload
    private final PayloadTypeDetector payloadTypeDetector = new PayloadTypeDetector();

    // To deserialize the payload JSON string to an object
    private final Gson gson = GsonFactory.createSnakeCase();

    // To send a webhook request to Slack Platform
    private final ResponseSender responseSender = new ResponseSender(Slack.getInstance());

    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
        String json = payloadExtractor.extractPayloadJsonAsString(request);
        String payloadType = payloadTypeDetector.detectType(json);
        if (AttachmentActionPayload.TYPE.equals(payloadType)) {
            AttachmentActionPayload payload = gson.fromJson(json, AttachmentActionPayload.class);
            if (!tokenVerifier.isValid(payload)) {
                return ApiGatewayResponse.builder().setStatusCode(403).build();
            }
            // TODO: handle requests from some parts in attachments
            return ApiGatewayResponse.builder().setStatusCode(200).build();

        } else if (BlockActionPayload.TYPE.equals(payloadType)) {
            BlockActionPayload payload = gson.fromJson(json, BlockActionPayload.class);
            if (!tokenVerifier.isValid(payload)) {
                return ApiGatewayResponse.builder().setStatusCode(403).build();
            }
            for (BlockActionPayload.Action action : payload.getActions()) {
                // TODO: handle requests from some parts in attachments
                // If you have some heavy operations which may take a bit long, doing it asynchronously would be preferable
                ActionResponse responseMessage = ActionResponse.builder()
                        .responseType("ephemeral")
                        .text(action.getValue() + " has been accepted. Thanks!") // or attachments / blocks
                        .deleteOriginal(false)
                        .replaceOriginal(false)
                        .build();
                try {
                    WebhookResponse apiResponse = responseSender.send(payload.getResponseUrl(), responseMessage);
                    if (apiResponse.getCode() != 200) {
                        log.error("Got an error from Slack Platform (response: {})", apiResponse.getBody());
                        return ApiGatewayResponse.builder().setStatusCode(500).build();
                    }
                } catch (IOException e) {
                    log.error("Failed to send a response message to Slack Platform because {}", e.getMessage(), e);
                    return ApiGatewayResponse.builder().setStatusCode(500).build();
                }
            }

        } else {
            // "type" value is missing or an unexpected one
            return ApiGatewayResponse.builder().setStatusCode(400).build();

        }
        return ApiGatewayResponse.builder().setStatusCode(500).setRawBody("This pattern is not properly handled by the backend.").build();
    }

}
