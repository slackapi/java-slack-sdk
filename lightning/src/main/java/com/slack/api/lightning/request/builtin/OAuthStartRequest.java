package com.slack.api.lightning.request.builtin;

import com.slack.api.lightning.context.builtin.OAuthCallbackContext;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class OAuthStartRequest extends Request<OAuthCallbackContext> {

    private final String requestBody;
    private final RequestHeaders headers;

    public OAuthStartRequest(
            String requestBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
    }

    private OAuthCallbackContext context = new OAuthCallbackContext();

    @Override
    public OAuthCallbackContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.OAuthStart;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

}
