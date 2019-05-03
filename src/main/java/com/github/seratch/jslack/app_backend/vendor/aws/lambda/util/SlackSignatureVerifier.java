package com.github.seratch.jslack.app_backend.vendor.aws.lambda.util;

import com.github.seratch.jslack.app_backend.SlackSignature;
import com.github.seratch.jslack.app_backend.vendor.aws.lambda.request.ApiGatewayRequest;

public class SlackSignatureVerifier {

    private final SlackSignature.Generator signatureGenerator;

    public SlackSignatureVerifier() {
        this(new SlackSignature.Generator());
    }

    public SlackSignatureVerifier(SlackSignature.Generator signatureGenerator) {
        this.signatureGenerator = signatureGenerator;
    }

    public boolean isValid(ApiGatewayRequest request) {
        if (request != null && request.getHeaders() != null) {
            String requestTimestamp = request.getHeaders().get(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);
            String requestBody = request.getBody();
            String expected = signatureGenerator.generate(requestTimestamp, requestBody);
            String actual = request.getHeaders().get(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
            return actual != null && expected != null && actual.equals(expected);
        } else {
            return false;
        }
    }

}
