package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes the behavior of a single column in a {@link TableBlock}. Up to 20 column
 * settings can be supplied, one per column.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableColumnSetting {
    /**
     * Horizontal text alignment for the column. One of {@code left}, {@code center},
     * or {@code right}. Defaults to {@code left}.
     */
    private String align;
    /**
     * Whether text in the column wraps onto multiple lines. Defaults to {@code false}.
     */
    private Boolean isWrapped;
}
