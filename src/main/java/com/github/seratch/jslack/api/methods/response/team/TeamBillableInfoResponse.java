package com.github.seratch.jslack.api.methods.response.team;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.BillableInfo;
import lombok.Data;

import java.util.Map;

@Data
public class TeamBillableInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private Map<String, BillableInfo> billableInfo;
}