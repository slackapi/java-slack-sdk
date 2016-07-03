package com.github.seratch.jslack.api.methods.request;

import com.github.seratch.jslack.api.model.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatPostMessageRequest {

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
    private String iconUrl;
    private String iconEmoji;
}