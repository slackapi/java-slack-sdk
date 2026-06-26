package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_number} table cell, holding a numeric value. The optional {@code text}
 * field carries the display representation of the value (for example a formatted string).
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawNumberTableCell implements TableCell {
    public static final String TYPE = "raw_number";
    private final String type = TYPE;
    private Double value;
    private String text;
}
