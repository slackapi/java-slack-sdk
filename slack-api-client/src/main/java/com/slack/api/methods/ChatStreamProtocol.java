package com.slack.api.methods;

import com.slack.api.methods.request.chat.ChatAppendStreamRequest;
import com.slack.api.methods.request.chat.ChatStartStreamRequest;
import com.slack.api.methods.request.chat.ChatStopStreamRequest;
import com.slack.api.methods.response.chat.ChatAppendStreamResponse;
import com.slack.api.methods.response.chat.ChatStartStreamResponse;
import com.slack.api.methods.response.chat.ChatStopStreamResponse;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

class ChatStreamProtocol {

    private final MethodsClient client;
    private final String channel;
    private final String threadTs;
    private final String recipientTeamId;
    private final String recipientUserId;

    ChatStreamProtocol(
            MethodsClient client,
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

    ChatStartStreamResponse startStream(String markdownText) throws IOException, SlackApiException {
        return client.chatStartStream(ChatStartStreamRequest.builder()
                .channel(channel)
                .threadTs(threadTs)
                .recipientTeamId(recipientTeamId)
                .recipientUserId(recipientUserId)
                .markdownText(markdownText)
                .build());
    }

    ChatAppendStreamResponse appendStream(String streamTs, String markdownText) throws IOException, SlackApiException {
        return client.chatAppendStream(ChatAppendStreamRequest.builder()
                .channel(channel)
                .ts(streamTs)
                .markdownText(markdownText)
                .build());
    }

    ChatStopStreamResponse stopStream(
            String streamTs,
            String markdownText,
            List<LayoutBlock> blocks,
            Message.Metadata metadata
    ) throws IOException, SlackApiException {
        return client.chatStopStream(ChatStopStreamRequest.builder()
                .channel(channel)
                .ts(streamTs)
                .markdownText(markdownText)
                .blocks(blocks)
                .metadata(metadata)
                .build());
    }
}
