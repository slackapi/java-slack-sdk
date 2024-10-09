package com.slack.api.bolt.context.builtin;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.FunctionUtility;
import com.slack.api.bolt.context.SayUtility;
import com.slack.api.bolt.service.AssistantThreadContextService;
import com.slack.api.bolt.util.BuilderConfigurator;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetStatusResponse;
import com.slack.api.methods.response.asssistant.threads.AssistantThreadsSetSuggestedPromptsResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.Message;
import com.slack.api.model.assistant.AssistantThreadContext;
import com.slack.api.model.assistant.SuggestedPrompt;
import com.slack.api.model.block.LayoutBlock;
import lombok.*;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class EventContext extends Context implements SayUtility, FunctionUtility {

    private String channelId;

    // For assistant thread events
    private String threadTs;
    private AssistantThreadContext threadContext;
    private AssistantThreadContextService threadContextService;
    private boolean assistantThreadEvent;

    private Message.Metadata buildMetadata() {
        return Message.Metadata.builder()
                .eventType("assistant_thread")
                .eventPayload(this.getThreadContext() != null ? this.getThreadContext().toMap() : null)
                .build();
    }

    @Override
    public ChatPostMessageResponse say(String text) throws IOException, SlackApiException {
        if (isAssistantThreadEvent()) {
            return this.client().chatPostMessage(r -> r
                    .channel(this.getChannelId())
                    .threadTs(this.getThreadTs())
                    .text(text)
                    .metadata(this.buildMetadata())
            );
        } else {
            return SayUtility.super.say(text);
        }
    }

    @Override
    public ChatPostMessageResponse say(List<LayoutBlock> blocks) throws IOException, SlackApiException {
        if (isAssistantThreadEvent()) {
            return this.client().chatPostMessage(r -> r
                    .channel(this.getChannelId())
                    .threadTs(this.getThreadTs())
                    .blocks(blocks)
                    .metadata(this.buildMetadata())
            );
        } else {
            return SayUtility.super.say(blocks);
        }
    }

    @Override
    public ChatPostMessageResponse say(String text, List<LayoutBlock> blocks) throws IOException, SlackApiException {
        if (isAssistantThreadEvent()) {
            return this.client().chatPostMessage(r -> r
                    .channel(this.getChannelId())
                    .threadTs(this.getThreadTs())
                    .text(text)
                    .blocks(blocks)
                    .metadata(this.buildMetadata())
            );
        } else {
            return SayUtility.super.say(text, blocks);
        }
    }

    @Override
    public ChatPostMessageResponse say(BuilderConfigurator<ChatPostMessageRequest.ChatPostMessageRequestBuilder> request) throws IOException, SlackApiException {
        if (isAssistantThreadEvent()) {
            ChatPostMessageRequest params = request.configure(ChatPostMessageRequest.builder()).build();
            params.setChannel(this.getChannelId());
            params.setThreadTs(this.getThreadTs());
            params.setMetadata(this.buildMetadata());
            return this.client().chatPostMessage(params);
        } else {
            return super.say(request);
        }
    }

    public AssistantThreadsSetStatusResponse setStatus(String status) throws IOException, SlackApiException {
        if (isAssistantThreadEvent()) {
            return this.client().assistantThreadsSetStatus(r -> r
                    .channelId(this.getChannelId())
                    .threadTs(this.getThreadTs())
                    .status(status)
            );
        } else {
            throw new IllegalStateException("This utility is only available for Assistant feature enabled app!");
        }
    }

    public AssistantThreadsSetSuggestedPromptsResponse setSuggestedPrompts(List<SuggestedPrompt> prompts) throws IOException, SlackApiException {
        if (isAssistantThreadEvent()) {
            return this.client().assistantThreadsSetSuggestedPrompts(r -> r
                    .channelId(this.getChannelId())
                    .threadTs(this.getThreadTs())
                    .prompts(prompts)
            );
        } else {
            throw new IllegalStateException("This utility is only available for Assistant feature enabled app!");
        }
    }

    // X-Slack-Retry-Num: 2 in HTTP Mode
    // "retry_attempt": 0, in Socket Mode
    private Integer retryNum;
    // X-Slack-Retry-Reason: http_error in HTTP Mode
    // "retry_reason": "timeout", in Socket Mode
    private String retryReason;
}
