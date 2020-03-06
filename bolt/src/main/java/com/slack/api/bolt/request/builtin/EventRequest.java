package com.slack.api.bolt.request.builtin;

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
        if (event.get("subtype") != null) {
            this.eventSubtype = event.get("subtype").getAsString();
        } else {
            this.eventSubtype = null;
        }
        this.getContext().setTeamId(payload.get("team_id").getAsString());
        JsonElement enterpriseId = payload.get("enterprise_id");
        if (enterpriseId != null) {
            this.getContext().setTeamId(enterpriseId.getAsString());
        }
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
}
