package com.slack.api.methods.request.im;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Deprecated // https://docs.slack.dev/changelog/2020-01-deprecating-antecedents-to-the-conversations-api
@Data
@Builder
public class ImOpenRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `im:write`
     */
    private String token;

    /**
     * User to open a direct message channel with.
     */
    private String user;

    /**
     * Boolean, indicates you want the full IM channel definition in the response.
     */
    private boolean returnIm;

    /**
     * Set this to `true` to receive the locale for this im. Defaults to `false`
     */
    private boolean includeLocale;

}