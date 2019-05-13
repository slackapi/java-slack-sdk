package com.github.seratch.jslack.api.methods.request.mpim;

import com.github.seratch.jslack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpimRepliesRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `mpim:history`
     */
    private String token;

    /**
     * Multiparty direct message channel to fetch thread from.
     */
    private String channel;

    /**
     * Unique identifier of a thread's parent message.
     */
    private String threadTs;

}