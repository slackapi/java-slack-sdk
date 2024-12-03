package com.slack.api.methods.response.admin.functions;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.AppFunctionPermissions;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminFunctionsPermissionsLookupResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private Map<String, AppFunctionPermissions> permissions;
    private Map<String, Object> metadata;
    private ResponseMetadata responseMetadata;
    private Object errors; // TODO: typing
}