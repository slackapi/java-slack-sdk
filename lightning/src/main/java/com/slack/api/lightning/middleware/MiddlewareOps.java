package com.slack.api.lightning.middleware;

import com.slack.api.lightning.request.RequestType;

/**
 * Utilities for Middleware.
 */
public class MiddlewareOps {

    private MiddlewareOps() {
    }

    /**
     * Returns true if there is no need to verify a request signature with a given request.
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
     * @param requestType request type
     * @return true if the request normally doesn't have a valid bot token for it.
     */
    public static boolean isNoAuthRequiredRequest(RequestType requestType) {
        return isNoSlackSignatureRequest(requestType)
                || requestType == RequestType.UrlVerification;
    }
}
