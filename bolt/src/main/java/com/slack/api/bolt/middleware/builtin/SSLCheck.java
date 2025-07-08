package com.slack.api.bolt.middleware.builtin;

import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.bolt.request.builtin.SSLCheckRequest;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * Responds to ssl_check requests from Slack for Slash commands.
 */
@Slf4j
public class SSLCheck implements Middleware {

    private final String expectedVerificationToken;

    public SSLCheck(String verificationToken) {
        this.expectedVerificationToken = verificationToken;
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (req.getRequestType() == RequestType.SSLCheck) {
            // https://docs.slack.dev/interactivity/implementing-slash-commands
            // If public distribution is active for your app,
            // Slack will occasionally send your command's request URL a simple POST request
            // to verify the server's SSL certificate.
            if (expectedVerificationToken != null) {
                // These requests will include a parameter ssl_check set to 1 and a token parameter.
                // The token value corresponds to the verification token registered with your app's slash command.
                // See the token field above for more information on validating verification tokens.
                // Mostly, you may ignore these requests, but please do confirm receipt as below.
                SSLCheckRequest request = (SSLCheckRequest) req;

                String sslCheck = request.getPayload().getSslCheck();
                String actualVerificationToken = request.getPayload().getToken();
                if (!sslCheck.equals("1")
                        || actualVerificationToken == null
                        || !actualVerificationToken.equals(expectedVerificationToken)) {
                    log.info("Detected an invalid ssl_check request - payload: {}, headers: {}", request.getPayload(), request.getHeaders());
                    return Response.error(401);
                }
                if (log.isDebugEnabled()) {
                    log.debug("Received a valid ssl_check request - payload: {}, headers: {}", request.getPayload(), request.getHeaders());
                }
            }
            return Response.ok();
        } else {
            return chain.next(req);
        }
    }

}
