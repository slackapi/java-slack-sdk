package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_text} cell within a {@link DataTableBlock}: an object containing some text.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawTextDataTableCell implements DataTableCell {
    public static final String TYPE = "raw_text";
    private final String type = TYPE;

    /**
     * The text for the block. The minimum length is 1 character.
     */
    private String text;
}
