package com.github.seratch.jslack.api.methods.response.reactions;

import com.github.seratch.jslack.api.methods.SlackApiResponse;
import com.github.seratch.jslack.api.model.MessageItem;
import com.github.seratch.jslack.api.model.Paging;
import lombok.Data;

import java.util.List;

@Data
public class ReactionsListResponse implements SlackApiResponse {

    private boolean ok;
    private String warning;
    private String error;

    private List<MessageItem> items;
    private Paging paging;
}