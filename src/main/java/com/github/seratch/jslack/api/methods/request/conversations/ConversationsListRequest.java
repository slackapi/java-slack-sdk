package com.github.seratch.jslack.api.methods.request.conversations;

import java.util.List;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.ConversationType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationsListRequest implements SlackApiRequest {
    
    private String token;
    private String cursor;
    private boolean excludeArchived;
    private Integer limit;
    private List<ConversationType> types;

}
