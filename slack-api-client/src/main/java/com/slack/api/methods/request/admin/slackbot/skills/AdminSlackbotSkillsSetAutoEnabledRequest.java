package com.slack.api.methods.request.admin.slackbot.skills;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.slackbot.skills.setAutoEnabled
 */
@Data
@Builder
public class AdminSlackbotSkillsSetAutoEnabledRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes. Tokens should be passed as an HTTP Authorization header or alternatively, as a POST parameter.
     */
    private String token;

    /**
     * Canvas skill to deploy.
     */
    private String skillId;

    /**
     * Deployment audience.
     * Acceptable values: `everyone`, `named_entities`, `no_one`
     */
    private String permissionType;

    /**
     * One to 100 user groups for named_entities. Duplicates count toward the input limit and are stored once. Omit for other permission types.
     */
    private List<String> usergroupIds;

}
