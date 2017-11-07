package com.github.seratch.jslack.api.methods.response.conversations;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import lombok.Data;

@Data
public class ConversationsArchiveResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;
}
