package com.slack.api.methods.request.slackbot.skills.library;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skills.library.remove
 */
@Data
@Builder
public class SlackbotSkillsLibraryRemoveRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Skill to turn off for the caller.
     */
    private String skillId;

}
