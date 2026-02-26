package com.slack.api.model.work_objects;

import lombok.Getter;
import lombok.Setter;

public class SlackUser extends User {
    private static final String USER_TYPE = "slack";

    @Getter @Setter
    private String userId;

    @Getter @Setter
    private UserMetadata metadata;

    public SlackUser() {
        super(USER_TYPE);
    }
}
