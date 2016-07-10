package com.github.seratch.jslack.api.methods.request.usergroups;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsergroupsUpdateRequest {

    private String token;
    private String usergroup;
    private String name;
    private String handle;
    private String description;
    private List<String> channels;
    private Integer includeCount;
}