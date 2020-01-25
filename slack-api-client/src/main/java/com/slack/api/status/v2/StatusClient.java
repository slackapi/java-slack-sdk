package com.slack.api.status.v2;

import com.slack.api.status.v2.model.CurrentStatus;
import com.slack.api.status.v2.model.SlackIssue;

import java.io.IOException;
import java.util.List;

/**
 * https://api.slack.com/docs/slack-status
 */
public interface StatusClient {

    String ENDPOINT_URL_PREFIX = "https://status.slack.com/api/v2.0.0/";

    String getEndpointUrlPrefix();

    void setEndpointUrlPrefix(String endpointUrlPrefix);

    CurrentStatus current() throws IOException, StatusApiException;

    List<SlackIssue> history() throws IOException, StatusApiException;

}
