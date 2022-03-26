package com.slack.api.methods.request.migration;

import com.slack.api.methods.SlackApiRequest;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * For Enterprise Grid workspaces, map local user IDs to global user IDs.
 *
 * https://api.slack.com/methods/migration.exchange
 */
@Data
@Builder
public class MigrationExchangeRequest implements SlackApiRequest {

    /**
     * Authentication token. Requires scope: `tokens.basic`
     */
    private String token;

    /**
     * Specify `true` to convert `W` global user IDs to workspace-specific `U` IDs. Defaults to `false`.
     */
    private boolean toOld;

    /**
     * A comma-separated list of user ids, up to 400 per request
     */
    private List<String> users;

    /**
     * Required for org-wide apps.
     */
    private String teamId;

}