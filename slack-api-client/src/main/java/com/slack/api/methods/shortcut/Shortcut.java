package com.slack.api.methods.shortcut;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.reactions.ReactionsAddResponse;
import com.slack.api.methods.response.search.SearchAllResponse;
import com.slack.api.methods.shortcut.model.ChannelId;
import com.slack.api.methods.shortcut.model.ChannelName;
import com.slack.api.methods.shortcut.model.ReactionName;
import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Shortcut is an original API provided by this library.
 * As the name tells you, the module provides you more handy APIs than the official API methods.
 */
public interface Shortcut {

    /**
     * Shortcut internally has a cache for channels API response. This method refreshes the cache immediately.
     */
    void updateChannelsCache() throws IOException, SlackApiException;

    /**
     * Returns ChannelId corresponding to a given channel name if exists.
     */
    Optional<ChannelId> findChannelIdByName(ChannelName name) throws IOException, SlackApiException;

    /**
     * Returns a channel's human readable name corresponding to a given channel id if exists.
     */
    Optional<ChannelName> findChannelNameById(ChannelId channelId);

    /**
     * Returns a list of messages in the channel a given name matches.
     */
    List<Message> findRecentMessagesByName(ChannelName name) throws IOException, SlackApiException;

    List<Message> findRecentMessagesByName(ChannelName name, int limit) throws IOException, SlackApiException;

    /**
     * Adds a reaction to a specified message.
     */
    ReactionsAddResponse addReaction(Message message, ReactionName reactionName) throws IOException, SlackApiException;

    /**
     * Returns search result by a given query.
     */
    SearchAllResponse search(String query) throws IOException, SlackApiException;

    /**
     * Posts a message to a given channel.
     */
    ChatPostMessageResponse post(ChannelName channel, String text) throws IOException, SlackApiException;

    ChatPostMessageResponse postAsBot(ChannelName channel, String text) throws IOException, SlackApiException;

    /**
     * Posts a message to a given channel.
     */
    ChatPostMessageResponse post(ChannelName channel, String text, List<Attachment> attachments) throws IOException, SlackApiException;

    ChatPostMessageResponse postAsBot(ChannelName channel, String text, List<Attachment> attachments) throws IOException, SlackApiException;

    /**
     * Posts a message to a given channel.
     */
    ChatPostMessageResponse post(ChannelName channel, List<LayoutBlock> blocks) throws IOException, SlackApiException;

    ChatPostMessageResponse postAsBot(ChannelName channel, List<LayoutBlock> blocks) throws IOException, SlackApiException;

}
