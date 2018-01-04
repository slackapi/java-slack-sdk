package com.github.seratch.jslack.api.methods.request.chat;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.Attachment;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ChatPostEphemeralRequest implements SlackApiRequest {

    private String token;
    private String channel;
    private String text;
    private String user;
    private boolean asUser=true;
    private List<Attachment> attachments;
    private boolean linkNames = true;
    private String parse = "full";
}