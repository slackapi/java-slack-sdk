package com.slack.api.methods.response.admin.apps;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.AppConfig;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminAppsConfigLookupResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<AppConfig> configs;
    private ResponseMetadata responseMetadata;
}