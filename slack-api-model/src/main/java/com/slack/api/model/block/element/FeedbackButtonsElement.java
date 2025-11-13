package com.slack.api.model.block.element;

import com.slack.api.model.block.ContextActionsBlockElement;
import com.slack.api.model.block.composition.FeedbackButtonObject;

import lombok.*;

/**
 * Buttons to indicate positive or negative feedback.
 * https://docs.slack.dev/reference/block-kit/block-elements/feedback-buttons-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class FeedbackButtonsElement extends BlockElement implements ContextActionsBlockElement {
    public static final String TYPE = "feedback_buttons";
    private final String type = TYPE;

    /**
     * An identifier for the action triggered when a menu option is selected.
     * You can use this when you receive an interaction payload to identify the source of the action.
     * Should be unique among all other action_ids used elsewhere by your app.
     * Maximum length for this field is 255 characters.
     */
    private String actionId;

    /**
     * A button to indicate positive feedback.
     */
    private FeedbackButtonObject positiveButton;

    /**
     * A button to indicate negative feedback.
     */
    private FeedbackButtonObject negativeButton;
}
