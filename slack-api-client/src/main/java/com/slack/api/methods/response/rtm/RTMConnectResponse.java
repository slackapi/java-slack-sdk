package com.slack.api.methods.response.rtm;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Team;
import com.slack.api.model.User;
import lombok.Data;

/**
 * @see <a href="https://api.slack.com/methods/rtm.connect">rtm.connect</a>
 */
@Data
public class RTMConnectResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String url;
    private User self;
    private Team team;
}
