package com.github.seratch.jslack.api.methods.response.groups;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class GroupsRenameResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private RenamedChannel channel;

    @Data
    public static class RenamedChannel {
        private String id;
        @SerializedName("is_group")
        private boolean group;
        private String name;
        private Integer created;
    }
}