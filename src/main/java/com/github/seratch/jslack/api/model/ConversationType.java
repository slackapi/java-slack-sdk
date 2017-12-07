package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a type of conversation such as a public channel or IM.
 *
 * @see <a href="https://api.slack.com/methods/conversations.list">Conversations.list API</a>
 */
public enum ConversationType {
    @SerializedName("public_channel") PUBLIC_CHANNEL("public_channel"),
    @SerializedName("private_channel") PRIVATE_CHANNEL("private_channel"),
    @SerializedName("mpim") MPIM("mpim"),
    @SerializedName("im") IM("im");

    private final String value;

    ConversationType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
