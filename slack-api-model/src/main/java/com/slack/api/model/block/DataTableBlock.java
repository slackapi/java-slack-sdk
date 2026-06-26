package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a sortable, paginated grid of tabular data.
 *
 * <p>Each row is a list of {@link DataTableCell cells}; a cell may be a
 * {@link RawTextDataTableCell} ({@code raw_text}), {@link RawNumberDataTableCell}
 * ({@code raw_number}), or a {@link RichTextBlock} ({@code rich_text}). The first
 * row is the header row; header cells cannot use {@code rich_text}. A data table
 * supports 1-20 columns and up to 100 data rows (101 rows including the header),
 * with all rows sharing the same column count.</p>
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
     * The rows of the table. Each row is a list of cells. The first row is the header row.
     * Minimum 2 rows (header plus one data row); maximum 101 rows. Every row must contain
     * the same number of cells (1-20).
     */
    @Builder.Default
    private List<List<DataTableCell>> rows = new ArrayList<>();

    /**
     * Required. The caption describing the table, used as the caption of the rendered HTML element.
     */
    private String caption;

    /**
     * The number of rows shown per page. Valid range 1-100; defaults to 5 when omitted.
     */
    private Integer pageSize;

    /**
     * Zero-based index of the column that identifies each row. Defaults to 0 when omitted.
     */
    private Integer rowHeaderColumnIndex;

    private String blockId;
}
