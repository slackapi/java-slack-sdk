package com.github.seratch.jslack.app_backend.events.servlet;

import com.slack.api.app_backend.SlackSignature;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SlackSignatureVerifier {

    private final SlackSignature.Verifier verifier;

    public SlackSignatureVerifier() {
        this(new SlackSignature.Generator());
    }

    public SlackSignatureVerifier(SlackSignature.Generator signatureGenerator) {
        this.verifier = new SlackSignature.Verifier(signatureGenerator);
    }

    public boolean isValid(HttpServletRequest request, String requestBody) {
        return isValid(request, requestBody, System.currentTimeMillis());
    }

    public boolean isValid(HttpServletRequest request, String requestBody, long nowInMillis) {
        if (request != null && request.getHeaderNames() != null) {
            String requestTimestamp = request.getHeader(SlackSignature.HeaderNames.X_SLACK_REQUEST_TIMESTAMP);
            String requestSignature = request.getHeader(SlackSignature.HeaderNames.X_SLACK_SIGNATURE);
            return verifier.isValid(requestTimestamp, requestBody, requestSignature, nowInMillis);
        } else {
            return false;
        }
    }

}
