package app_backend.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayload;
import com.github.seratch.jslack.app_backend.slash_commands.payload.SlashCommandPayloadParser;
import com.github.seratch.jslack.app_backend.slash_commands.response.SlashCommandResponse;
import com.github.seratch.jslack.app_backend.util.RequestTokenVerifier;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.response.ApiGatewayResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlashCommandHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    private final SlashCommandPayloadParser payloadParser = new SlashCommandPayloadParser();

    // To make sure if the verification token is correct
    // When you go with the no arg constructor, the existence of env variable "SLACK_VERIFICATION_TOKEN" would be expected.
    private final RequestTokenVerifier tokenVerifier = new RequestTokenVerifier();

    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
        SlashCommandPayload payload = payloadParser.parse(request.getBody());
        if (!tokenVerifier.isValid(payload)) {
            return ApiGatewayResponse.builder().setStatusCode(403).build();
        }
        // NOTE: https://api.slack.com/slash-commands#responding_basic_receipt
        // This confirmation must be received by Slack within 3000 milliseconds of the original request being sent,
        // otherwise a `Timeout was reached` will be displayed to the user.
        // If you couldn't verify the request payload, your app should return an error instead and ignore the request.
        SlashCommandResponse responseMessage = SlashCommandResponse.builder()
                .responseType("ephemeral")
                .text("Accepted!") // or attachments / blocks
                .build();

        // If you have some heavy operations which may take a bit long, doing it asynchronously would be preferable

        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(responseMessage)
                .build();
    }

}
