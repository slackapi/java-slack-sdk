package com.slack.api.methods.request.bookmarks;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/bookmarks.add
 */
@Data
@Builder
public class BookmarksAddRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `bookmarks:write`
     */
    private String token;

    /**
     * Channel to add bookmark in.
     */
    private String channelId;

    /**
     * Title for the bookmark.
     */
    private String title;

    /**
     * Type of the bookmark i.e link.
     */
    private String type;

    /**
     * Emoji tag to apply to the link.
     */
    private String emoji;

    /**
     * ID of the entity being bookmarked. Only applies to message and file types.
     */
    private String entityId;

    /**
     * Link to bookmark.
     */
    private String link;

    /**
     * Id of this bookmark's parent
     */
    private String parentId;

}