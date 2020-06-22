package com.slack.api.methods.response.im;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Channel;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class ImOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private boolean noOp;
    private boolean alreadyOpen;

    private Channel channel;
    private ResponseMetadata responseMetadata;
}