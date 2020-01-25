package com.github.seratch.jslack.api.methods.response.groups;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.Group;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class GroupsCreateResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private Group group;
}