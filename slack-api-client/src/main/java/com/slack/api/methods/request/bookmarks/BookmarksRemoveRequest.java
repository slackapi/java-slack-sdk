package com.slack.api.methods.request.bookmarks;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/bookmarks.remove
 */
@Data
@Builder
public class BookmarksRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `bookmarks:write`
     */
    private String token;

    /**
     * Bookmark to remove.
     */
    private String bookmarkId;

    /**
     * Channel to remove bookmark.
     */
    private String channelId;
}