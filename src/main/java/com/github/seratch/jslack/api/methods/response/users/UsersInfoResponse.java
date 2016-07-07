package com.github.seratch.jslack.api.methods.response.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.User;
import lombok.Data;

@Data
public class UsersInfoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private User user;
}