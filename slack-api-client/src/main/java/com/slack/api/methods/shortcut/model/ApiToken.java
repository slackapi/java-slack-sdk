package com.slack.api.methods.shortcut.model;

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
