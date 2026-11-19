package com.slack.api.methods.request.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skills.delete
 */
@Data
@Builder
public class SlackbotSkillsDeleteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Skill to delete.
     */
    private String skillId;

}
