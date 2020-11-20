package com.slack.api.bolt.socket_mode.request;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.util.SlackRequestParser;
import com.slack.api.socket_mode.request.EventsApiEnvelope;
import com.slack.api.socket_mode.request.InteractiveEnvelope;
import com.slack.api.socket_mode.request.SlashCommandsEnvelope;
import com.slack.api.socket_mode.request.SocketModeEnvelope;
import com.slack.api.util.json.GsonFactory;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SocketModeRequestParser {
    private static final Gson GSON = GsonFactory.createSnakeCase();
    private final SlackRequestParser slackRequestParser;

    public SocketModeRequestParser(AppConfig appConfig) {
        this.slackRequestParser = new SlackRequestParser(appConfig);
    }

    @Data
    public static class GenericSocketModeEnvelope implements SocketModeEnvelope {
        private String type;
        private String envelopeId;
        private Boolean acceptsResponsePayload;
        private JsonElement payload;
        private Integer retryAttempt;
        private String retryReason;
    }

    private static final List<String> ENVELOPE_TYPES = Arrays.asList(
            EventsApiEnvelope.TYPE,
            InteractiveEnvelope.TYPE,
            SlashCommandsEnvelope.TYPE
    );

    public SocketModeRequest parse(String message) {
        GenericSocketModeEnvelope envelope = GSON.fromJson(message, GenericSocketModeEnvelope.class);
        if (ENVELOPE_TYPES.contains(envelope.getType())) {
            return SocketModeRequest.builder()
                    .envelope(envelope)
                    .boltRequest(slackRequestParser.parse(SlackRequestParser.HttpRequest.builder()
                            .socketMode(true)
                            .requestUri("")
                            .remoteAddress("")
                            .queryString(Collections.emptyMap())
                            .requestBody(GSON.toJson(envelope.getPayload()))
                            .headers(new RequestHeaders(Collections.emptyMap())) // TODO: retries in Events API
                            .build()))
                    .build();
        }
        return null;
    }

}
