package com.slack.api.methods.request.assistant.threads;

import com.slack.api.methods.SlackApiRequest;
import com.slack.api.model.assistant.SuggestedPrompt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * https://api.slack.com/methods/assistant.threads.setSuggestedPrompts
 */
@Data
@Builder
public class AssistantThreadsSetSuggestedPromptsRequest implements SlackApiRequest {

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
     * Title for the prompts. Like Suggested Prompts, Related questions
     */
    private String title;

    /**
     * The suggested prompts.
     */
    private List<SuggestedPrompt> prompts;

}