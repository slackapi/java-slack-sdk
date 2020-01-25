package com.slack.api.lightning.request.builtin;

import com.slack.api.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.slack.api.lightning.context.builtin.DialogSuggestionContext;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.request.RequestType;
import com.slack.api.util.json.GsonFactory;
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
        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
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
