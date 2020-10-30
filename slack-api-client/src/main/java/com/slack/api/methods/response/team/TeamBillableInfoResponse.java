package com.slack.api.methods.response.team;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.BillableInfo;
import lombok.Data;

import java.util.Map;

@Data
public class TeamBillableInfoResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Map<String, BillableInfo> billableInfo;
}