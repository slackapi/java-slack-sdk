package com.slack.api.methods.response.canvases.sections;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.canvas.CanvasDocumentSection;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CanvasesSectionsLookupResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<CanvasDocumentSection> sections;

    private ResponseMetadata responseMetadata;

}