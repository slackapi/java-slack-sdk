package com.slack.api.methods.response.admin.apps;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class AdminAppsUninstallResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

}