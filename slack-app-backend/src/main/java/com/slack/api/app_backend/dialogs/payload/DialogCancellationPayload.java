package com.slack.api.app_backend.dialogs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @see <a href="https://api.slack.com/dialogs">Dialogs</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogCancellationPayload {
    private static final String TYPE = "dialog_cancellation";
    private final String type = TYPE;
    private String token;
    private String actionTs;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private Channel channel;
    private String callbackId;
    private String responseUrl;
    private String state;
    private boolean isEnterpriseInstall;
}
