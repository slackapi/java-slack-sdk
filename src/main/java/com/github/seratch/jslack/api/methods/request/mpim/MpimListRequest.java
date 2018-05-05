package com.github.seratch.jslack.api.methods.request.mpim;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpimListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `mpim:read`
     */
    private String token;

}