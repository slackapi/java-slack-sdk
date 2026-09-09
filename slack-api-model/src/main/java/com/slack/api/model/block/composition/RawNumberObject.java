package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_number} table cell, holding a numeric value. The optional {@code text}
 * field carries the display representation of the value (for example a formatted string).
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawNumberObject implements TableCell {
    public static final String TYPE = "raw_number";
    private final String type = TYPE;
    private Double value;
    private String text;
}
