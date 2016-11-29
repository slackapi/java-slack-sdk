package com.github.seratch.jslack.api.methods.request.usergroups;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

@Data
@Builder
public class UsergroupsCreateRequest implements SlackApiRequest {

    private String token;
    private String name;
    private String handle;
    private String description;
    private List<String> channels;
    private Integer includeCount;
}