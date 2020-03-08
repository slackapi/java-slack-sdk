package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.bolt.context.builtin.BlockSuggestionContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

@ToString(callSuper = true)
public class BlockSuggestionRequest extends Request<BlockSuggestionContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final BlockSuggestionPayload payload;

    public BlockSuggestionRequest(
            String requestBody,
            String payloadBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = GsonFactory.createSnakeCase().fromJson(payloadBody, BlockSuggestionPayload.class);
        getContext().setEnterpriseId(payload.getTeam().getEnterpriseId());
        getContext().setTeamId(payload.getTeam().getId());
        getContext().setRequestUserId(payload.getUser().getId());
    }

    private BlockSuggestionContext context = new BlockSuggestionContext();

    @Override
    public BlockSuggestionContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.BlockSuggestion;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public BlockSuggestionPayload getPayload() {
        return payload;
    }

    @Override
    public String getResponseUrl() {
        return null;
    }
}
