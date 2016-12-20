package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Action {
    public static final String BUTTON = "button";

    private String name;
    private String text;
    private String style;
    private final String type = BUTTON;
    private String value;
    private ConfirmationMessage confirmationMessage;
}
