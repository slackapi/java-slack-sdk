package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.WorkflowObject;
import lombok.*;

/**
 * https://api.slack.com/reference/block-kit/block-elements#workflow_button
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WorkflowButtonElement extends BlockElement {
    public static final String TYPE = "workflow_button";
    private final String type = TYPE;

    private String actionId;

    /**
     * A text object that defines the button's text. Can only be of type: plain_text.
     * text may truncate with ~30 characters. Maximum length for the text in this field is
     * 75 characters.
     */
    private PlainTextObject text;

    /**
     * A workflow object that contains details about the workflow that will run
     * when the button is clicked.
     */
    private WorkflowObject workflow;

    /**
     * Decorates buttons with alternative visual color schemes. Use this option with restraint.
     * primary gives buttons a green outline and text, ideal for affirmation or confirmation actions.
     * primary should only be used for one button within a set.
     * danger gives buttons a red outline and text, and should be used when the action is destructive.
     * Use danger even more sparingly than primary.
     * If you don't include this field, the default button style will be used.
     */
    private String style;

    /**
     * A label for longer descriptive text about a button element.
     * This label will be read out by screen readers instead of the button text object.
     * Maximum length for this field is 75 characters.
     */
    private String accessibilityLabel;
}
