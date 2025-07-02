package com.slack.api.methods.request.calls;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.CallParticipant;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/calls.add
 */
@Data
@Builder
public class CallsAddRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * An ID supplied by the 3rd-party Call provider.
     * It must be unique across all Calls from that service.
     */
    private String externalUniqueId;

    /**
     * The URL required for a client to join the Call.
     */
    private String joinUrl;

    /**
     * The valid Slack user ID of the user who created this Call.
     * When this method is called with a user token, the created_by field is optional
     * and defaults to the authed user of the token. Otherwise, the field is required.
     */
    private String createdBy;

    /**
     * Call start time in UTC UNIX timestamp format
     */
    private Integer dateStart;

    /**
     * When supplied, available Slack clients will attempt to directly launch the 3rd-party Call with this URL.
     */
    private String desktopAppJoinUrl;

    /**
     * An optional, human-readable ID supplied by the 3rd-party Call provider.
     * If supplied, this ID will be displayed in the Call object.
     */
    private String externalDisplayId;

    /**
     * The name of the Call.
     */
    private String title;

    /**
     * The list of users to register as participants in the Call.
     */
    private List<CallParticipant> users;

}