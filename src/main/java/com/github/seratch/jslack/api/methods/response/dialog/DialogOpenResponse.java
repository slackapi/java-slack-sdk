package com.github.seratch.jslack.api.methods.response.dialog;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.dialog.DialogResponseMetadata;
import lombok.Data;

@Data
public class DialogOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private DialogResponseMetadata responseMetadata;
}
