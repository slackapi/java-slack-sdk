package com.slack.api.methods.request.groups;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class GroupsCloseRequest implements SlackApiRequest {

    private String token;
    private String channel;
}