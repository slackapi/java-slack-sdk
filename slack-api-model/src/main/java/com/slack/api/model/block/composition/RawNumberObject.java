package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines an object containing a numeric value.
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

    /**
     * The numeric value.
     */
    private Double value;

    /**
     * The text used to display the value. The minimum length is 1 character.
     */
    private String text;
}
