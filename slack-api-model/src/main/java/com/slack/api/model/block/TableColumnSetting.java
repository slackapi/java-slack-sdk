package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes the behavior of a column in a {@link TableBlock}.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block#schema-for-column_settings">Schema for column_settings</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableColumnSetting {

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
