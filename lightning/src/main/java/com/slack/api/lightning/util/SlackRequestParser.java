package com.slack.api.lightning.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.app_backend.dialogs.payload.DialogCancellationPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSubmissionPayload;
import com.slack.api.app_backend.dialogs.payload.DialogSuggestionPayload;
import com.slack.api.app_backend.events.payload.EventsApiPayload;
import com.slack.api.app_backend.events.payload.UrlVerificationPayload;
import com.slack.api.app_backend.interactive_components.payload.AttachmentActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.app_backend.interactive_components.payload.BlockSuggestionPayload;
import com.slack.api.app_backend.interactive_components.payload.MessageActionPayload;
import com.slack.api.app_backend.oauth.payload.VerificationCodePayload;
import com.slack.api.app_backend.outgoing_webhooks.WebhookPayloadDetector;
import com.slack.api.app_backend.slash_commands.SlashCommandPayloadDetector;
import com.slack.api.app_backend.ssl_check.SSLCheckPayloadDetector;
import com.slack.api.app_backend.util.JsonPayloadExtractor;
import com.slack.api.app_backend.views.payload.ViewClosedPayload;
import com.slack.api.app_backend.views.payload.ViewSubmissionPayload;
import com.slack.api.lightning.AppConfig;
import com.slack.api.lightning.request.Request;
import com.slack.api.lightning.request.RequestHeaders;
import com.slack.api.lightning.request.builtin.*;
import com.slack.api.util.json.GsonFactory;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class SlackRequestParser {

    private JsonPayloadExtractor jsonPayloadExtractor = new JsonPayloadExtractor();
    private SlashCommandPayloadDetector commandRequestDetector = new SlashCommandPayloadDetector();
    private SSLCheckPayloadDetector sslCheckPayloadDetector = new SSLCheckPayloadDetector();
    private WebhookPayloadDetector webhookRequestDetector = new WebhookPayloadDetector();
    private Gson gson = GsonFactory.createSnakeCase();

    private final AppConfig appConfig;

    @Data
    @Builder
    public static class HttpRequest {
        private String requestUri;
        private Map<String, List<String>> queryString;
        private String requestBody;
        private RequestHeaders headers;
        private String remoteAddress;
    }

    public SlackRequestParser(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Nullable
    public Request<?> parse(HttpRequest httpRequest) {
        String requestUri = httpRequest.getRequestUri();
        String requestBody = httpRequest.getRequestBody();
        RequestHeaders headers = httpRequest.getHeaders();
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
                } else if (sslCheckPayloadDetector.isSSLCheckRequest(requestBody)) {
                    slackRequest = new SSLCheckRequest(requestBody, headers);
                } else if (webhookRequestDetector.isWebhook(requestBody)) {
                    slackRequest = new OutgoingWebhooksRequest(requestBody, headers);
                } else if (appConfig.isOAuthStartEnabled() && appConfig.getOauthStartRequestURI().equals(requestUri)) {
                    slackRequest = new OAuthStartRequest(requestBody, headers);
                } else if (appConfig.isOAuthCallbackEnabled() && appConfig.getOauthCallbackRequestURI().equals(requestUri)) {
                    Map<String, List<String>> queryString = httpRequest.getQueryString();
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

                String ipAddress = headers.getFirstValue("X-FORWARDED-FOR");
                if (ipAddress == null) {
                    ipAddress = httpRequest.getRemoteAddress();
                }
                slackRequest.setClientIpAddress(ipAddress);
            }
        }
    }

}
