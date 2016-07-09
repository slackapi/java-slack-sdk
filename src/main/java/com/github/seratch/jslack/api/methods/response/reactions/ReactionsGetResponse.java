package com.github.seratch.jslack.api.methods.response.reactions;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Message;
import lombok.Data;

@Data
public class ReactionsGetResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private String type;
    private String channel;
    private Message message;

}