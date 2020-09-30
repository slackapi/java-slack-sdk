package com.slack.api.bolt.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.app_backend.dialogs.payload.DialogCancellationPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.app_backend.events.payload.UrlVerificationPayload;
import com.slack.api.app_backend.interactive_components.payload.*;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.app_backend.slash_commands.SlashCommandPayloadDetector;
import com.slack.api.app_backend.ssl_check.SSLCheckPayloadDetector;
import com.slack.api.app_backend.util.JsonPayloadExtractor;
import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.app_backend.views.payload.WorkflowStepSavePayload;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.builtin.*;
import com.slack.api.model.event.WorkflowStepExecuteEvent;
import com.slack.api.util.json.GsonFactory;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses an HTTP request and generates a concrete Slack request.
 */
@Data
@Slf4j
public class SlackRequestParser {

    private final AppConfig appConfig;

    // NOTE: these are intentionally instance fields.
    private JsonPayloadExtractor jsonPayloadExtractor = new JsonPayloadExtractor();
    private SlashCommandPayloadDetector commandRequestDetector = new SlashCommandPayloadDetector();
    private SSLCheckPayloadDetector sslCheckPayloadDetector = new SSLCheckPayloadDetector();
    private Gson gson = GsonFactory.createSnakeCase();

    public SlackRequestParser(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Data
    @Builder
    public static class HttpRequest {
        private String requestUri;
        private Map<String, List<String>> queryString;
        private String requestBody;
        private RequestHeaders headers;
        private String remoteAddress;
    }

    public Request<?> parse(HttpRequest httpRequest) {
        String requestUri = httpRequest.getRequestUri();
        String requestBody = httpRequest.getRequestBody();
        RequestHeaders headers = httpRequest.getHeaders();
        Request<?> slackRequest = null;
        try {
            String jsonPayload = jsonPayloadExtractor.extractIfExists(requestBody);
            if (jsonPayload != null) {
                JsonObject payload = gson.fromJson(jsonPayload, JsonElement.class).getAsJsonObject();
                JsonElement typeElem = payload.get("type");
                if (typeElem == null) {
                    return null;
                }
                String payloadType = typeElem.getAsString();
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
                    case GlobalShortcutPayload.TYPE:
                        slackRequest = new GlobalShortcutRequest(requestBody, jsonPayload, headers);
                        break;
                    case MessageShortcutPayload.TYPE:
                        slackRequest = new MessageShortcutRequest(requestBody, jsonPayload, headers);
                        break;
                    case EventsApiPayload.TYPE:
                        String type = payload.get("event").getAsJsonObject().get("type").getAsString();
                        if (type.equals(WorkflowStepExecuteEvent.TYPE_NAME)) {
                            slackRequest = new WorkflowStepExecuteRequest(jsonPayload, headers);
                        } else {
                            slackRequest = new EventRequest(jsonPayload, headers);
                        }
                        break;
                    case UrlVerificationPayload.TYPE:
                        slackRequest = new UrlVerificationRequest(jsonPayload, headers);
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
                        String viewType = payload.get("view").getAsJsonObject().get("type").getAsString();
                        if (viewType.equals("workflow_step")) {
                            slackRequest = new WorkflowStepSaveRequest(requestBody, jsonPayload, headers);
                        } else {
                            slackRequest = new ViewSubmissionRequest(requestBody, jsonPayload, headers);
                        }
                        break;
                    case ViewClosedPayload.TYPE:
                        slackRequest = new ViewClosedRequest(requestBody, jsonPayload, headers);
                        break;
                    case WorkflowStepEditPayload.TYPE:
                        slackRequest = new WorkflowStepEditRequest(requestBody, jsonPayload, headers);
                        break;
                    default:
                        log.warn("No request pattern detected for {}", jsonPayload);
                }
            } else {
                if (commandRequestDetector.isCommand(requestBody)) {
                    slackRequest = new SlashCommandRequest(requestBody, headers);
                } else if (sslCheckPayloadDetector.isSSLCheckRequest(requestBody)) {
                    slackRequest = new SSLCheckRequest(requestBody, headers);
                } else if (appConfig.isOAuthStartEnabled() && appConfig.getOauthStartRequestURI().equals(requestUri)) {
                    slackRequest = new OAuthStartRequest(requestBody, headers);
                } else if (appConfig.isOAuthCallbackEnabled()
                        && appConfig.getOauthCallbackRequestURI().equals(requestUri)
                        && httpRequest.getQueryString() != null) {
                    Map<String, List<String>> queryString = new HashMap<>();
                    for (Map.Entry<String, List<String>> original : httpRequest.getQueryString().entrySet()) {
                        if (original.getValue() != null) {
                            // To ensure the value is mutable
                            queryString.put(original.getKey(), new ArrayList<>(original.getValue()));
                        }
                    }
                    VerificationCodePayload payload = VerificationCodePayload.from(queryString);
                    slackRequest = new OAuthCallbackRequest(queryString, requestBody, payload, headers);
                } else {
                    log.warn("No request pattern detected for {}", requestBody);
                }
            }
            return slackRequest;

        } finally {
            if (slackRequest != null) {
                slackRequest.updateContext(appConfig);

                Map<String, List<String>> queryString = httpRequest.getQueryString();
                if (queryString != null) {
                    for (Map.Entry<String, List<String>> each : queryString.entrySet()) {
                        List<String> values = slackRequest.getQueryString().get(each.getKey());
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        values.addAll(each.getValue());
                        slackRequest.getQueryString().put(each.getKey(), values);
                    }
                }

                String ipAddress = headers.getFirstValue("X-FORWARDED-FOR");
                if (ipAddress == null) {
                    ipAddress = httpRequest.getRemoteAddress();
                }
                slackRequest.setClientIpAddress(ipAddress);
            }
        }
    }

}
