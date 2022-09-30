package com.slack.api.methods.response.files;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FilesUploadV2Response implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private File file;
    private List<File> files;

    private final List<FilesGetUploadURLExternalResponse> getURLResponses = new ArrayList<>();
    private final Map<String, FilesUploadV2Response.UploadResponse> uploadResponses = new HashMap<>();
    private FilesCompleteUploadExternalResponse completeResponse;
    private final List<FilesInfoResponse> fileInfoResponses = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadResponse {
        private int code;
        private String body;
    }
}