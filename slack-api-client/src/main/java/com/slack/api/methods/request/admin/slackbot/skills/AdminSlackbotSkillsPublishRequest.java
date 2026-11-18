package com.slack.api.methods.request.admin.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.slackbot.skills.publish
 */
@Data
@Builder
public class AdminSlackbotSkillsPublishRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * One to 50 skill IDs. Successful items are not rolled back if other items fail.
     */
    private List<String> skillIds;

}
