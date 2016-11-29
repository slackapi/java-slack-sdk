package com.github.seratch.jslack.api.methods.request.usergroups;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsergroupsUpdateRequest implements SlackApiRequest {

    private String token;
    private String usergroup;
    private String name;
    private String handle;
    private String description;
    private List<String> channels;
    private Integer includeCount;
}