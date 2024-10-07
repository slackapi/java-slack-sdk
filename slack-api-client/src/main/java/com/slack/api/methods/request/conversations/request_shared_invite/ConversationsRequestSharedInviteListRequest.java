package com.slack.api.methods.request.conversations.request_shared_invite;

import com.slack.api.methods.SlackApiRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * https://api.slack.com/methods/conversations.requestSharedInvite.list
 */
@Data
@Builder
public class ConversationsRequestSharedInviteListRequest implements SlackApiRequest {
    private String token;

    /**
     * Paginate through collections of data by setting the cursor parameter to
     * a next_cursor attribute returned by a previous request's response_metadata.
     * Default value fetches the first "page" of the collection.
     */
    private String cursor;

    /**
     * When true approved invitation requests will be returned, otherwise they will be excluded
     */
    private boolean includeApproved;

    /**
     * When true denied invitation requests will be returned, otherwise they will be excluded
     */
    private boolean includeDenied;

    /**
     * When true expired invitation requests will be returned, otherwise they will be excluded
     */
    private boolean includeExpired;

    /**
     * An optional list of invitation ids to look up.
     */
    private List<String> inviteIds;

    /**
     * The number of items to return. Must be between 1 - 1000 (inclusive). Defaults to 200.
     */
    private Integer limit;

    /**
     * Optional filter to return invitation requests for the inviting user.
     */
    private String userId;
}
