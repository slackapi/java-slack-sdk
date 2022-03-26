package com.slack.api.methods.request.dnd;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://api.slack.com/methods/dnd.endDnd
 */
@Data
@Builder
public class DndEndDndRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `dnd:write`
     */
    private String token;

}