package com.slack.api.lightning.service;

import com.slack.api.lightning.request.builtin.OAuthCallbackRequest;
import com.slack.api.lightning.response.Response;

/**
 * Handle callback requests from the Slack OAuth confirmation page.
 */
public interface OAuthCallbackService {

    Response handle(OAuthCallbackRequest request);

}
