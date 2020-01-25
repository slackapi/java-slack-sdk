package com.slack.api.methods.response.admin.invite_requests;

import com.slack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class AdminInviteRequestsApproveResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

}