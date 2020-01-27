package com.slack.api.methods.response.users;

import com.slack.api.methods.SlackApiResponse;
import com.slack.api.model.Conversation;
import com.slack.api.model.ResponseMetadata;
import lombok.Data;

import java.util.List;

@Data
public class UsersConversationsResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;

    private List<Conversation> channels;
    private ResponseMetadata responseMetadata;
}
