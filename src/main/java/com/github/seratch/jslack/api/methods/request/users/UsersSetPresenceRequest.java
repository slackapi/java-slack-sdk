package com.github.seratch.jslack.api.methods.request.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersSetPresenceRequest {

    private String token;
    private String presence;
}