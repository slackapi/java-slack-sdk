package com.slack.api.model.block.composition;

import com.slack.api.model.block.element.ButtonElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines a cell containing a button. Action cells can only be used in the data table block, not the table block.
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionCellObject implements TableCell {
    public static final String TYPE = "action_cell";
    private final String type = TYPE;

    /**
     * A button element.
     */
    private ButtonElement element;

    /**
     * A raw_text or raw_number cell.
     */
    private TableCell fallback;
}
