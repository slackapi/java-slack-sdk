package com.slack.api.methods.response.apps.permissions.resources;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class AppsPermissionsResourcesListResponse implements SlackApiTextResponse {

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
    }

}