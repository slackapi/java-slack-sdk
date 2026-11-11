package com.slack.api.methods.request.admin.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.slackbot.skills.list
 */
@Data
@Builder
public class AdminSlackbotSkillsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Filter by org deployment, independently of publication.
     * Acceptable values: `all`, `enabled`, `disabled`
     */
    private String autoEnabledStatus;

    /**
     * Only return skills in this category.
     * Acceptable values: `communication`, `customer_support`, `data_and_analytics`, `engineering`, `product_management`, `productivity`, `professional_development`, `project_management`, `sales`, `system`, `team_management`, `other`
     */
    private String category;

    /**
     * Only return skills created by these users. Accepts U or W user IDs.
     */
    private List<String> creatorIds;

    /**
     * Continuation from the previous page. Keep all filters and sorting unchanged. Pages can be empty while a continuation remains; continue until next_cursor is empty. Changes during traversal may move results; pagination is not a snapshot.
     */
    private String cursor;

    /**
     * The maximum number of skills to return. Fewer may be returned even when more remain.
     */
    private Integer limit;

    /**
     * Filter by publication to the org catalog.
     * Acceptable values: `all`, `published`, `unpublished`
     */
    private String publishStatus;

    /**
     * Sort across the inventory. Name uses case-insensitive ordering. Unedited skills use their creation date for date_updated sorting.
     * Acceptable values: `name`, `usage_count`, `date_updated`
     */
    private String sort;

    /**
     * Sort direction; file ID breaks ties.
     * Acceptable values: `asc`, `desc`
     */
    private String sortDir;

}
