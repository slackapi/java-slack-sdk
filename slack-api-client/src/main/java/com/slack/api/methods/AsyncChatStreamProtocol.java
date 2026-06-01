package com.slack.api.methods;

import com.slack.api.methods.request.chat.ChatAppendStreamRequest;
import com.slack.api.methods.request.chat.ChatStartStreamRequest;
import com.slack.api.methods.request.chat.ChatStopStreamRequest;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;

import java.util.List;
import java.util.concurrent.CompletableFuture;

class AsyncChatStreamProtocol {

    private final AsyncMethodsClient client;
    private final String channel;
    private final String threadTs;
    private final String recipientTeamId;
    private final String recipientUserId;

    AsyncChatStreamProtocol(
            AsyncMethodsClient client,
            String channel,
            String threadTs,
            String recipientTeamId,
            String recipientUserId
    ) {
        this.client = client;
        this.channel = channel;
        this.threadTs = threadTs;
        this.recipientTeamId = recipientTeamId;
        this.recipientUserId = recipientUserId;
    }

    CompletableFuture<ChatStartStreamResponse> startStream(String markdownText) {
        return client.chatStartStream(ChatStartStreamRequest.builder()
                .channel(channel)
                .threadTs(threadTs)
                .recipientTeamId(recipientTeamId)
                .recipientUserId(recipientUserId)
                .markdownText(markdownText)
                .build());
    }

    CompletableFuture<ChatAppendStreamResponse> appendStream(String streamTs, String markdownText) {
        return client.chatAppendStream(ChatAppendStreamRequest.builder()
                .channel(channel)
                .ts(streamTs)
                .markdownText(markdownText)
                .build());
    }

    CompletableFuture<ChatStopStreamResponse> stopStream(
            String streamTs,
            String markdownText,
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) {
        return client.chatStopStream(ChatStopStreamRequest.builder()
                .channel(channel)
                .ts(streamTs)
                .markdownText(markdownText)
                .blocks(blocks)
                .metadata(metadata)
                .build());
    }
}
