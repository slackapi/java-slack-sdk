package com.slack.api.methods.response.calls.participants;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Call;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

@Data
public class CallsParticipantsAddResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Call call;

    private ResponseMetadata responseMetadata;

}