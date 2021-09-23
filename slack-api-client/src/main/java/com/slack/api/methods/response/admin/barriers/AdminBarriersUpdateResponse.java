package com.slack.api.methods.response.admin.barriers;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.InformationBarrier;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminBarriersUpdateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private InformationBarrier barrier;
    private ResponseMetadata responseMetadata;
}