package com.slack.api.model.dialog;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the type for an {@link DialogSelectElement}
 */
public enum DialogDataSourceType {

    @SerializedName("static") STATIC("static"),
    @SerializedName("users") USERS("users"),
    @SerializedName("channels") CHANNELS("channels"),
    @SerializedName("conversations") CONVERSATIONS("conversations"),
    @SerializedName("external") EXTERNAL("external");

    private final String value;

    DialogDataSourceType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
