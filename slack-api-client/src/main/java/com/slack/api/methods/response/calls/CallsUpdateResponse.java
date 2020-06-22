package com.slack.api.methods.response.calls;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Call;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

@Data
public class CallsUpdateResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Call call;

    private ResponseMetadata responseMetadata;

}