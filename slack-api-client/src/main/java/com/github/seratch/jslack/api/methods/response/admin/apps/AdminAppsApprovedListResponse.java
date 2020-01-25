package com.github.seratch.jslack.api.methods.response.admin.apps;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.ApprovedApp;
import lombok.Data;

import java.util.List;

@Data
public class AdminAppsApprovedListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<ApprovedApp> approvedApps;
    private ResponseMetadata responseMetadata;

}