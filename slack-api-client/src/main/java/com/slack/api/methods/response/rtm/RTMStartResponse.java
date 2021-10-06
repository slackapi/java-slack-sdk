package com.slack.api.methods.response.rtm;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://api.slack.com/methods/rtm.start">rtm.start</a>
 * @deprecated Use `rtm.connect` API method instead
 */
@Data
@Deprecated
public class RTMStartResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String url;
    private User self;
    private Team team;
    private List<User> users;
    private Prefs prefs;
    private List<Channel> channels;
    private List<Group> groups;
    private List<Im> ims;

    @Data
    public static class Prefs {
        // TODO
    }
}
