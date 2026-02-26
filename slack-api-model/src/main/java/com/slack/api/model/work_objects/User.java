package com.slack.api.model.work_objects;

import com.slack.api.util.annotation.Required;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class User {
    @Required
    @Getter
    protected final String userType;

    public boolean isExternalUser() {
        return getUserType().equals("external");
    }

    public boolean isSlackUser() {
        return getUserType().equals("slack");
    }
}
