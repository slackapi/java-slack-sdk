package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsHistoryRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:history`
     */
    private String token;

    /**
     * Conversation ID to fetch history for.
     */
    private String channel;

    /**
     * Paginate through collections of data by setting the `cursor` parameter to a `next_cursor` attribute
     * returned by a previous request's `response_metadata`.
     * <p>
     * Default value fetches the first \"page\" of the collection. See [pagination](/docs/pagination) for more detail.
     */
    private String cursor;

    /**
     * Start of time range of messages to include in results.
     */
    private String oldest;

    /**
     * End of time range of messages to include in results.
     */
    private String latest;

    /**
     * The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the users list hasn't been reached.
     */
    private Integer limit;

    /**
     * Include messages with latest or oldest timestamp in results only when either timestamp is specified.
     */
    private boolean inclusive;

}
