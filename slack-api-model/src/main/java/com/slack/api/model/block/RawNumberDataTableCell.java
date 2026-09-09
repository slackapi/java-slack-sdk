package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_number} cell within a {@link DataTableBlock}: an object containing a numeric value.
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

    /**
     * The numeric value.
     */
    private Double value;

    /**
     * The text used to display the value. The minimum length is 1 character.
     */
    private String text;
}
