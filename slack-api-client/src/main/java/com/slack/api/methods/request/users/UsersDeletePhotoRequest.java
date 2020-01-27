package com.slack.api.methods.request.users;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * Delete the user profile photo
 */
@Data
@Builder
public class UsersDeletePhotoRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `users.profile:write`
     */
    private String token;

}
