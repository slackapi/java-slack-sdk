package com.slack.api.methods.response.admin.invite_requests;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.admin.InviteRequest;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminInviteRequestsDeniedListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<DeniedInviteRequest> deniedRequests;

    @Data
    public static class DeniedInviteRequest {
        private InviteRequest inviteRequest;
        private DeniedBy deniedBy;
    }

    @Data
    public static class DeniedBy {
        private String actorType;
        private String actorId;
    }
}