package com.slack.api.methods.request.slackbot.skill_sets;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skillSets.delete
 */
@Data
@Builder
public class SlackbotSkillSetsDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Skill set to delete.
     */
    private String skillSetId;

}
