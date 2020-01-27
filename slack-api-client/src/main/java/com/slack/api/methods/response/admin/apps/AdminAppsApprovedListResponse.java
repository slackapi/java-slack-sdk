package com.slack.api.methods.response.admin.apps;

import com.slack.api.methods.SlackApiResponse;
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