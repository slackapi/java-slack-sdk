package com.github.seratch.jslack.api.methods.response.rtm;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.*;
import lombok.Data;

import java.util.List;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 */
@Data
public class RTMStartResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String url;
    private User self;
    private Team team;
    private List<User> users;
    private List<Channel> channels;
    private List<Group> groups;
    private List<Im> ims;
}
