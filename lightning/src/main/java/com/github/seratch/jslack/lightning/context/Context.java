package com.github.seratch.jslack.lightning.context;

import com.slack.api.Slack;
import com.github.seratch.jslack.api.methods.MethodsClient;
import com.github.seratch.jslack.lightning.response.Response;
import com.github.seratch.jslack.lightning.util.JsonOps;
import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Getter
@Setter
public abstract class Context {

    protected Slack slack;

    protected String enterpriseId;
    protected String teamId;

    protected String botToken;
    protected String botId; // set by MultiTeamsAuthorization
    protected String botUserId;

    protected String requestUserId;
    protected String requestUserToken;

    protected final Map<String, String> additionalValues = new HashMap<>();

    public MethodsClient client() {
        return slack.methods(botToken);
    }

    public Response ack() {
        return Response.ok();
    }

    public Response ackWithJson(Object obj) {
        return ack(toJson(obj));
    }

    public Response ack(JsonElement json) {
        return Response.json(200, json);
    }

    public JsonElement toJson(Object obj) {
        return JsonOps.toJson(obj);
    }

}
