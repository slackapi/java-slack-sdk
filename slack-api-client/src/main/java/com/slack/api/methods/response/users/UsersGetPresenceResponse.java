package com.slack.api.methods.response.users;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class UsersGetPresenceResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String presence;
    private boolean online;
    private boolean autoAway;
    private boolean manualAway;
    private Integer connectionCount;
    private Integer lastActivity;
}