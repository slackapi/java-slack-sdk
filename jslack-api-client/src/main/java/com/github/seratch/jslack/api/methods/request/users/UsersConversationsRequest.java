package com.github.seratch.jslack.api.methods.request.users;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.ConversationType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * List conversations the calling user may access.
 */
@Data
@Builder
public class UsersConversationsRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:read`
     */
    private String token;

    /**
     * Browse conversations by a specific user ID's membership.
     * Non-public channels are restricted to those where the calling user shares membership."
     */
    private String user;

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

}
