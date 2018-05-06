package com.github.seratch.jslack.api.methods.response.im;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Message;
import lombok.Data;

import java.util.List;

@Data
public class ImRepliesResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<Message.MessageRoot> messages;
}