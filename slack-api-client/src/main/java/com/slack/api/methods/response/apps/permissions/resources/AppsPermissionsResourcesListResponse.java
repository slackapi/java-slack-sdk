package com.slack.api.methods.response.apps.permissions.resources;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppsPermissionsResourcesListResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Resource> resources;
    private ResponseMetadata responseMetadata;
    private transient Map<String, List<String>> httpResponseHeaders;

    @Data
    public static class Resource {
        private String id;
        private String type;
    }

}