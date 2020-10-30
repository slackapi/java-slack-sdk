package com.slack.api.methods.response.dialog;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.dialog.DialogResponseMetadata;
import lombok.Data;

@Data
public class DialogOpenResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private DialogResponseMetadata responseMetadata;
}
