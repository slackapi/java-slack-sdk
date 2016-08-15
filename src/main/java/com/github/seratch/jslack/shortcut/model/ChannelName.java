package com.github.seratch.jslack.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class ChannelName {

    private final String value;

    public ChannelName(String value) {
        this.value = value;
    }

    public static ChannelName of(String value) {
        return new ChannelName(value);
    }
}
