package com.slack.api.methods.response.groups;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.ThreadInfo;
import lombok.Data;

import java.util.List;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class GroupsRepliesResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Message> messages;
    private ThreadInfo threadInfo;
    private boolean hasMore;
    private ResponseMetadata responseMetadata;
}
