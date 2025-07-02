package com.slack.api.methods.request.calls;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/calls.update
 */
@Data
@Builder
public class CallsUpdateRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * id returned by the calls.add method.
     */
    private String id;

    /**
     * When supplied, available Slack clients will attempt to
     * directly launch the 3rd-party Call with this URL.
     */
    private String desktopAppJoinUrl;

    /**
     * The URL required for a client to join the Call.
     */
    private String joinUrl;

    /**
     * The name of the Call.
     */
    private String title;

}