package com.github.seratch.jslack.api.audit.request;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionsRequest implements SlackApiRequest {
    private String token;
}
