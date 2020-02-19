package com.slack.api.bolt.context;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

public interface SayUtility {

    String getChannelId();

    MethodsClient client();

    default ChatPostMessageResponse say(String text) throws IOException, SlackApiException {
        verifyChannelId();
        ChatPostMessageResponse response = client().chatPostMessage(ChatPostMessageRequest.builder()
                .text(text)
                .channel(getChannelId())
                .build()
        );
        return response;
    }

    default ChatPostMessageResponse say(List<LayoutBlock> blocks) throws IOException, SlackApiException {
        verifyChannelId();
        ChatPostMessageResponse response = client().chatPostMessage(ChatPostMessageRequest.builder()
                .blocks(blocks)
                .channel(getChannelId())
                .build()
        );
        return response;
    }

    default void verifyChannelId() {
        if (getChannelId() == null || getChannelId().trim().isEmpty()) {
            throw new IllegalStateException("This request doesn't have a channel to post reply to.");
        }
    }

}
