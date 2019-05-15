package com.github.seratch.jslack.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class EmojiName {

    private final String value;

    public EmojiName(String value) {
        this.value = value;
    }

    public static EmojiName of(String value) {
        return new EmojiName(value);
    }
}
