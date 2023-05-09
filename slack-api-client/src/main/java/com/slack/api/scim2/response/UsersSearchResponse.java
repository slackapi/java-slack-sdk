package com.slack.api.scim2.response;

import com.google.gson.annotations.SerializedName;
import com.slack.api.scim2.SCIM2ApiResponse;
import com.slack.api.scim2.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class UsersSearchResponse implements SCIM2ApiResponse {
    private Integer totalResults;
    private Integer itemsPerPage;
    private Integer startIndex;
    private List<String> schemas;
    @SerializedName("Resources")
    private List<User> resources;
}
