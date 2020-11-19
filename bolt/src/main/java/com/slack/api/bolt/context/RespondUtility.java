package com.slack.api.bolt.context;

import com.slack.api.Slack;
import com.slack.api.bolt.util.Responder;

public interface RespondUtility {

    Slack getSlack();

    String getResponseUrl();

    Responder getResponder();

    void setResponder(Responder responder);

}
