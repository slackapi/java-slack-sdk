package com.github.seratch.jslack.api.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class BotProfile {

    private String id; // B00000000
    private boolean deleted;
    private String name;
    private Integer updated;
    private String appId; // A00000000
    private Icons icons;
    private String teamId;

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
