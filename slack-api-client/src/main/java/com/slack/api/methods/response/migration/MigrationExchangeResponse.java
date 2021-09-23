package com.slack.api.methods.response.migration;

import com.slack.api.methods.SlackApiTextResponse;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MigrationExchangeResponse implements SlackApiTextResponse {

    private boolean ok;
    private String warning;
    private String error;
    private String needed;
    private String provided;
    private transient Map<String, List<String>> httpResponseHeaders;

    /**
     * The workspace/team ID containing the mapped users
     */
    private String teamId;

    /**
     * The enterprise grid organization ID containing the workspace/team.
     */
    private String enterpriseId;

    /**
     * A list of User IDs that cannot be mapped or found
     */
    private List<String> invalidUserIds;

    /**
     * A mapping of provided user IDs with mapped user IDs
     */
    private Map<String, String> userIdMap;

}