package com.slack.api.status.v1;

import com.slack.api.status.v1.model.LegacyCurrentStatus;
import com.slack.api.status.v1.model.LegacySlackIssue;

import java.io.IOException;
import java.util.List;

/**
 * Slack Status API V1 Client.
 *
 * @see <a href="https://docs.slack.dev/reference/slack-status-api">Slack Status API</a>
 */
public interface LegacyStatusClient {

    String ENDPOINT_URL_PREFIX = "https://slack-status.com/api/v1.0.0/";

    String getEndpointUrlPrefix();

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    LegacyCurrentStatus current() throws IOException, LegacyStatusApiException;

    List<LegacySlackIssue> history() throws IOException, LegacyStatusApiException;

}
