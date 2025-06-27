package com.slack.api.methods.request.mpim;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class MpimMarkRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `mpim:write`
     */
    private String token;

    /**
     * multiparty direct message channel to set reading cursor in.
     */
    private String channel;

    /**
     * Timestamp of the most recently seen message.
     */
    private String ts;

}