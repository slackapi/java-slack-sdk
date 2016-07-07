package com.github.seratch.jslack.api.methods.request.groups;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupsListRequest {

    private String token;
    private Integer excludeArchived;
}