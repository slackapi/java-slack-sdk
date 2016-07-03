package com.github.seratch.jslack.api.methods.response;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Channel {

    private String id;
    private String name;
    private Integer created;
    private String creator;
    @SerializedName("is_archived")
    private boolean archived;
    @SerializedName("is_member")
    private boolean member;
    @SerializedName("is_general")
    private boolean general;
    private String lastRead;
    private Latest latest;
    private Integer unreadCount;
    private Integer unreadCountDisplay;
    private List<String> members;
    private Topic topic;
    private Purpose purpose;

    @Data
    public static class Topic {
        private String value;
        private String creator;
        private Integer lastSet;
    }

    @Data
    public static class Purpose {
        private String value;
        private String creator;
        private Integer lastSet;
    }
}
