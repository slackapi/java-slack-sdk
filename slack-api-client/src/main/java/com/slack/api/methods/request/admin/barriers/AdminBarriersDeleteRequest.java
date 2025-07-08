package com.slack.api.methods.request.admin.barriers;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/admin.barriers.delete
 */
@Data
@Builder
public class AdminBarriersDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The ID of the barrier you're trying to delete
     */
    private String barrierId;

}
