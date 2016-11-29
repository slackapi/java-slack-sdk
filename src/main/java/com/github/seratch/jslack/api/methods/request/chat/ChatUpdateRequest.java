package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatUpdateRequest implements SlackApiRequest {

    private String token;
    private String ts;
    private String channel;
    private String text;
    private List<Attachment> attachments;
    private String parse;
    private Integer linkNames;
    private boolean asUser;
}