package com.github.seratch.jslack.api.methods.request.team;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `team:read`
     */
    private String token;

}