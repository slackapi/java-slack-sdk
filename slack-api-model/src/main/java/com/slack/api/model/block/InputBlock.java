package com.slack.api.model.block;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.BlockElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://api.slack.com/reference/messaging/blocks#input
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
     * An plain-text input element, a select menu element, or a multi-select menu element.
     */
    private BlockElement element;

    /**
     * An optional hint that appears below an input element in a lighter grey.
     * It must be a a text object with a type of plain_text.
     * Maximum length for the text in this field is 2000 characters.
     */
    private PlainTextObject hint;

    /**
     * A boolean that indicates whether the input element may be empty when a user submits the modal.
     * Defaults to false.
     */
    private boolean optional;

}
