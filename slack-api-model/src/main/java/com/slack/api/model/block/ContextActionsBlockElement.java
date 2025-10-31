package com.slack.api.model.block;

import com.slack.api.model.block.element.FeedbackButtonsElement;
import com.slack.api.model.block.element.IconButtonElement;

/**
 * Specific interface to make context actions layout blocks' {@link ContextActionsBlock} elements type-safe,
 * because ContextActionsBlock can only contain {@link FeedbackButtonsElement} and {@link IconButtonElement} elements.
 * <p>
 * Slack Block Kit Reference: <a href="https://docs.slack.dev/reference/block-kit/blocks/context-actions-block">Context Actions Block's elements</a>
 */
public interface ContextActionsBlockElement {

    String getType();

}
