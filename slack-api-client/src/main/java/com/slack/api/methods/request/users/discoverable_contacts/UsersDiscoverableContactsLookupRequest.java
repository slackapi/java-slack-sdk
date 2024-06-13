package com.slack.api.methods.request.users.discoverable_contacts;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.ConversationType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/users.discoverableContacts.lookups
 */
@Data
@Builder
public class UsersDiscoverableContactsLookupRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `conversations:read`
     */
    private String token;

    private String email;
}
