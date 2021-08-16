package com.slack.api.model.connect;

import lombok.Data;

@Data
public class ConnectChannel {
    private String id;
    private Boolean isIm;
    private Boolean isPrivate;
    private String name;
}
