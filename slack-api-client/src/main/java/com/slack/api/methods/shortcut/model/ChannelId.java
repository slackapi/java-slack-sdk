package com.slack.api.methods.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class ChannelId {

    private final String value;

    public ChannelId(String value) {
        this.value = value;
    }

    public static ChannelId of(String value) {
        return new ChannelId(value);
    }
}
