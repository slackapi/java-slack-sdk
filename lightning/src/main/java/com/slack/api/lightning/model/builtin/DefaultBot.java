package com.slack.api.lightning.model.builtin;

import com.slack.api.lightning.model.Bot;
import lombok.Data;

@Data
public class DefaultBot implements Bot {

    private String enterpriseId;
    private String teamId;
    private String teamName;

    private String scope;

    private String botId;
    private String botUserId;
    private String botScope;
    private String botAccessToken;

    private Long installedAt;
}
