package com.slack.api.methods.response.groups;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class GroupsHistoryResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private String latest;
    private List<Message> messages;
    private boolean hasMore;
    private String channelActionsTs;
    private Integer channelActionsCount;
    private ResponseMetadata responseMetadata;
}