package com.slack.api.model.connect;

import lombok.Data;

@Data
public class ConnectInviteReview {
    private String type;
    private Integer dateReview;
    private ConnectTeam reviewingTeam;
}
