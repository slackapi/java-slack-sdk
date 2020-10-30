package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class ConversationsHistoryResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String latest;
    private List<Message> messages;
    private boolean hasMore;
    private Integer pinCount;
    private String channelActionsTs;
    private Integer channelActionsCount;
    private ResponseMetadata responseMetadata;
}
