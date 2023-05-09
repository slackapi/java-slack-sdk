package com.slack.api.scim2.model;

import com.google.gson.annotations.SerializedName;

public enum PatchOperation {
    @SerializedName("add")
    Add,
    @SerializedName("replace")
    Replace,
    @SerializedName("remove")
    Remove,
}
