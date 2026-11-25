package com.slack.api.methods.request.slackbot.skill_sets;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skillSets.list
 */
@Data
@Builder
public class SlackbotSkillSetsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * `library`: skill sets in the caller's library. `owned`: skill sets the caller created. `editable`: skill sets the caller can edit. `accessible`: skill sets the caller can view.
     * Acceptable values: `library`, `owned`, `editable`, `accessible`
     */
    private String filter;

    /**
     * Paginate through collections of data by setting the `cursor` parameter to a `next_cursor` attribute returned by a previous request's `response_metadata`. Must be used with the same `filter`.
     */
    private String cursor;

    /**
     * The maximum number of skill sets to return. Fewer may be returned even when more remain.
     */
    private Integer limit;

}
