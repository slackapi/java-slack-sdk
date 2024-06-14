package com.slack.api.methods.response.users.discoverable_contacts;

import com.google.gson.annotations.SerializedName;
import com.slack.api.methods.SlackApiTextResponse;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UsersDiscoverableContactsLookupResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    private UserContact user;

    private ResponseMetadata responseMetadata;

    @Data
    public static class UserContact {
        @SerializedName("is_discoverable")
        private boolean discoverable;
    }
}
