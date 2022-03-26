package com.slack.api.methods.request.conversations;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.ConversationType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/conversations.list
 */
@Data
@Builder
public class ConversationsListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:read`
     */
    private String token;

    /**
     * Paginate through collections of data by setting the `cursor` parameter to
     * a `next_cursor` attribute returned by a previous request's `response_metadata`.
     * Default value fetches the first \"page\" of the collection. See [pagination](/docs/pagination) for more detail.
     */
    private String cursor;

    /**
     * Set to `true` to exclude archived channels from the list
     */
    private boolean excludeArchived;

    /**
     * The maximum number of items to return. Fewer than the requested number of items may be returned,
     * even if the end of the list hasn't been reached. Must be an integer no larger than 1000.
     */
    private Integer limit;

    /**
     * Mix and match channel types by providing a comma-separated list of
     * any combination of `public_channel`, `private_channel`, `mpim`, `im`
     */
    private List<ConversationType> types;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}
