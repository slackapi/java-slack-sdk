package com.github.seratch.jslack.shortcut.impl;

import com.github.seratch.jslack.Slack;
import com.github.seratch.jslack.api.methods.SlackApiException;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsHistoryRequest;
import com.github.seratch.jslack.api.methods.request.channels.ChannelsListRequest;
import com.github.seratch.jslack.api.methods.request.chat.ChatPostMessageRequest;
import com.github.seratch.jslack.api.methods.request.reactions.ReactionsAddRequest;
import com.github.seratch.jslack.api.methods.request.search.SearchAllRequest;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsHistoryResponse;
import com.github.seratch.jslack.api.methods.response.channels.ChannelsListResponse;
import com.github.seratch.jslack.api.methods.response.chat.ChatPostMessageResponse;
import com.github.seratch.jslack.api.methods.response.reactions.ReactionsAddResponse;
import com.github.seratch.jslack.api.methods.response.search.SearchAllResponse;
import com.github.seratch.jslack.api.model.Attachment;
import com.github.seratch.jslack.api.model.Channel;
import com.github.seratch.jslack.api.model.Message;
import com.github.seratch.jslack.shortcut.Shortcut;
import com.github.seratch.jslack.shortcut.model.ApiToken;
import com.github.seratch.jslack.shortcut.model.ChannelId;
import com.github.seratch.jslack.shortcut.model.ChannelName;
import com.github.seratch.jslack.shortcut.model.ReactionName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShortcutImpl implements Shortcut {

    private final Optional<ApiToken> apiToken;

    private final Slack slack;

    private List<Channel> channels = new ArrayList<>();

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
        Optional<ChannelId> maybeChannelId = findChannelIdByName(name);
        if (maybeChannelId.isPresent()) {
            ChannelId channelId = maybeChannelId.get();
            ChannelsHistoryResponse response = slack.methods().channelsHistory(ChannelsHistoryRequest.builder()
                    .token(apiToken.get().getValue())
                    .channel(channelId.getValue())
                    .count(1000)
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

    @Override
    public void updateChannelsCache() throws IOException, SlackApiException {
        if (apiToken.isPresent()) {
            if (channels.isEmpty()) {
                ChannelsListResponse response = slack.methods().channelsList(ChannelsListRequest.builder().
                        token(apiToken.get().getValue())
                        .build());
                if (response.isOk()) {
                    channels = response.getChannels();
                }
            }
        } else {
            throw new IllegalStateException("apiToken is absent.");
        }
    }
}
