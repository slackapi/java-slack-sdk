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
public class ListView {
    private String id;
    private String name;
    private String type;
    @SerializedName("is_locked")
    private boolean locked;
    private String position;
    private List<ListViewColumn> columns;
    private Long dateCreated;
    private String createdBy;
    private Boolean stickColumnLeft;
    private Boolean isAllItemsView;
}
