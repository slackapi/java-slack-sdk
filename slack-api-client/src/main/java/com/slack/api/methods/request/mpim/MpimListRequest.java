package com.slack.api.methods.request.mpim;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://api.slack.com/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class MpimListRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `mpim:read`
     */
    private String token;

}