package com.slack.api.model.block.composition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Defines an object containing Slack file information to be used in an image block or image element.
 * <p>
 * This file must be an image, and you must provide either the URL or ID.
 * In addition, the user posting these blocks must have access to this file.
 * If both are provided then the payload will be rejected.
 * Currently only png, jpg, jpeg, and gif Slack image files are supported.
 * <p>
 * @see <a href="https://docs.slack.dev/reference/block-kit/composition-objects/slack-file-object">document</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlackFileObject {

    /**
     * Slack ID of the file.
     */
    private String id;

    /**
     * This URL can be the url_private or the permalink of the Slack file.
     */
    private String url;
}
