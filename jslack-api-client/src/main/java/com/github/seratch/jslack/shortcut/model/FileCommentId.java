package com.github.seratch.jslack.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class FileCommentId {

    private final String value;

    public FileCommentId(String value) {
        this.value = value;
    }

    public static FileCommentId of(String value) {
        return new FileCommentId(value);
    }
}
