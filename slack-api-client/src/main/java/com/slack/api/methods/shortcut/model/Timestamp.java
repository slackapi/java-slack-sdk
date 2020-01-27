package com.slack.api.methods.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class Timestamp {

    private final String value;

    public Timestamp(String value) {
        this.value = value;
    }

    public static Timestamp of(String value) {
        return new Timestamp(value);
    }
}