package com.github.seratch.jslack.api.methods.response.admin.apps;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.ResponseMetadata;
import com.github.seratch.jslack.api.model.admin.AppRequest;
import lombok.Data;

import java.util.List;

@Data
public class AdminAppsRequestsListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<AppRequest> appRequests;
    private ResponseMetadata responseMetadata;

}