package com.github.seratch.jslack.api.methods.response.chat;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ChatUnfurlResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

}
