package com.github.seratch.jslack.api.scim.response;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class UsersDeleteResponse implements SlackApiResponse {
    private boolean ok;
    private String warning;
    private String error;
}
