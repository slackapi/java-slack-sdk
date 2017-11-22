package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class MatchedItem {

    private String type;
    private Channel channel;
    private String user;
    private String username;
    private String ts;
    private String text;
    private String permalink;
    private String name;
    private String preview;
    private String timestamp;
    @SerializedName("thumb_360")
    private String thumb360;
    @SerializedName("url_private")
    private String urlprivate;

    @SerializedName("previous_2")
    private OtherItem previous2;
    private OtherItem previous;
    private OtherItem next;
    @SerializedName("next_2")
    private OtherItem next2;

    @Data
    public static class Channel {
        private String id;
        private String name;
    }

    @Data
    public static class OtherItem {
        private String user;
        private String username;
        private String ts;
        private String text;
        private String type;
    }

}
