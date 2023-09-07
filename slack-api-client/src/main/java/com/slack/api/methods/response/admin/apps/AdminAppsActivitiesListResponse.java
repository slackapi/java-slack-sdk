package com.slack.api.methods.response.admin.apps;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.AppActivity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAppsActivitiesListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<AppActivity> activities;
    private ResponseMetadata responseMetadata;

}