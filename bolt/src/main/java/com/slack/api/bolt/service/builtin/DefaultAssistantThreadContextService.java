package com.slack.api.bolt.service.builtin;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.service.AssistantThreadContextService;
import com.slack.api.methods.MethodsClient;
import com.slack.api.model.Message;
import com.slack.api.model.assistant.AssistantThreadContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultAssistantThreadContextService implements AssistantThreadContextService  {
    private Context context;
    private MethodsClient client;

    public DefaultAssistantThreadContextService(Context context) {
        this.context = context;
        this.client = context.client();
    }

    @Override
    public Optional<AssistantThreadContext> findCurrentContext(String channelId, String threadTs) {
        Optional<Message> firstReplyMessage = this.findFirstReplyMessage(channelId, threadTs);
        if (firstReplyMessage.isPresent() && firstReplyMessage.get().getMetadata() != null) {
            Map<String, Object> context = firstReplyMessage.get().getMetadata().getEventPayload();
            if (context != null && !context.isEmpty()) {
                return Optional.of(AssistantThreadContext.builder()
                        .enterpriseId(context.get("enterpriseId") != null ? context.get("enterpriseId").toString() : null)
                        .teamId(context.get("teamId") != null ? context.get("teamId").toString() : null)
                        .channelId(context.get("channelId") != null ? context.get("channelId").toString() : null)
                        .build());
            }
        }
        return Optional.empty();
    }

    @Override
    public void saveCurrentContext(String channelId, String threadTs, AssistantThreadContext context) {
        Optional<Message> firstReplyMessage = this.findFirstReplyMessage(channelId, threadTs);
        if (firstReplyMessage.isPresent()) {
            Message message = firstReplyMessage.get();
            try {
                Map<String, Object> payload = new HashMap<>();
                payload.put("enterpriseId", context.getEnterpriseId());
                payload.put("teamId", context.getTeamId());
                payload.put("channelId", context.getChannelId());

                this.client.chatUpdate(r -> r
                        .channel(channelId)
                        .ts(message.getTs())
                        .text(message.getText())
                        .metadata(Message.Metadata.builder()
                                .eventType("assistant_thread")
                                .eventPayload(payload)
                                .build())
                );
            } catch (Exception e) {
                this.context.logger.error("Failed to update the first reply: {e}", e);
            }
        }
    }

    private Optional<Message> findFirstReplyMessage(String channelId, String threadTs) {
        try {
            List<Message> messages = this.client.conversationsReplies(r -> r
                    .channel(channelId)
                    .ts(threadTs)
                    .oldest(threadTs)
                    .includeAllMetadata(true)
                    .limit(4)
            ).getMessages();
            if (messages != null) {
                for (Message message : messages) {
                    if (message.getSubtype() == null && message.getUser().equals(this.context.getBotUserId())) {
                        return Optional.of(message);
                    }
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
