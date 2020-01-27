package com.slack.api.methods.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class GroupId {

    private final String value;

    public GroupId(String value) {
        this.value = value;
    }

    public static GroupId of(String value) {
        return new GroupId(value);
    }
}