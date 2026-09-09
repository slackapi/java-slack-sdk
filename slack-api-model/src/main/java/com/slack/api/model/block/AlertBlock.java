package com.slack.api.model.block;

import com.slack.api.model.block.composition.TextObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/alert-block
 * <p>
 * Displays an inline alert message. Alert blocks are currently only supported in modals.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertBlock implements LayoutBlock {
    public static final String TYPE = "alert";
    private final String type = TYPE;

    /**
     * The message content of the alert, as a plain_text or mrkdwn text object.
     * Maximum length for the text in this field is 200 characters.
     */
    private TextObject text;

    /**
     * The severity level of the alert. One of default, info, warning, error, or success.
     * Will be default if omitted.
     */
    private String level;

    /**
     * A string acting as a unique identifier for a block. If not specified, one will be generated.
     * Maximum length for this field is 255 characters.
     * block_id should be unique for each message and each iteration of a message.
     * If a message is updated, use a new block_id.
     */
    private String blockId;
}
