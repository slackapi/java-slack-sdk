package com.slack.api.methods.request.admin.functions;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * https://docs.slack.dev/reference/methods/admin.functions.list
 */
@Data
@Builder
public class AdminFunctionsListRequest implements SlackApiRequest {

    /**
     * Authentication token bearing required scopes.
     */
    private String token;

    private List<String> appIds;
    private String cursor;
    private Integer limit;
    private String teamId;
}
