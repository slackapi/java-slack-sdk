package com.github.seratch.jslack.api.methods.response.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.slack.api.model.User;
import lombok.Data;

@Data
public class UsersSetPhotoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private User.Profile profile;
}
