package com.slack.api.methods.request.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skills.list
 */
@Data
@Builder
public class SlackbotSkillsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Only return skills in this category.
     */
    private String category;

    /**
     * Paginate through collections of data by setting the `cursor` parameter to a `next_cursor` attribute returned by a previous request's `response_metadata`. Must be used with the same `filter` and `category`.
     */
    private String cursor;

    /**
     * `library`: skills enabled for the caller, including auto-enabled skills and skills from skill sets. `owned`: skills the caller created. `catalog`: the organization's official skills.
     * Acceptable values: `library`, `owned`, `catalog`
     */
    private String filter;

    /**
     * The maximum number of skills to return. Fewer may be returned even when more remain.
     */
    private Integer limit;

}
