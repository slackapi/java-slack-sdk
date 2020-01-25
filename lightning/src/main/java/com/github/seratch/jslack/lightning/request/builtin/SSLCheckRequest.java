package com.github.seratch.jslack.lightning.request.builtin;

import com.slack.api.app_backend.ssl_check.payload.SSLCheckPayload;
import com.slack.api.app_backend.ssl_check.SSLCheckPayloadParser;
import com.github.seratch.jslack.lightning.context.builtin.DefaultContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class SSLCheckRequest extends Request<DefaultContext> {

    private static final SSLCheckPayloadParser PAYLOAD_PARSER = new SSLCheckPayloadParser();

    private final DefaultContext context = new DefaultContext();

    private final String requestBody;
    private final RequestHeaders headers;
    private final SSLCheckPayload payload;

    public SSLCheckRequest(
            String requestBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = PAYLOAD_PARSER.parse(requestBody);
    }

    @Override
    public DefaultContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.SSLCheck;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public SSLCheckPayload getPayload() {
        return payload;
    }
}
