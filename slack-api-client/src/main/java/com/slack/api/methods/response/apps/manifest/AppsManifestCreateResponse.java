package com.slack.api.methods.response.apps.manifest;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.AppCredentials;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppsManifestCreateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private String appId;
    private AppCredentials credentials;
    private String oauthAuthorizeUrl;

    private ResponseMetadata responseMetadata;
    private List<Error> errors;

    @Data
    public static class Error {
        private String code;
        private String message;
        private String pointer;
    }
}