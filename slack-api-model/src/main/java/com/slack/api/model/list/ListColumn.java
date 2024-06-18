package com.slack.api.model.list;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListColumn {
    private String id;
    private String name;
    private String key;
    private String type;
    @SerializedName("is_primary_column")
    private boolean primaryColumn;
    private ListColumnOptions options;
}
