package com.slack.api.bolt.response;

import com.slack.api.Slack;

/**
 * Use com.slack.api.bolt.util.Responder instead. This class will be removed in v2.0.
 */
@Deprecated
public class Responder extends com.slack.api.bolt.util.Responder {

    @Deprecated
    public Responder(Slack slack, String responseUrl) {
        super(slack, responseUrl);
    }
}
