package com.slack.api.methods.response.im;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Message;
import lombok.Data;

import java.util.List;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class ImRepliesResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Message.MessageRoot> messages;
    private boolean hasMore;
}