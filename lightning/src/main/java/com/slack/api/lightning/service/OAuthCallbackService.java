package com.slack.api.lightning.service;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;

public interface OAuthCallbackService {

    Response handle(OAuthCallbackRequest request);

}
