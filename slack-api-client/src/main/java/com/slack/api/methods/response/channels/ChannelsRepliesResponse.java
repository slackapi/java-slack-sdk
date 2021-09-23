package com.slack.api.methods.response.channels;

import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.ThreadInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
public class ChannelsRepliesResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private List<Message> messages;
    private boolean hasMore;

    /**
     * This field seems to be no longer available.
     * To fetch ts to identify its thread, you can use thread_ts of each message in `messages`
     */
    @Deprecated
    private ThreadInfo threadInfo;

    private ResponseMetadata responseMetadata;
}
