package com.github.seratch.jslack.api.model.block;

/**
 * Block Kit is a new UI framework that offers you more control and flexibility
 * when building messages for Slack. Comprised of "blocks," stackable bits of
 * message UI, you can customize the order and appearance of information
 * delivered by your app in Slack.
 *
 * @see <a href="https://api.slack.com/block-kit">Block Kit Guide</a>
 * @see <a href="https://api.slack.com/reference/messaging/blocks">Block Kit Reference</a>
 */
public interface LayoutBlock {
    /**
     * Determines the type of layout block, e.g. section, divider, context, actions
     * and image.
     */
    public String getType();
}
