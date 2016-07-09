package com.github.seratch.jslack.api.methods.response.reactions;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ReactionsRemoveResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}