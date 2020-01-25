package com.slack.api.methods.response.admin.invite_requests;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class AdminInviteRequestsDeniedListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<String> deniedRequests;

}