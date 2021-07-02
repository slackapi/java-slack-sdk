package com.slack.api.methods.response.admin.auth.policy;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

@Data
public class AdminAuthPolicyRemoveEntitiesResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Integer entityTotalCount;

    private ResponseMetadata responseMetadata;
}