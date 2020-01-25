package com.github.seratch.jslack.api.methods.response.apps.permissions.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AppsPermissionsUsersListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Resource> resources;
    private ResponseMetadata responseMetadata;

    @Data
    public static class Resource {
        private String id;
        private String type;
        private List<String> scopes;
    }

}