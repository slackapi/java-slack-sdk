package com.github.seratch.jslack.lightning.servlet;

import com.github.seratch.jslack.app_backend.dialogs.payload.DialogCancellationPayload;
import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.github.seratch.jslack.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.github.seratch.jslack.app_backend.events.payload.EventsApiPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.AttachmentActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockActionPayload;
import com.github.seratch.jslack.app_backend.interactive_messages.payload.BlockSuggestionPayload;
import com.github.seratch.jslack.app_backend.message_actions.payload.MessageActionPayload;
import com.github.seratch.jslack.app_backend.oauth.payload.VerificationCodePayload;
import com.github.seratch.jslack.app_backend.util.JsonPayloadExtractor;
import com.github.seratch.jslack.app_backend.util.OutgoingWebhooksRequestDetector;
import com.github.seratch.jslack.app_backend.util.SlashCommandRequestDetector;
import com.github.seratch.jslack.app_backend.views.payload.ViewClosedPayload;
import com.github.seratch.jslack.app_backend.views.payload.ViewSubmissionPayload;
import com.github.seratch.jslack.common.json.GsonFactory;
import com.github.seratch.jslack.lightning.AppConfig;
import com.github.seratch.jslack.lightning.request.Request;
import com.github.seratch.jslack.lightning.request.RequestHeaders;
import com.github.seratch.jslack.lightning.request.builtin.*;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.util.QueryStringParser;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ServletAdapter {

    private AppConfig appConfig;
    private JsonPayloadExtractor jsonPayloadExtractor = new JsonPayloadExtractor();
    private SlashCommandRequestDetector commandRequestDetector = new SlashCommandRequestDetector();
    private OutgoingWebhooksRequestDetector webhookRequestDetector = new OutgoingWebhooksRequestDetector();
    private Gson gson = GsonFactory.createSnakeCase();

    public ServletAdapter(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    protected String doReadRequestBodyAsString(HttpServletRequest req) throws IOException {
        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    public Request<?> buildSlackRequest(HttpServletRequest req) throws IOException {
        String requestBody = doReadRequestBodyAsString(req);
        RequestHeaders headers = new RequestHeaders(toHeaderMap(req));
        Request<?> slackRequest = null;
        try {
            String jsonPayload = jsonPayloadExtractor.extractIfExists(requestBody);
            if (jsonPayload != null) {
                JsonObject payload = gson.fromJson(jsonPayload, JsonElement.class).getAsJsonObject();
                String payloadType = payload.get("type").getAsString();
                switch (payloadType) {
                    case AttachmentActionPayload.TYPE:
                        slackRequest = new AttachmentActionRequest(requestBody, jsonPayload, headers);
                        break;
                    case BlockActionPayload.TYPE:
                        slackRequest = new BlockActionRequest(requestBody, jsonPayload, headers);
                        break;
                    case BlockSuggestionPayload.TYPE:
                        slackRequest = new BlockSuggestionRequest(requestBody, jsonPayload, headers);
                        break;
                    case MessageActionPayload.TYPE:
                        slackRequest = new MessageActionRequest(requestBody, jsonPayload, headers);
                        break;
                    case EventsApiPayload.TYPE:
                        slackRequest = new EventRequest(jsonPayload, headers);
                        break;
                    case "url_verification":
                        String challenge = payload.get("challenge").getAsString();
                        slackRequest = new UrlVerificationRequest(challenge, headers);
                        break;
                    case DialogCancellationPayload.TYPE:
                        slackRequest = new DialogCancellationRequest(requestBody, jsonPayload, headers);
                        break;
                    case DialogSubmissionPayload.TYPE:
                        slackRequest = new DialogSubmissionRequest(requestBody, jsonPayload, headers);
                        break;
                    case DialogSuggestionPayload.TYPE:
                        slackRequest = new DialogSuggestionRequest(requestBody, jsonPayload, headers);
                        break;
                    case ViewSubmissionPayload.TYPE:
                        slackRequest = new ViewSubmissionRequest(requestBody, jsonPayload, headers);
                        break;
                    case ViewClosedPayload.TYPE:
                        slackRequest = new ViewClosedRequest(requestBody, jsonPayload, headers);
                        break;
                    default:
                        log.warn("No request pattern detected for {}", jsonPayload);
                }
            } else {
                if (commandRequestDetector.isCommand(requestBody)) {
                    slackRequest = new SlashCommandRequest(requestBody, headers);
                } else if (webhookRequestDetector.isWebhook(requestBody)) {
                    slackRequest = new OutgoingWebhooksRequest(requestBody, headers);
                } else if (appConfig.isOAuthStartEnabled() && appConfig.getOauthStartRequestURI().equals(req.getRequestURI())) {
                    slackRequest = new OAuthStartRequest(requestBody, headers);
                } else if (appConfig.isOAuthCallbackEnabled() && appConfig.getOauthCallbackRequestURI().equals(req.getRequestURI())) {
                    VerificationCodePayload payload = VerificationCodePayload.from(QueryStringParser.toMap(req.getQueryString()));
                    slackRequest = new OAuthCallbackRequest(requestBody, payload, headers);
                } else {
                    log.warn("No request pattern detected for {}", requestBody);
                }
            }
            return slackRequest;

        } finally {
            if (slackRequest != null) {
                slackRequest.updateContext(appConfig);

                String ipAddress = req.getHeader("X-FORWARDED-FOR");
                if (ipAddress == null) {
                    ipAddress = req.getRemoteAddr();
                }
                slackRequest.setClientIpAddress(ipAddress);
            }
        }
    }

    private static Map<String, String> toHeaderMap(HttpServletRequest req) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> names = req.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            map.put(name, req.getHeader(name));
        }
        return map;
    }

    public void writeResponse(HttpServletResponse resp, Response slackResp) throws IOException {
        resp.setStatus(slackResp.getStatusCode());
        for (Map.Entry<String, String> header : slackResp.getHeaders().entrySet()) {
            resp.setHeader(header.getKey(), header.getValue());
        }
        resp.setHeader("Content-Type", slackResp.getContentType());
        if (slackResp.getBody() != null) {
            resp.getWriter().write(slackResp.getBody());
        }
    }
}
