package com.slack.api.methods.request.dnd;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/dnd.info
 */
@Data
@Builder
public class DndInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `dnd:read`
     */
    private String token;

    /**
     * User to fetch status for (defaults to current user)
     */
    private String user;

    private String teamId;

}