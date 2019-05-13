package com.github.seratch.jslack.api.methods.request.groups;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupsInfoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `groups:read`
     */
    private String token;

    /**
     * Private channel to get info on
     */
    private String channel;

    /**
     * Set this to `true` to receive the locale for this group. Defaults to `false`
     */
    private boolean includeLocale;
}