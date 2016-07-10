package com.github.seratch.jslack.api.methods.request.usergroups.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupUsersListRequest {

    private String token;
    private String usergroup;
    private Integer includeDisabled;
}