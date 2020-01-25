package com.github.seratch.jslack.lightning.request.builtin;

import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.github.seratch.jslack.lightning.context.builtin.OAuthCallbackContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class OAuthCallbackRequest extends Request<OAuthCallbackContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final VerificationCodePayload payload;

    public OAuthCallbackRequest(
            String requestBody,
            VerificationCodePayload payload,
            RequestHeaders headers) {
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

}
