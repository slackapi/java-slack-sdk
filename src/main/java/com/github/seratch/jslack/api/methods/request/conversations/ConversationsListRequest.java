package com.github.seratch.jslack.api.methods.request.conversations;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import com.github.seratch.jslack.api.model.ConversationType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConversationsListRequest implements SlackApiRequest {

    private String token;
    private String cursor;
    private boolean excludeArchived;
    private Integer limit;
    private List<ConversationType> types;

}
