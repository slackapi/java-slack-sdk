package com.slack.api.model.connect;

import lombok.Data;

@Data
public class ConnectUser {
    private String id;
    private String teamId;
    private String name;
    private Integer updated;
    private ConnectUserProfile profile;
}
