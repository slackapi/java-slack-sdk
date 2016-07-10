package com.github.seratch.jslack.api.methods.request.usergroups;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsergroupsListRequest {

    private String token;
    private Integer includeDisabled;
    private Integer includeCount;
    private Integer includeUsers;
}