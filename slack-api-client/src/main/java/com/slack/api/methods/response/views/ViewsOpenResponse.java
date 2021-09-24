package com.slack.api.methods.response.views;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ErrorResponseMetadata;
import com.slack.api.model.view.View;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ViewsOpenResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private View view;

    private ErrorResponseMetadata responseMetadata;
}
