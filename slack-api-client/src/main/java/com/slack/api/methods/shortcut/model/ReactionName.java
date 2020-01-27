package com.slack.api.methods.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class ReactionName {

    private final String value;

    public ReactionName(String value) {
        this.value = value;
    }

    public static ReactionName of(String value) {
        return new ReactionName(value);
    }
}
