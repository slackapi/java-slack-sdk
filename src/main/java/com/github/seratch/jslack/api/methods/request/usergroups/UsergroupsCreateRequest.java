package com.github.seratch.jslack.api.methods.request.usergroups;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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