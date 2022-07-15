package com.slack.api.methods.request.admin.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/admin.users.invite
 */
@Data
@Builder
public class AdminUsersInviteRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    /**
     * A comma-separated list of channel_ids for this user to join. At least one channel is required.
     */
    private List<String> channelIds;

    /**
     * The email address of the person to invite.
     */
    private String email;

    /**
     * Workspace Id
     */
    private String teamId;

    /**
     * An optional message to send to the user in the invite email.
     */
    private String customMessage;

    /**
     * Allow invited user to sign in via email and password.
     * Only available for Enterprise Grid teams via admin invite.
     * (As this parameter does not have the default value, we don't use the primitive value type)
     */
    private Boolean emailPasswordPolicyEnabled;

    /**
     * Timestamp when guest account should be disabled.
     * Only include this timestamp if you invite a guest user and you want their account to expire on a certain date.
     */
    private String guestExpirationTs;

    /**
     * Is this user a multi-channel guest user? (default: false)
     */
    private boolean isRestricted;

    /**
     * Is this user a single channel guest user? (default: false)
     */
    private boolean isUltraRestricted;

    /**
     * Full name of the user.
     */
    private String realName;

    /**
     * Allow this invite to be resent in the future if a user has not signed up yet. (default: false)
     */
    private boolean resend;

}
