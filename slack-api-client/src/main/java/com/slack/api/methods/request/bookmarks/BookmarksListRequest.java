package com.slack.api.methods.request.bookmarks;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/bookmarks.list
 */
@Data
@Builder
public class BookmarksListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `bookmarks:read`
     */
    private String token;

    /**
     * Channel to list bookmarks in.
     */
    private String channelId;
}