package com.github.seratch.jslack.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class FileId {

    private final String value;

    public FileId(String value) {
        this.value = value;
    }

    public static FileId of(String value) {
        return new FileId(value);
    }
}