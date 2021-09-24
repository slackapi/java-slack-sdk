package com.slack.api.methods.response.admin.apps;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.RestrictedApp;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAppsRestrictedListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<RestrictedApp> restrictedApps;
    private ResponseMetadata responseMetadata;

}