package com.slack.api.model.connect;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class InvitePayload {
    private String invite_type;
    @SerializedName("is_sponsored")
    private boolean sponsored;
    @SerializedName("is_external_limited")
    private boolean externalLimited;
    private Channel channel;

    @Data
    public static class Channel {
        private String id;
        @SerializedName("is_private")
        private boolean privateChannel;
        private String name;
        @SerializedName("is_im")
        private boolean im;
    }
}
