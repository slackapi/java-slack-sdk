package com.slack.api.methods.response.admin.barriers;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.admin.InformationBarrier;
import lombok.Data;

import java.util.List;

@Data
public class AdminBarriersListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<InformationBarrier> barriers;
    private ResponseMetadata responseMetadata;
}