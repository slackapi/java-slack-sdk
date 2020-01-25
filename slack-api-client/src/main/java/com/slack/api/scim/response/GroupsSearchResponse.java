package com.slack.api.scim.response;

import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.model.Group;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class GroupsSearchResponse implements SCIMApiResponse {
    private Integer totalResults;
    private Integer itemsPerPage;
    private Integer startIndex;
    private List<String> schemas;
    @SerializedName("Resources")
    private List<Group> resources;
}
