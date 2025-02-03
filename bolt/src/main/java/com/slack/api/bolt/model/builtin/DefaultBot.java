package com.slack.api.bolt.model.builtin;

import com.slack.api.bolt.model.Bot;
import lombok.Data;

/**
 * The default data class for the Bot interface.
 */
@Data
public class DefaultBot implements Bot {

    private String appId;
    private String enterpriseId;
    private String enterpriseName;
    private String teamId;
    private String teamName;

    private Boolean isEnterpriseInstall;
    private String enterpriseUrl;
    private String tokenType;

    private String scope;

    private String botId;
    private String botUserId;
    private String botScope;
    private String botAccessToken;
    private String botRefreshToken;
    private Long botTokenExpiresAt;

    private Long installedAt;

}
