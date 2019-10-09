package com.github.seratch.jslack.lightning.request.builtin;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.github.seratch.jslack.lightning.context.builtin.DialogSuggestionContext;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class DialogSuggestionRequest extends Request<DialogSuggestionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final DialogSuggestionPayload payload;

    public DialogSuggestionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, DialogSuggestionPayload.class);
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private DialogSuggestionContext context = new DialogSuggestionContext();

    @Override
    public DialogSuggestionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.DialogSuggestion;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public DialogSuggestionPayload getPayload() {
        return payload;
    }

}
