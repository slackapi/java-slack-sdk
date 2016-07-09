package com.github.seratch.jslack.api.model;

import lombok.Data;

@Data
public class Login {

    private String userId;
    private String username;
    private Integer dateFirst;
    private Integer dateLast;
    private Integer count;
    private String ip;
    private String userAgent;
    private String isp;
    private String country;
    private String region;
}
