package com.slack.api.bolt.middleware.builtin;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import lombok.extern.slf4j.Slf4j;

import static com.slack.api.bolt.middleware.MiddlewareOps.isNoSlackSignatureRequest;

/**
 * Verifies if the x-slack-signature, x-slack-request-timestamp are valid.
 */
@Slf4j
public class RequestVerification implements Middleware {

    private final SlackSignature.Verifier verifier;

    public RequestVerification(SlackSignature.Verifier verifier) {
        this.verifier = verifier;
    }

    @Override
    public Response apply(Request req, Response resp, MiddlewareChain chain) throws Exception {
        if (isNoSlackSignatureRequest(req.getRequestType())) {
            return chain.next(req);
        }
        if (req.isValid(verifier)) {
            return chain.next(req);
        } else {
            String signature = req.getHeaders().getFirstValue(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
            log.info("Invalid signature detected - {}", signature);
            return Response.json(401, "{\"error\":\"invalid request\"}");
        }
    }
}
