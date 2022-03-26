package com.slack.api.methods.request.dnd;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/dnd.teamInfo
 */
@Data
@Builder
public class DndTeamInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `dnd:read`
     */
    private String token;

    /**
     * Comma-separated list of users to fetch Do Not Disturb status for
     */
    private List<String> users;

    private String teamId;

}