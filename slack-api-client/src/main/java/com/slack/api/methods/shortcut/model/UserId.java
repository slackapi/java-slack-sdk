package com.slack.api.methods.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class UserId {

    private final String value;

    public UserId(String value) {
        this.value = value;
    }

    public static UserId of(String value) {
        return new UserId(value);
    }
}