package com.slack.api.model.connect;

import com.slack.api.model.TeamIcon;
import lombok.Data;

@Data
public class ConnectTeam {
    private String id; // E12345, T12345
    private String name;
    private TeamIcon icon;
    private boolean isVerified;
    private String domain;
    private Integer dateCreated;
}
