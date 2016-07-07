package com.github.seratch.jslack.api.methods.response.groups;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class GroupsOpenResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private boolean noOp;
    private boolean alreadyOpen;
}