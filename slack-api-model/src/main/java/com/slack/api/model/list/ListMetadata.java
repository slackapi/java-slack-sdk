package com.slack.api.model.list;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.LayoutBlock;
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
    private List<ListColumn> subtaskSchema;
    private List<ListView> views;
    private List<String> integrations;
    private List<LayoutBlock> descriptionBlocks;
}
