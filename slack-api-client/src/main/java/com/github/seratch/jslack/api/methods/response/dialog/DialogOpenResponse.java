package com.github.seratch.jslack.api.methods.response.dialog;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.dialog.DialogResponseMetadata;
import lombok.Data;

@Data
public class DialogOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private DialogResponseMetadata responseMetadata;
}
