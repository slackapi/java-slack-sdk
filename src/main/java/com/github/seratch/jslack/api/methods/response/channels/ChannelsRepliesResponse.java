package com.github.seratch.jslack.api.methods.response.channels;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.api.model.ThreadInfo;
import lombok.Data;

import java.util.List;

@Data
public class ChannelsRepliesResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<Message> messages;
    private ThreadInfo threadInfo;
}
