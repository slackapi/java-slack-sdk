package com.github.seratch.jslack.api.methods.request.dnd;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

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

}