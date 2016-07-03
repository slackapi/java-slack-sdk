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
    private String ts;
    @SerializedName("is_starred")
    private boolean starred;
    private List<String> pinnedTo;
    private List<Reaction> reactions;

    @Data
    public static class Reaction {
        private String name;
        private Integer count;
        private List<String> users;
    }
}
