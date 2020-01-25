package com.github.seratch.jslack.lightning.handler;

import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;

@FunctionalInterface
public interface OAuthExceptionHandler {

    Response handle(OAuthCallbackRequest req, Exception e);

}
