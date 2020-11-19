package com.slack.api.methods.request.team;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeamAccessLogsRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `admin`
     */
    private String token;

    /**
     * End of time range of logs to include in results (inclusive).
     */
    private Integer before;

    private Integer count;

    private Integer page;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}