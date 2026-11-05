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
    private List<Sort> sorts;
    private List<InfoColumnFilter> infoColumnFilters;
    private List<Calculation> calculations;
    private Options options;
    private Boolean isTemplateInitialView;
    private Integer rowHeight;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Grouping {
        private String groupBy;
        private String groupByColumnId;
        // Ordered group values; each entry carries the typed value(s) for its group.
        private List<GroupingOrder> order;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupingOrder {
        private List<String> select;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sort {
        private String key;
        private Boolean ascending;
        private String columnId;
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
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InfoColumnFilter {
        private String key;
        private String operator;
        private List<String> values;
        private List<TypedValue> typedValues;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Calculation {
        private String key;
        private String operator;
        private String columnId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Options {
        private String coverField;
        private String coverFit;
        private String calendarField;
    }

    @Data
    public static class TypedValue {
        // TODO: Add publicly available properties here
    }
}
