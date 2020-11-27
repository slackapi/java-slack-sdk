package com.slack.api.bolt.request.builtin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.slack.api.bolt.context.builtin.EventContext;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestHeaders;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.util.json.GsonFactory;
import lombok.ToString;

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
        // Th reason why we should do that is that the top-level enterprise_id / team_id
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

        if (event.get("channel") != null && event.get("channel").isJsonPrimitive()) {
            this.getContext().setChannelId(event.get("channel").getAsString());
        } else if (event.get("channel_id") != null) {
            this.getContext().setChannelId(event.get("channel_id").getAsString());
        }
    }

    private EventContext context = new EventContext();

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
        if (eventSubtype == null) {
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
