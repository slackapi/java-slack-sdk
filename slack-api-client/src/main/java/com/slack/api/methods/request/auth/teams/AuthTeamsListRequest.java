package com.slack.api.methods.request.auth.teams;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTeamsListRequest implements SlackApiRequest {

    private String token;
    private String cursor;
    private Integer limit;
    private Boolean includeIcon;

}