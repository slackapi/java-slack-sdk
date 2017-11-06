package com.github.seratch.jslack.api.methods.response.conversations;

import java.util.List;

import com.github.seratch.jslack.api.model.ResponseMetadata;

import lombok.Data;

@Data
public class ConversationsMembersResponse {
    
    private boolean ok;
    private String warning;
    private String error;
    
    private List<String> members;
    private ResponseMetadata responseMetadata;
}
