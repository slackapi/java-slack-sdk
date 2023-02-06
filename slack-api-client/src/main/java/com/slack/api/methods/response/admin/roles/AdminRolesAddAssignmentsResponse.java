
package com.slack.api.methods.response.admin.roles;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AdminRolesAddAssignmentsResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<RejectedUser> rejectedUsers;

    private ResponseMetadata responseMetadata;

    @Data
    public static class RejectedUser {
        private String id; // e.g., "U02BGTB3S8H"
        private String error; // e.g., "user_is_not_member_in_all_channels_passed"
    }
}