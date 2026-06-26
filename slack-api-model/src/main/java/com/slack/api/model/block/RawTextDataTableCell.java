package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A {@code raw_text} cell within a {@link DataTableBlock}, holding unformatted plain text.
 * The {@code text} must be at least one character long.
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
    private String text;
}
