package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://api.slack.com/methods/chat.postMessage
 */
@Data
@Builder
public class ChatPostMessageRequest implements SlackApiRequest {

    private String token;
    private String channel;
    private String text;
    private String parse;
    private Integer linkNames;
    private List<Attachment> attachments;
    private boolean unfurlLinks;
    private boolean unfurlMedia;
    private String username;
    private boolean asUser;
    private boolean mrkdwn;
    private String iconUrl;
    private String iconEmoji;
    private String threadTs;
    private boolean replyBroadcast;
}