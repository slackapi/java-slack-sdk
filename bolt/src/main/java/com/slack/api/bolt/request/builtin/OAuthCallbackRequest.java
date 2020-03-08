package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.bolt.context.builtin.OAuthCallbackContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString(callSuper = true)
public class OAuthCallbackRequest extends Request<OAuthCallbackContext> {

    private final Map<String, List<String>> queryString;
    private final String requestBody;
    private final RequestHeaders headers;
    private final VerificationCodePayload payload;

    public OAuthCallbackRequest(
            Map<String, List<String>> queryString,
            String requestBody,
            VerificationCodePayload payload,
            RequestHeaders headers) {
        this.queryString = queryString;
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = payload;
    }

    private OAuthCallbackContext context = new OAuthCallbackContext();

    @Override
    public OAuthCallbackContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.OAuthCallback;
    }

    @Override
    public Map<String, List<String>> getQueryString() {
        return this.queryString;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public VerificationCodePayload getPayload() {
        return payload;
    }

    @Override
    public String getResponseUrl() {
        return null;
    }
}
