package app_backend.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.response.oauth.OAuthAccessResponse;
import com.github.seratch.jslack.app_backend.config.SlackAppConfig;
import com.github.seratch.jslack.app_backend.oauth.OAuthFlowOperator;
import com.github.seratch.jslack.app_backend.oauth.payload.VerificationCodePayload;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.response.ApiGatewayResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * https://api.slack.com/docs/oauth
 */
@Slf4j
public class OAuthFlowHandler implements RequestHandler<ApiGatewayRequest, ApiGatewayResponse> {

    private static final String ERROR_URL = "https://www.example.com/error";
    private static final String CANCELLATION_URL = "https://www.example.com/cancellation";
    private static final String COMPLETION_URL = "https://www.example.com/thank-you";

    private final Slack slack = Slack.getInstance();

    private static SlackAppConfig buildConfig() {
        return SlackAppConfig.builder()
                .clientId(System.getenv("SLACK_APP_CLIENT_ID"))
                .clientSecret(System.getenv("SLACK_APP_CLIENT_SECRET"))
                .redirectUri(System.getenv("SLACK_APP_REDIRECT_URI"))
                .build();
    }

    private final SlackAppConfig config = buildConfig();

    private final OAuthFlowOperator oAuthFlowOperator = new OAuthFlowOperator(slack, config);

    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest request, Context context) {
        VerificationCodePayload payload = VerificationCodePayload.from(request.getQueryStringParameters());

        if (!isValidStateParameter(payload.getState())) {
            return ApiGatewayResponse.build302Response(ERROR_URL);
        }

        if (payload.getError() != null) {
            // The Slack app installation has been cancelled
            return ApiGatewayResponse.build302Response(CANCELLATION_URL);

        } else {
            // Call the oauth.access endpoint to fetch OAuth access token for this integration
            try {
                OAuthAccessResponse response = oAuthFlowOperator.callOAuthAccessMethod(payload);
                if (response.isOk()) {
                    // TODO: store the access token
                    return ApiGatewayResponse.build302Response(COMPLETION_URL);

                } else {
                    log.error("Failed to call oauth.access API - error: {}", response.getError());
                    return ApiGatewayResponse.build302Response(CANCELLATION_URL);
                }
            } catch (IOException | SlackApiException e) {
                log.error("Failed to handle an OAuth request error: {}, {}", e.getClass().getCanonicalName(), e.getMessage());
                return ApiGatewayResponse.build302Response(ERROR_URL);
            }
        }
    }

    /**
     * If the user authorizes your app, Slack will redirect back to your specified redirect_uri with a temporary code in a code GET parameter,
     * as well as a state parameter if you provided one in the previous step.
     * If the states don't match, the request may have been created by a third party and you should abort the process.
     *
     * @param state the given state parameter value
     * @return true if the state value is valid
     * @see "https://api.slack.com/docs/oauth"
     */
    private boolean isValidStateParameter(String state) {
        return true; // TODO: implement this
    }

}
