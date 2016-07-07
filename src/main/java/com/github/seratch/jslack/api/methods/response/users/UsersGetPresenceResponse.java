package com.github.seratch.jslack.api.methods.response.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class UsersGetPresenceResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String presence;
    private boolean online;
    private boolean autoAway;
    private boolean manualAway;
    private Integer connectionCount;
    private Integer lastActivity;
}