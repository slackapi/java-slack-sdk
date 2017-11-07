package com.github.seratch.jslack.api.methods.response.channels;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ChannelsRenameResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private RenamedChannel channel;

    @Data
    public static class RenamedChannel {
        private String id;
        @SerializedName("is_channel")
        private boolean channel;
        private String name;
        private int created;
    }

}
