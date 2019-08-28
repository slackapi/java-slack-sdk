package com.github.seratch.jslack.api.status.v2;

import com.github.seratch.jslack.api.status.v2.model.CurrentStatus;
import com.github.seratch.jslack.api.status.v2.model.SlackIssue;

import java.io.IOException;
import java.util.List;

/**
 * https://api.slack.com/docs/slack-status
 */
public interface StatusClient {

    CurrentStatus current() throws IOException, StatusApiException;

    List<SlackIssue> history() throws IOException, StatusApiException;

}
