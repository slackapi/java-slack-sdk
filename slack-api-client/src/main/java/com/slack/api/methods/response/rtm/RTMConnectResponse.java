package com.slack.api.methods.response.rtm;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Team;
import com.slack.api.model.User;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://api.slack.com/methods/rtm.connect">rtm.connect</a>
 */
@Data
public class RTMConnectResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String url;
    private User self;
    private Team team;
}
