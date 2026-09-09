package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays rich tables that support pagination, sorting, filtering, and interactivity.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataTableBlock implements LayoutBlock {
    public static final String TYPE = "data_table";
    private final String type = TYPE;

    /**
     * An array consisting of table rows.
     */
    @Builder.Default
    private List<List<DataTableCell>> rows = new ArrayList<>();

    /**
     * A caption for the table; used as the value for the HTML caption element.
     */
    private String caption;

    /**
     * Number of rows per page. Min {@code 1}, Max {@code 100}. Defaults to {@code 5} if omitted.
     */
    private Integer pageSize;

    /**
     * The 0-based index of the column that uniquely identifies each row (the row header). This column
     * is treated as the row's primary identifier for screen readers. Defaults to {@code 0} if omitted.
     */
    private Integer rowHeaderColumnIndex;

    private String blockId;
}
