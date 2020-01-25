package com.github.seratch.jslack.lightning.context;

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
public class WebEndpointContext {

    protected final Map<String, String> additionalValues = new HashMap<>();

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
