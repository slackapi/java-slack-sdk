package com.slack.api.methods.request.slackbot.skill_sets.items;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skillSets.items.remove
 */
@Data
@Builder
public class SlackbotSkillSetsItemsRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Skill set to remove the skills from.
     */
    private String skillSetId;

    /**
     * One to 20 skill IDs. Each distinct ID is processed once. Successful items are not rolled back if other items fail.
     */
    private List<String> skillIds;

}
