package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/input-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputBlock implements LayoutBlock {
    public static final String TYPE = "input";
    private final String type = TYPE;

    private String blockId;

    /**
     * A label that appears above an input element in the form of a text object that must have type of plain_text.
     * Maximum length for the text in this field is 2000 characters.
     */
    private PlainTextObject label;

    /**
     * A plain-text input element, a select menu element, a multi-select menu, or a datepicker element.
     */
    private BlockElement element;

    /**
     * A boolean that indicates whether or not use of element in this block
     * should dispatch a block_actions payload. Defaults to false.
     */
    private Boolean dispatchAction;

    /**
     * An optional hint that appears below an input element in a lighter grey.
     * It must be a text object with a type of plain_text.
     * Maximum length for the text in this field is 2000 characters.
     */
    private PlainTextObject hint;

    /**
     * A boolean that indicates whether the input element may be empty when a user submits the modal.
     * Defaults to false.
     */
    private boolean optional;

}
