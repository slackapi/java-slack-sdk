package com.slack.api.model.list;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListMetadata {

    private String icon;
    private String iconUrl;
    private String iconTeamId;
    private String description;
    @SerializedName("is_trial")
    private boolean trial;
    private ListCreationSource creationSource;
    private List<ListColumn> schema;
    private List<ListView> views;
    private List<String> integrations;
}
