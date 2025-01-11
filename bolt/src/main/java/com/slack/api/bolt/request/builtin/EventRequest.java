package com.slack.api.bolt.request.builtin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.bolt.util.EventsApiPayloadParser;
import com.slack.api.model.assistant.AssistantThreadContext;
import com.slack.api.model.event.FunctionExecutedEvent;
import com.slack.api.model.event.MessageEvent;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

import java.util.Locale;

@ToString(callSuper = true)
public class EventRequest extends Request<EventContext> {

    private final String requestBody;
    private final RequestHeaders headers;
    private final String eventType;
    private final String eventSubtype;

    public EventRequest(
            String requestBody,
            RequestHeaders headers) {
        this.requestBody = requestBody;
        this.headers = headers;
        JsonObject payload = GsonFactory.createSnakeCase().fromJson(requestBody, JsonElement.class).getAsJsonObject();
        JsonObject event = payload.get("event").getAsJsonObject();
        this.eventType = event.get("type").getAsString();

        // As of Nov 2020, only .authorizations[].enterprise_id can be "null" in JSON data.
        // That being said, just in case, we should always check if a value is not null
        // because if we call #getAsString or similar without checking that may result in a runtime exception.

        if (event.get("subtype") != null && !event.get("subtype").isJsonNull()) {
            this.eventSubtype = event.get("subtype").getAsString();
        } else {
            this.eventSubtype = null;
        }
        String enterpriseId = null;
        String teamId = null;

        // To properly support events generated in a shared channel, we should prioritize
        // enterprise_id / team_id in authorizations over the ones at the top-level.
        // The reason why we should do that is that the top-level enterprise_id / team_id
        // can be the one for a workspace that originated the shared channel,
        // not the ID of the workspace where this app was installed.
        //
        // Bolt for Java does not support authed_teams here.
        // We highly recommend switching to authorizations as it provides more information
        // the app installation like enterprise_id that is missing in authed_teams.
        //
        if (payload.get("authorizations") != null && !payload.get("authorizations").isJsonNull()) {
            JsonArray authorizations = payload.get("authorizations").getAsJsonArray();
            if (authorizations.size() > 0) {
                JsonObject authorization = authorizations.get(0).getAsJsonObject();
                if (authorization != null && !authorization.isJsonNull()) {
                    JsonElement enterpriseIdElement = authorization.get("enterprise_id");
                    if (enterpriseIdElement != null && !enterpriseIdElement.isJsonNull()) {
                        enterpriseId = enterpriseIdElement.getAsString();
                    }
                    JsonElement teamIdElement = authorization.get("team_id");
                    if (teamIdElement != null && !teamIdElement.isJsonNull()) {
                        teamId = teamIdElement.getAsString();
                    }
                }
            }
        } else {
            // NOTE: When this app works in shared channels,
            // these IDs could be unusable for authorize function
            // as it may be the source of the events, not the installed org/workspace
            JsonElement enterpriseIdElement = payload.get("enterprise_id");
            if (enterpriseIdElement != null && !enterpriseIdElement.isJsonNull()) {
                enterpriseId = enterpriseIdElement.getAsString();
            }
            JsonElement teamIdElement = payload.get("team_id");
            if (teamIdElement != null && !teamIdElement.isJsonNull()) {
                teamId = teamIdElement.getAsString();
            }
        }
        this.getContext().setEnterpriseId(enterpriseId);
        this.getContext().setTeamId(teamId);
        this.getContext().setRequestUserId(extractRequestUserId(payload));

        // set retry related header values to the context
        if (this.headers != null && this.headers.getNames().size() > 0) {
            for (String name : this.headers.getNames()) {
                String normalizedName = name.toLowerCase(Locale.ENGLISH);
                String value = this.headers.getFirstValue(name);
                if (normalizedName.equals("x-slack-retry-num")) {
                    try {
                        Integer num = Integer.parseInt(value);
                        this.getContext().setRetryNum(num);
                    } catch (NumberFormatException e) {
                    }
                }
                if (normalizedName.equals("x-slack-retry-reason")) {
                    this.getContext().setRetryReason(value);
                }
            }
        }

        if (event.get("channel") != null && event.get("channel").isJsonPrimitive()) {
            this.getContext().setChannelId(event.get("channel").getAsString());
        } else if (event.get("channel_id") != null) {
            this.getContext().setChannelId(event.get("channel_id").getAsString());
        }
        // assistant thread events
        if (EventsApiPayloadParser.isAssistantThreadStartedOrContextChangedEvent(event)) {
            this.getContext().setAssistantThreadEvent(true);
            // assistant_thread_started, assistant_thread_context_changed events
            JsonObject thread = event.get("assistant_thread").getAsJsonObject();
            String channelId = thread.get("channel_id").getAsString();
            this.getContext().setChannelId(channelId);
            String threadTs = thread.get("thread_ts").getAsString();
            this.getContext().setThreadTs(threadTs);
            JsonObject context = thread.get("context").getAsJsonObject();
            if (context != null) {
                AssistantThreadContext threadContext = AssistantThreadContext.builder()
                        // enterprise_id here can be a null value
                        // others cannot be null as of Jan 2025, but added the same logic to all for future safety
                        .enterpriseId(context.get("enterprise_id") != null && !context.get("enterprise_id").isJsonNull() ? context.get("enterprise_id").getAsString() : null)
                        .teamId(context.get("team_id") != null && !context.get("team_id").isJsonNull() ? context.get("team_id").getAsString() : null)
                        .channelId(context.get("channel_id") != null && !context.get("channel_id").isJsonNull() ? context.get("channel_id").getAsString() : null)
                        .threadEntryPoint(context.get("thread_entry_point") != null && !context.get("thread_entry_point").isJsonNull() ? context.get("thread_entry_point").getAsString() : null)
                        .build();
                this.getContext().setThreadContext(threadContext);
            }
        } else if (this.eventType != null
                && this.eventType.equals(MessageEvent.TYPE_NAME)
                && EventsApiPayloadParser.isMessageEventInAssistantThread(event)) {
            // message events (user replies)
            this.getContext().setAssistantThreadEvent(true);
            this.getContext().setChannelId(event.get("channel").getAsString());
            if (event.get("thread_ts") != null
                    && !event.get("thread_ts").isJsonNull()) {
                this.getContext().setThreadTs(event.get("thread_ts").getAsString());
            } else if (event.get("message") != null
                    && event.get("message").getAsJsonObject().get("thread_ts") != null
                    && !event.get("message").getAsJsonObject().get("thread_ts").isJsonNull()) {
                // message_changed
                this.getContext().setThreadTs(event.get("message").getAsJsonObject().get("thread_ts").getAsString());
            }
            // Assistant middleware can set threadContext using AssistantThreadContextStore
        }

        if (this.eventType != null && this.eventType.equals(FunctionExecutedEvent.TYPE_NAME)) {
            if (event.get("bot_access_token") != null) {
                String functionBotAccessToken = event.get("bot_access_token").getAsString();
                this.getContext().setFunctionBotAccessToken(functionBotAccessToken);
            }
            if (event.get("function_execution_id") != null) {
                String functionExecutionId = event.get("function_execution_id").getAsString();
                this.getContext().setFunctionExecutionId(functionExecutionId);
            }
        }
    }

    private static String extractRequestUserId(JsonObject payload) {
        if (payload.get("user") != null) {
            if (payload.get("user").isJsonPrimitive()) {
                return payload.get("user").getAsString();
            } else if (payload.get("user").isJsonObject()) {
                JsonElement userId = payload.get("user").getAsJsonObject().get("id");
                if (userId != null) {
                    return userId.getAsString();
                }
            }
        }
        if (payload.get("user_id") != null) {
            return payload.get("user_id").getAsString();
        }
        if (payload.get("event") != null) {
            return extractRequestUserId(payload.get("event").getAsJsonObject());
        }
        if (payload.get("message") != null) {
            // message_changed: body["event"]["message"]
            return extractRequestUserId(payload.get("message").getAsJsonObject());
        }
        if (payload.get("previous_message") != null) {
            // message_deleted: body["event"]["previous_message"]
            return extractRequestUserId(payload.get("previous_message").getAsJsonObject());
        }
        return null;
    }

    private boolean isMessageEventInAssistantThread(JsonObject event) {
        if (event.get("channel_type") != null && event.get("channel_type").getAsString().equals("im")) {
            if (event.get("thread_ts") != null) return true;
            if (event.get("message") != null) {
                // message_changed
                return isAssistantThreadMessageSubEvent(event, "message");
            } else if (event.get("previous_message") != null) {
                // message_deleted
                return isAssistantThreadMessageSubEvent(event, "previous_message");
            }
        }
        return false;
    }

    private boolean isAssistantThreadMessageSubEvent(JsonObject event, String message) {
        JsonElement subtype = event.get(message).getAsJsonObject().get("subtype");
        return (subtype != null && subtype.getAsString().equals("assistant_app_thread"))
                || event.get(message).getAsJsonObject().get("thread_ts") != null;
    }

    private final EventContext context = new EventContext();

    @Override
    public EventContext getContext() {
        return context;
    }

    @Override
    public RequestType getRequestType() {
        return RequestType.Event;
    }

    @Override
    public String getRequestBodyAsString() {
        return requestBody;
    }

    @Override
    public RequestHeaders getHeaders() {
        return this.headers;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventTypeAndSubtype() {
        // Since the data structure of the "message" type event significantly varies among subtypes,
        // This key string needs to consider the subtype for the event.
        // Other events with subtypes (e.g., "app_mention") do not have such differences,
        // so we can reuse the same data classes for them.
        if (!eventType.equals(MessageEvent.TYPE_NAME) || eventSubtype == null) {
            return eventType;
        } else {
            return eventType + ":" + eventSubtype;
        }
    }

    @Override
    public String getResponseUrl() {
        return null;
    }
}