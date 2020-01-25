package com.slack.api.scim.response;

import com.slack.api.scim.SCIMApiResponse;
import com.slack.api.scim.model.User;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class UsersSearchResponse implements SCIMApiResponse {
    private Integer totalResults;
    private Integer itemsPerPage;
    private Integer startIndex;
    private List<String> schemas;
    @SerializedName("Resources")
    private List<User> resources;
}
