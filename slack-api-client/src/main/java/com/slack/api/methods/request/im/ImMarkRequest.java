package com.slack.api.methods.request.im;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class ImMarkRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `im:write`
     */
    private String token;

    /**
     * Direct message channel to set reading cursor in.
     */
    private String channel;

    /**
     * Timestamp of the most recently seen message.
     */
    private String ts;

}