package com.github.seratch.jslack.api.methods.request.users.profile;

import com.github.seratch.jslack.api.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersProfileSetRequest {

    private String token;
    private String user;

    /**
     * Collection of key:value pairs presented as a URL-encoded JSON hash.
     */
    private User.Profile profile;

    /**
     * Name of a single key to set. Usable only if profile is not passed.
     */
    private String name;

    /**
     * Value to set a single key to. Usable only if profile is not passed.
     */
    private String value;
}