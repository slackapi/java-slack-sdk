package com.github.seratch.jslack.api.methods.response.admin.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class AdminUsersAssignResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private ResponseMetadata responseMetadata;

    @Data
    public static class ResponseMetadata {
        private List<String> messages;
    }
}