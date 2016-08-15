package com.github.seratch.jslack.shortcut.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = false)
public class ApiToken {

    private final String value;

    public ApiToken(String value) {
        this.value = value;
    }

    public static ApiToken of(String value) {
        return new ApiToken(value);
    }
}
