package com.slack.api.bolt.middleware;

import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.request.RequestType;
import com.slack.api.bolt.request.builtin.EventRequest;
import com.slack.api.model.event.AppUninstalledEvent;
import com.slack.api.model.event.TokensRevokedEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Utilities for Middleware.
 */
public class MiddlewareOps {

    private MiddlewareOps() {
    }

    /**
     * Returns true if there is no need to verify a request signature with a given request.
     *
     * @param requestType request type
     * @return true if the request normally doesn't have a signature.
     */
    public static boolean isNoSlackSignatureRequest(RequestType requestType) {
        return requestType == RequestType.OAuthStart
                || requestType == RequestType.OAuthCallback
                || requestType == RequestType.SSLCheck;
    }

    /**
     * Returns true if there is no need to check the existence of valid app installations.
     *
     * @param requestType request type
     * @return true if the request normally doesn't have a valid bot token for it.
     */
    public static boolean isNoAuthRequiredRequest(RequestType requestType) {
        return isNoSlackSignatureRequest(requestType)
                || requestType == RequestType.UrlVerification;
    }

    private static final List<String> EVENT_TYPES_TO_SKIP_AUTHORIZATION =
            Arrays.asList(AppUninstalledEvent.TYPE_NAME, TokensRevokedEvent.TYPE_NAME);

    /**
     * Returns true if the request is an Event API payload and matches specific types.
     * @param req request
     * @return true if middleware can skip fetching tokens
     */
    public static boolean isNoTokenRequiredRequest(Request req) {
        if (req.getRequestType() == RequestType.Event) {
            EventRequest eventRequest = (EventRequest) req;
            String eventType = eventRequest.getEventType();
            return eventType != null && EVENT_TYPES_TO_SKIP_AUTHORIZATION.contains(eventType);
        }
        return false;
    }

}
