package com.slack.api.methods.request.mpim;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class MpimCloseRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `mpim:write`
     */
    private String token;

    /**
     * MPIM to close.
     */
    private String channel;

}