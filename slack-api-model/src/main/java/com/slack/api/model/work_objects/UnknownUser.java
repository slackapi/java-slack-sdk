package com.slack.api.model.work_objects;

import lombok.Builder;

public class UnknownUser extends User {
    public UnknownUser() {
        super("unknown");
    }

    @Builder
    public UnknownUser(String userType) {
        super(userType);
    }
}
