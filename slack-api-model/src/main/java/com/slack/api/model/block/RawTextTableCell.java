package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_text} table cell, holding unformatted plain text.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawTextTableCell implements TableCell {
    public static final String TYPE = "raw_text";
    private final String type = TYPE;
    private String text;
}
