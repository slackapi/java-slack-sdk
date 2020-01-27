package com.slack.api.app_backend.vendor.aws.lambda.util;

import com.slack.api.app_backend.SlackSignature;
import com.slack.api.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SlackSignatureVerifier {

    private final SlackSignature.Verifier verifier;

    public SlackSignatureVerifier() {
        this(new SlackSignature.Generator());
    }

    public SlackSignatureVerifier(SlackSignature.Generator signatureGenerator) {
        this.verifier = new SlackSignature.Verifier(signatureGenerator);
    }

    public boolean isValid(ApiGatewayRequest request) {
        return isValid(request, System.currentTimeMillis());
    }

    public boolean isValid(ApiGatewayRequest request, long nowInMillis) {
        if (request != null && request.getHeaders() != null) {
            String requestTimestamp = request.getHeaders().get(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);
            String requestBody = request.getBody();
            String requestSignature = request.getHeaders().get(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
            return verifier.isValid(requestTimestamp, requestBody, requestSignature, nowInMillis);
        } else {
            return false;
        }
    }

}
