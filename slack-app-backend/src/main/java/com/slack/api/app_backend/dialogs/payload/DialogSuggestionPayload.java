package com.slack.api.app_backend.dialogs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The payload which is sent when a user clicked on a dialog which has
 * "type": "select" and "data_source": "external"
 * <p>
 *
 * @see <a href="https://api.slack.com/dialogs">Dialogs</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogSuggestionPayload {
    private static final String TYPE = "dialog_suggestion";
    private final String type = TYPE;
    private String token;
    private String actionTs;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private Channel channel;
    private String name;
    private String value;
    private String callbackId;
    private boolean isEnterpriseInstall;
}
