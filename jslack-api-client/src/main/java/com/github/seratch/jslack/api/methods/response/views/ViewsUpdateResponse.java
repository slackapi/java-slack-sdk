package com.github.seratch.jslack.api.methods.response.views;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.ErrorResponseMetadata;
import com.github.seratch.jslack.api.model.view.View;
import lombok.Data;

@Data
public class ViewsUpdateResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private View view;

    private ErrorResponseMetadata responseMetadata;
}