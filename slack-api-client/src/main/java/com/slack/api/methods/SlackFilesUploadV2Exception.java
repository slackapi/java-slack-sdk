package com.slack.api.methods;

import com.slack.api.methods.response.files.FilesCompleteUploadExternalResponse;
import com.slack.api.methods.response.files.FilesGetUploadURLExternalResponse;
import com.slack.api.methods.response.files.FilesInfoResponse;
import com.slack.api.methods.response.files.FilesUploadV2Response;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an error returned from Slack Web APIs.
 */
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class SlackFilesUploadV2Exception extends RuntimeException {

    private final List<FilesGetUploadURLExternalResponse> getURLResponses = new ArrayList<>();
    private final Map<String, FilesUploadV2Response.UploadResponse> uploadResponses = new HashMap<>();
    private FilesCompleteUploadExternalResponse completeResponse;
    private final List<FilesInfoResponse> fileInfoResponses = new ArrayList<>();
}
