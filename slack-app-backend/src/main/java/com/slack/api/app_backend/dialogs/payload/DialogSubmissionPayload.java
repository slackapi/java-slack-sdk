package com.slack.api.app_backend.dialogs.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @see <a href="https://api.slack.com/dialogs">Dialogs</a>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DialogSubmissionPayload {
    private static final String TYPE = "dialog_submission";
    private final String type = TYPE;
    private String callbackId;
    private Map<String, String> submission;
    private String state;
    private Enterprise enterprise;
    private Team team;
    private User user;
    private Channel channel;
    private String actionTs;
    private String token;
    private String responseUrl;
    private boolean isEnterpriseInstall;
}
