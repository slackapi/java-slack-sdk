package com.github.seratch.jslack.api.model.dialog;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the subtype for an {@link DialogTextElement} or {@link DialogTextAreaElement}
 * In some form factors, optimized input is provided for this subtype.
 */
public enum DialogSubType {
    @SerializedName("email") EMAIL("email"),
    @SerializedName("number") NUMBER("number"),
    @SerializedName("tel") TEL("tel"),
    @SerializedName("url") URL("url");

    private final String value;

    DialogSubType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
