package com.github.seratch.jslack.api.model;

import lombok.Data;

@Data
public class Latest {

    private String type;
    private String user;
    private String text;
    private String ts;
}
