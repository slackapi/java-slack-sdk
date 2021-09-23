package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ConversationsRepliesResponse implements SlackApiTextResponse {
    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Message> messages;
    private boolean hasMore;
    private ResponseMetadata responseMetadata;
}
