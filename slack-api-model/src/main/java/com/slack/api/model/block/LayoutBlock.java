package com.slack.api.model.block;

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
    String getType();

    /**
     * Returns the block_id string; the value can be null if the object is manually crafted.
     */
    String getBlockId();

}
