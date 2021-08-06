package com.slack.api.model.block.composition;

import com.slack.api.model.block.ContextBlockElement;

/**
 * https://api.slack.com/reference/messaging/composition-objects#text
 */
public abstract class TextObject implements ContextBlockElement {

    public abstract String getText();

}
