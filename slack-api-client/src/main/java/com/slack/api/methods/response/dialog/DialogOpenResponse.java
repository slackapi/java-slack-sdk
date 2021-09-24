package com.slack.api.methods.response.dialog;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.dialog.DialogResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DialogOpenResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private DialogResponseMetadata responseMetadata;
}
