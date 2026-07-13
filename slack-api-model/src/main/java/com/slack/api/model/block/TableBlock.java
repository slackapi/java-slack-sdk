package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays tabular data as a grid of rows and cells.
 *
 * <p>Each row is a list of {@link TableCell cells}; a cell may be a
 * {@link RawTextTableCell} ({@code raw_text}), {@link RawNumberTableCell}
 * ({@code raw_number}), or a {@link RichTextBlock} ({@code rich_text}). A table
 * supports up to 100 rows and up to 20 cells per row, with optional per-column
 * behavior configured via {@link TableColumnSetting column settings}.</p>
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableBlock implements LayoutBlock {
    public static final String TYPE = "table";
    private final String type = TYPE;

    /**
     * The rows of the table. Each row is a list of cells. Maximum 100 rows, with up to
     * 20 cells per row.
     */
    @Builder.Default
    private List<List<TableCell>> rows = new ArrayList<>();

    /**
     * Per-column behavior configuration (alignment, wrapping). Maximum 20 items.
     */
    private List<TableColumnSetting> columnSettings;

    private String blockId;
}
