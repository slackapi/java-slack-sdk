package com.slack.api.methods.request.admin.barriers;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.barriers.update
 */
@Data
@Builder
public class AdminBarriersUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * The ID of the barrier you're trying to modify
     */
    private String barrierId;

    /**
     * A list of IDP Groups ids that the primary usergroup is to be barriered from.
     */
    private List<String> barrieredFromUsergroupIds;

    /**
     * The id of the primary IDP Group
     */
    private String primaryUsergroupId;

    /**
     * What kind of interactions are blocked by this barrier?
     * For v1, we only support a list of all 3, e.g. im, mpim, call
     */
    private List<String> restrictedSubjects;

}
