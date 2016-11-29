package com.github.seratch.jslack.api.methods.request.usergroups.users;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

@Data
@Builder
public class UsergroupUsersUpdateRequest implements SlackApiRequest {

    private String token;
    private String usergroup;
    private List<String> users;
    private Integer includeCount;
}