package com.github.seratch.jslack.lightning.middleware;

import com.github.seratch.jslack.lightning.request.RequestType;

public class MiddlewareOps {
    private MiddlewareOps() {}

    public static boolean isOAuthRequest(RequestType requestType) {
        return requestType == RequestType.OAuthStart || requestType == RequestType.OAuthCallback;
    }
}
