package com.slack.api.methods.response.canvases.access;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CanvasesAccessDeleteResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<String> failedToUpdateChannelIds;
    private List<String> failedToUpdateUserIds;

    private ResponseMetadata responseMetadata;
}