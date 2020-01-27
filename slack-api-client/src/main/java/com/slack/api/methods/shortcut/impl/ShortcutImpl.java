package com.slack.api.methods.shortcut.impl;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.conversations.ConversationsHistoryRequest;
import com.slack.api.methods.request.conversations.ConversationsListRequest;
import com.slack.api.methods.request.reactions.ReactionsAddRequest;
import com.slack.api.methods.request.search.SearchAllRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.methods.response.conversations.ConversationsListResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.methods.response.search.SearchAllResponse;
import com.slack.api.methods.shortcut.Shortcut;
import com.slack.api.methods.shortcut.model.ApiToken;
import com.slack.api.methods.shortcut.model.ChannelId;
import com.slack.api.methods.shortcut.model.ChannelName;
import com.slack.api.methods.shortcut.model.ReactionName;
import com.slack.api.model.Attachment;
import com.slack.api.model.Conversation;
import com.slack.api.model.Message;
import com.slack.api.model.ResponseMetadata;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShortcutImpl implements Shortcut {

    private final Optional<ApiToken> apiToken;

    private final Slack slack;

    public Slack getSlack() {
        return slack;
    }

    private List<Conversation> channels = new ArrayList<>();

    public ShortcutImpl(Slack slack) {
        this.apiToken = Optional.empty();
        this.slack = slack;
    }

    public ShortcutImpl(Slack slack, ApiToken apiToken) {
        this.apiToken = Optional.ofNullable(apiToken);
        this.slack = slack;
    }

    @Override
    public Optional<ChannelId> findChannelIdByName(ChannelName name) throws IOException, SlackApiException {
        if (channels.isEmpty()) {
            updateChannelsCache();
        }
        return channels.stream()
                .filter(c -> c.getName().equals(name.getValue()))
                .findFirst()
                .map(c -> new ChannelId(c.getId()));
    }

    @Override
    public Optional<ChannelName> findChannelNameById(ChannelId channelId) {
        return channels.stream()
                .filter(c -> c.getId().equals(channelId.getValue()))
                .findFirst()
                .map(c -> new ChannelName(c.getName()));
    }

    @Override
    public List<Message> findRecentMessagesByName(ChannelName name) throws IOException, SlackApiException {
        return findRecentMessagesByName(name, 1000);
    }

    @Override
    public List<Message> findRecentMessagesByName(ChannelName name, int limit) throws IOException, SlackApiException {
        Optional<ChannelId> maybeChannelId = findChannelIdByName(name);
        if (maybeChannelId.isPresent()) {
            ChannelId channelId = maybeChannelId.get();
            ConversationsHistoryResponse response = slack.methods().conversationsHistory(ConversationsHistoryRequest.builder()
                    .token(apiToken.get().getValue())
                    .channel(channelId.getValue())
                    .limit(limit)
                    .build());
            if (response.isOk()) {
                response.getMessages().forEach(message -> {
                    // channel in message can bt null in this case...
                    if (message.getChannel() == null) {
                        message.setChannel(channelId.getValue());
                    }
                });
                return response.getMessages();
            } else {
                return Collections.emptyList();
            }
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public ReactionsAddResponse addReaction(Message message, ReactionName reactionName) throws IOException, SlackApiException {
        if (apiToken.isPresent()) {
            return slack.methods().reactionsAdd(ReactionsAddRequest.builder()
                    .token(apiToken.get().getValue())
                    .channel(message.getChannel())
                    .timestamp(message.getTs())
                    .name(reactionName.getValue())
                    .build());
        } else {
            throw new IllegalStateException("apiToken is absent.");
        }
    }

    @Override
    public SearchAllResponse search(String query) throws IOException, SlackApiException {
        if (apiToken.isPresent()) {
            return slack.methods().searchAll(SearchAllRequest.builder()
                    .token(apiToken.get().getValue())
                    .query(query)
                    .count(100)
                    .build());
        } else {
            throw new IllegalStateException("apiToken is absent.");
        }
    }

    @Override
    public ChatPostMessageResponse post(ChannelName channel, String text) throws IOException, SlackApiException {
        return _post(channel, text, Collections.emptyList(), true);
    }

    @Override
    public ChatPostMessageResponse postAsBot(ChannelName channel, String text) throws IOException, SlackApiException {
        return _post(channel, text, Collections.emptyList(), false);
    }

    @Override
    public ChatPostMessageResponse post(ChannelName channel, String text, List<Attachment> attachments) throws IOException, SlackApiException {
        return _post(channel, text, attachments, true);
    }

    @Override
    public ChatPostMessageResponse postAsBot(ChannelName channel, String text, List<Attachment> attachments) throws IOException, SlackApiException {
        return _post(channel, text, attachments, false);
    }

    @Override
    public ChatPostMessageResponse post(ChannelName channel, List<LayoutBlock> blocks) throws IOException, SlackApiException {
        return _post(channel, blocks, true);
    }

    @Override
    public ChatPostMessageResponse postAsBot(ChannelName channel, List<LayoutBlock> blocks) throws IOException, SlackApiException {
        return _post(channel, blocks, false);
    }

    private ChatPostMessageResponse _post(ChannelName channel, String text, List<Attachment> attachments, boolean asUser) throws IOException, SlackApiException {
        if (apiToken.isPresent()) {
            Optional<ChannelId> channelId = findChannelIdByName(channel);
            if (channelId.isPresent()) {
                return slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .token(apiToken.get().getValue())
                        .asUser(asUser)
                        .channel(channelId.get().getValue())
                        .text(text)
                        .attachments(attachments)
                        .build());
            } else {
                throw new IllegalStateException("Unknown channel: " + channel.getValue());
            }
        } else {
            throw new IllegalStateException("apiToken is absent.");
        }
    }

    private ChatPostMessageResponse _post(ChannelName channel, List<LayoutBlock> blocks, boolean asUser) throws IOException, SlackApiException {
        if (apiToken.isPresent()) {
            Optional<ChannelId> channelId = findChannelIdByName(channel);
            if (channelId.isPresent()) {
                return slack.methods().chatPostMessage(ChatPostMessageRequest.builder()
                        .token(apiToken.get().getValue())
                        .asUser(asUser)
                        .channel(channelId.get().getValue())
                        .blocks(blocks)
                        .build());
            } else {
                throw new IllegalStateException("Unknown channel: " + channel.getValue());
            }
        } else {
            throw new IllegalStateException("apiToken is absent.");
        }
    }

    @Override
    public void updateChannelsCache() throws IOException, SlackApiException {
        if (apiToken.isPresent()) {
            if (channels.isEmpty()) {
                String cursor = null;
                do {
                    ConversationsListResponse response = slack.methods()
                            .conversationsList(ConversationsListRequest.builder()
                                    .token(apiToken.get().getValue())
                                    .cursor(cursor)
                                    .build());
                    if (response.isOk()) {
                        channels.addAll(response.getChannels());
                        cursor = Optional.ofNullable(response.getResponseMetadata())
                                .map(ResponseMetadata::getNextCursor)
                                .orElse(null);
                    } else {
                        // if response isn't okay, clear the cursor otherwise it may infinite loop
                        cursor = null;
                    }
                } while (cursor != null && !cursor.isEmpty());
            }
        } else {
            throw new IllegalStateException("apiToken is absent.");
        }
    }
}
