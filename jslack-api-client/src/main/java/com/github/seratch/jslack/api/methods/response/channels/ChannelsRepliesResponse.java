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
    private String needed;
    private String provided;

    private List<Message> messages;
    private boolean hasMore;

    /**
     * This field seems to be no longer available.
     * To fetch ts to identify its thread, you can use thread_ts of each message in `messages`
     */
    @Deprecated
    private ThreadInfo threadInfo;
}
