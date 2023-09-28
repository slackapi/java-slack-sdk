package com.slack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ChannelEmailAddress {
    private String teamId;
    private String userId;
    private String conversationId;
    private Integer dateCreated;
    private String address;
    private String name;
    private Icons icons;

    @Data
    public static class Icons {
        @SerializedName("image_36")
        private String image36;
        @SerializedName("image_48")
        private String image48;
        @SerializedName("image_72")
        private String image72;
    }
}
