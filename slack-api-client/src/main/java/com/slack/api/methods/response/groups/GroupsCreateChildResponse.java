package com.slack.api.methods.response.groups;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Group;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class GroupsCreateChildResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Group group;
}