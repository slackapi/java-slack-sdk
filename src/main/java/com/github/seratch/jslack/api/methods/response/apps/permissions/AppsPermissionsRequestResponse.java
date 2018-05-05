package com.github.seratch.jslack.api.methods.response.apps.permissions;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class AppsPermissionsRequestResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

}