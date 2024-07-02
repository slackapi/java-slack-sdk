package com.slack.api.model.connect;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ConnectRequestActor {
    private String id;
    private String name;
    @SerializedName("is_bot")
    private boolean bot;
    private String teamId;
    private String timezone;
    private String realName;
    private String displayName;
}
