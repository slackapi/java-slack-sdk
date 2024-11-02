package com.slack.api.model.list;

import com.google.gson.annotations.SerializedName;
import com.slack.api.model.block.RichTextBlock;
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
    private String defaultViewKey; // "all_items"
    private Boolean showCompletedItems;
    private Grouping grouping;
    private List<Filter> filters;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grouping {
        private String groupBy;
        private String groupByColumnId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Filter {
        private String key;
        private String operator;
        private List<String> values;
        private List<TypedValue> typedValues;
        private String columnId;
    }

    @Data
    public static class TypedValue {
        // TODO: Add publicly available properties here
    }
}
