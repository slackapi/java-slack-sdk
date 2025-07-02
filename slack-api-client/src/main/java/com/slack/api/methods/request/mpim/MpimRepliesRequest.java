package com.slack.api.methods.request.mpim;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
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