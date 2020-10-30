package com.slack.api.methods.response.apps.permissions;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

@Data
public class AppsPermissionsRequestResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

}