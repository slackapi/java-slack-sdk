package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_number} cell within a {@link DataTableBlock}, holding a numeric value.
 * The {@code text} field carries the display representation of the value (for example a
 * formatted string) and must be at least one character long. Columns made up entirely of
 * {@code raw_number} cells are sorted numerically.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawNumberDataTableCell implements DataTableCell {
    public static final String TYPE = "raw_number";
    private final String type = TYPE;
    private Double value;
    private String text;
}
