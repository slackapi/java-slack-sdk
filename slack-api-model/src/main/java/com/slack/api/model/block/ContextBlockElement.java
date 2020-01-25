package com.slack.api.model.block;

import com.slack.api.model.block.composition.TextObject;
import com.slack.api.model.block.element.ImageElement;

/**
 * Specific interface to make context layout blocks' {@link ContextBlock
 * elements} type-safe, because ContextBlock can only contain
 * {@link ImageElement} and {@link TextObject} elements.
 * <p>
 * Slack Block Kit Reference: <a href=
 * "https://api.slack.com/reference/messaging/blocks#context">Context
 * Block's elements</a>
 */
public interface ContextBlockElement {
}
