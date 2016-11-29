package com.github.seratch.jslack.api.methods.request.usergroups;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupsEnableRequest implements SlackApiRequest {

    private String token;
    private String usergroup;
    private Integer includeCount;
}