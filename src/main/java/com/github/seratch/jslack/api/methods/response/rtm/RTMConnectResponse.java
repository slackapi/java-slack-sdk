package com.github.seratch.jslack.api.methods.response.rtm;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Team;
import com.github.seratch.jslack.api.model.User;
import lombok.Data;

/**
 * @see <a href="https://api.slack.com/methods/rtm.connect">rtm.connect</a>
 */
@Data
public class RTMConnectResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String url;
    private User self;
    private Team team;
}
