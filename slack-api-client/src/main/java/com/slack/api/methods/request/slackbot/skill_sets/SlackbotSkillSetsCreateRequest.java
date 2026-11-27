package com.slack.api.methods.request.slackbot.skill_sets;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skillSets.create
 */
@Data
@Builder
public class SlackbotSkillSetsCreateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Human-readable label, 1 to 255 characters. Not unique.
     */
    private String displayName;

    /**
     * What the skill set is for. Max 2,000 characters.
     */
    private String description;

}
