package com.slack.api.bolt.service;

import com.slack.api.bolt.request.builtin.OAuthCallbackRequest;
import com.slack.api.bolt.response.Response;

/**
 * Handle callback requests from the Slack OAuth confirmation page.
 */
public interface OAuthCallbackService {

    Response handle(OAuthCallbackRequest request);

}
