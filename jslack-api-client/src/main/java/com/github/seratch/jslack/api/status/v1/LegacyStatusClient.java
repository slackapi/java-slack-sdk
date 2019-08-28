package com.github.seratch.jslack.api.status.v1;

import com.github.seratch.jslack.api.status.v1.model.LegacyCurrentStatus;
import com.github.seratch.jslack.api.status.v1.model.LegacySlackIssue;

import java.io.IOException;
import java.util.List;

/**
 * https://api.slack.com/docs/slack-status
 */
public interface LegacyStatusClient {

    LegacyCurrentStatus current() throws IOException, LegacyStatusApiException;

    List<LegacySlackIssue> history() throws IOException, LegacyStatusApiException;

}
