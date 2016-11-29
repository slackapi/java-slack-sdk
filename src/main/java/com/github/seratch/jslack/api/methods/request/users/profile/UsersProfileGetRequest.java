package com.github.seratch.jslack.api.methods.request.users.profile;

import com.github.seratch.jslack.api.methods.SlackApiRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersProfileGetRequest implements SlackApiRequest {

    private String token;
    private String user;
    private Integer includeLabels;
}