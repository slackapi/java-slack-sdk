package com.slack.api.bolt.context;

import com.google.gson.JsonElement;
import com.slack.api.Slack;
import com.slack.api.bolt.App;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.bolt.util.JsonOps;
import com.slack.api.methods.AsyncMethodsClient;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a context behind a request from Slack API.
 */
@ToString
@Getter
@Setter
public abstract class Context {

    protected Slack slack;

    public final Logger logger = LoggerFactory.getLogger(App.class);

    /**
     * Organization ID for Enterprise Grid.
     */
    protected String enterpriseId;

    /**
     * Workspace ID.
     */
    protected String teamId;

    /**
     * A bot token associated with this request. The format must be starting with `xoxb-`.
     */
    protected String botToken;
    /**
     * bot_id associated with this request.
     */
    protected String botId; // set by MultiTeamsAuthorization
    /**
     * Bot user's user_id associated with this request.
     */
    protected String botUserId;

    /**
     * An install user's user_id associated with this request.
     */
    protected String requestUserId;
    /**
     * An install user's access token associated with this request.
     */
    protected String requestUserToken;

    protected final Map<String, String> additionalValues = new HashMap<>();

    public MethodsClient client() {
        return getSlack().methods(botToken);
    }

    public AsyncMethodsClient asyncClient() {
        return getSlack().methodsAsync(botToken);
    }

    public ChatPostMessageResponse say(BuilderConfigurator<ChatPostMessageRequest.ChatPostMessageRequestBuilder> request) throws IOException, SlackApiException {
        MethodsClient client = client();
        ChatPostMessageResponse response = client.chatPostMessage(request.configure(ChatPostMessageRequest.builder()).build());
        return response;
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
