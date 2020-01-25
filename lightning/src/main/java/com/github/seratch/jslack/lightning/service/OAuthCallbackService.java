package com.github.seratch.jslack.lightning.service;

import com.github.seratch.jslack.lightning.request.builtin.OAuthCallbackRequest;
import com.github.seratch.jslack.lightning.response.Response;

public interface OAuthCallbackService {

    Response handle(OAuthCallbackRequest request);

}
