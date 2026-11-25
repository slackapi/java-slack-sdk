package com.slack.api.methods.request.slackbot.skill_sets.library;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/slackbot.skillSets.library.add
 */
@Data
@Builder
public class SlackbotSkillSetsLibraryAddRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Skill set to add to the caller's library.
     */
    private String skillSetId;

}
