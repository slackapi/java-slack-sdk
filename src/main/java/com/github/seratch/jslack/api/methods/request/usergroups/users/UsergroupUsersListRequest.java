package com.github.seratch.jslack.api.methods.request.usergroups.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupUsersListRequest implements SlackApiRequest {

    private String token;
    private String usergroup;
    private Integer includeDisabled;
}