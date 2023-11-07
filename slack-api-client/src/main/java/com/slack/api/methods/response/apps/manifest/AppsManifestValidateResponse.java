package com.slack.api.methods.response.apps.manifest;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AppsManifestValidateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private ResponseMetadata responseMetadata;
    private List<AppsManifestCreateResponse.Error> errors;

    @Data
    public static class Error {
        private String code;
        private String message;
        private String pointer;
    }
}