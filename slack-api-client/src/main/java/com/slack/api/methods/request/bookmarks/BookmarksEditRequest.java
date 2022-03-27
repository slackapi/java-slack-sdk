package com.slack.api.methods.request.bookmarks;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/bookmarks.edit
 */
@Data
@Builder
public class BookmarksEditRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `bookmarks:write`
     */
    private String token;

    /**
     * Bookmark to update.
     */
    private String bookmarkId;

    /**
     * Channel to update bookmark in.
     */
    private String channelId;

    /**
     * Link to bookmark.
     */
    private String link;

    /**
     * Emoji tag to apply to the link.
     */
    private String emoji;

    /**
     * Title for the bookmark.
     */
    private String title;
}