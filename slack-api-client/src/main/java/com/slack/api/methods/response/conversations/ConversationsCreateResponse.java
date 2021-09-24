package com.slack.api.methods.response.conversations;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Conversation;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ConversationsCreateResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private transient Map<String, List<String>> httpResponseHeaders;

    // {
    //   "ok": false,
    //   "error": "invalid_name_maxlength",
    //   "detail": "Value passed for `name` exceeded max length."
    // }
    private String detail;

    private String needed;
    private String provided;

    private Conversation channel;
}
