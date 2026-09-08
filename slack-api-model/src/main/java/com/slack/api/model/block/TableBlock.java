package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.slack.api.model.block.composition.TableCell;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays tabular data as a grid of rows and cells.
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
