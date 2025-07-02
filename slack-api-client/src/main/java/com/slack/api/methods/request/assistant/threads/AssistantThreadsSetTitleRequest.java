package com.slack.api.methods.request.assistant.threads;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

/**
 * https://docs.slack.dev/reference/methods/assistant.threads.setTitle
 */
@Data
@Builder
public class AssistantThreadsSetTitleRequest implements SlackApiRequest {

    private String token;

    /**
     * Channel ID containing the assistant thread.
     */
    private String channelId;

    /**
     * Message timestamp of the thread of where to set the status.
     */
    private String threadTs;

    /**
     * The title to use for the thread.
     */
    private String title;

}