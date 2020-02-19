package com.slack.api.bolt.request.builtin;

import com.slack.api.app_backend.slash_commands.SlashCommandPayloadParser;
import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import lombok.ToString;

@ToString(callSuper = true)
public class SlashCommandRequest extends Request<SlashCommandContext> {

    private static final SlashCommandPayloadParser PAYLOAD_PARSER = new SlashCommandPayloadParser();

    private final SlashCommandContext context = new SlashCommandContext();

    private final String requestBody;
    private final RequestHeaders headers;
    private final SlashCommandPayload payload;

    public SlashCommandRequest(
            String requestBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        this.payload = PAYLOAD_PARSER.parse(requestBody);
        getContext().setResponseUrl(payload.getResponseUrl());
        getContext().setTriggerId(payload.getTriggerId());
        getContext().setChannelId(payload.getChannelId());
        getContext().setEnterpriseId(payload.getEnterpriseId());
        getContext().setTeamId(payload.getTeamId());
        getContext().setRequestUserId(payload.getUserId());
    }

    @Override
    public SlashCommandContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.Command;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public SlashCommandPayload getPayload() {
        return payload;
    }
}
