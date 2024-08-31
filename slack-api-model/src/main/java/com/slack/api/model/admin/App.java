package com.slack.api.model.admin;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class App {
    private String id;
    private String name;
    private String description;
    private String helpUrl;
    private String privacyPolicyUrl;
    private String appHomepageUrl;
    private String appDirectoryUrl;
    @SerializedName("is_granular_bot_app")
    @SerializedName("is_app_directory_approved")
    private boolean appDirectoryApproved;
    @SerializedName("is_internal")
    private boolean internal;
    private String additionalInfo;
    private AppIcons icons;
}
