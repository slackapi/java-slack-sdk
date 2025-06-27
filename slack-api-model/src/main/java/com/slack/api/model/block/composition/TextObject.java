package com.slack.api.model.block.composition;

import com.slack.api.model.block.ContextBlockElement;

/**
 * https://docs.slack.dev/messaging/migrating-outmoded-message-compositions-to-blocks
 */
public abstract class TextObject implements ContextBlockElement {

    public abstract String getText();

}
