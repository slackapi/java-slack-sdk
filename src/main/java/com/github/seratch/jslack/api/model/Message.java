package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Message {

    private String type;
    private String channel;
    private String user;
    private String text;
    private List<Attachment> attachments;
    private String ts;
    private String threadTs;
    @SerializedName("is_starred")
    private boolean starred;
    private boolean wibblr;
    private List<String> pinnedTo;
    private List<Reaction> reactions;
    private String username;
    private String subtype;
    private String botId;
}
