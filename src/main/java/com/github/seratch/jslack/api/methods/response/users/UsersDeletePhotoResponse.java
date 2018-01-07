package com.github.seratch.jslack.api.methods.response.users;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class UsersDeletePhotoResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}
