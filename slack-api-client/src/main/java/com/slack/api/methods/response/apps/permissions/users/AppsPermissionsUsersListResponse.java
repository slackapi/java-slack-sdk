package com.slack.api.methods.response.apps.permissions.users;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppsPermissionsUsersListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Resource> resources;
    private ResponseMetadata responseMetadata;

    @Data
    public static class Resource {
        private String id;
        private String type;
        private List<String> scopes;
    }

}