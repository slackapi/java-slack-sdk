package com.slack.api.methods.response.mpim;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Group;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class MpimListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Group> groups;
    private ResponseMetadata responseMetadata;
}