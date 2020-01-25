package com.slack.api.lightning.middleware;

import com.slack.api.lightning.request.RequestType;

public class MiddlewareOps {
    private MiddlewareOps() {
    }

    public static boolean isNoSlackSignatureRequest(RequestType requestType) {
        return requestType == RequestType.OAuthStart
                || requestType == RequestType.OAuthCallback
                || requestType == RequestType.SSLCheck;
    }

    public static boolean isNoAuthRequiredRequest(RequestType requestType) {
        return isNoSlackSignatureRequest(requestType)
                || requestType == RequestType.UrlVerification;
    }
}
