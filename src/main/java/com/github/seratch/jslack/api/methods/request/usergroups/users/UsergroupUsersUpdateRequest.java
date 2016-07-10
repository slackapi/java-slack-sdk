package com.github.seratch.jslack.api.methods.request.usergroups.users;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsergroupUsersUpdateRequest {

    private String token;
    private String usergroup;
    private List<String> users;
    private Integer includeCount;
}