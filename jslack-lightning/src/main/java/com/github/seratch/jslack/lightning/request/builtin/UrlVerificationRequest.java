package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.lightning.context.builtin.DefaultContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class UrlVerificationRequest extends Request<DefaultContext> {

    private final RequestHeaders headers;
    private final String challenge;

    public UrlVerificationRequest(
            String challenge,
            RequestHeaders headers) {
        this.challenge = challenge;
        this.headers = headers;
    }

    private DefaultContext context = new DefaultContext();

    @Override
    public DefaultContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.UrlVerification;
    }

    @Override
    public String getRequestBodyAsString() {
        return challenge;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

}
