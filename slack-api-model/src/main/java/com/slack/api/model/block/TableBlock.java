package com.slack.api.model.block;

import com.slack.api.model.block.composition.TableCell;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays structured information in a table.
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
     * An array consisting of table rows. Maximum 100 rows. Each row object is an array with a max of 20 table
     * cells. Table cells can have a type of {@code rich_text}, {@code raw_text}, or {@code raw_number}.
     */
    @Builder.Default
    private List<List<TableCell>> rows = new ArrayList<>();

    /**
     * An array describing column behavior. If there are fewer items in the {@code column_settings} array than
     * there are columns in the table, then the items in the the {@code column_settings} array will describe the
     * same number of columns in the table as there are in the array itself. Any additional columns will have the
     * default behavior. Maximum 20 items.
     */
    private List<ColumnSetting> columnSettings;

    private String blockId;

    /**
     * Describes the behavior of a column in a {@link TableBlock}.
     *
     * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block#schema-for-column_settings">Schema for column_settings</a>
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColumnSetting {

        /**
         * The alignment for items in this column. Can be {@code left}, {@code center}, or {@code right}. Defaults to
         * {@code left} if not defined.
         */
        private String align;

        /**
         * Whether the contents of this column should be wrapped or not. Defaults to {@code false} if not defined.
         */
        private Boolean isWrapped;
    }
}
