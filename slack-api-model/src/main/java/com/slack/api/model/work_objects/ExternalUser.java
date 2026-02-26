package com.slack.api.model.work_objects;

public class ExternalUser extends User {
    private static final String USER_TYPE = "external";

    public ExternalUser() {
        super(USER_TYPE);
    }
}
