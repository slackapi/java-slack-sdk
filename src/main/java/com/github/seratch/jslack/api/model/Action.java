package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Action {
    public static final String BUTTON = "button";

    private String name;
    private String text;
    private String style;
    @Builder.Default private String type = BUTTON;
    private String value;
    private Confirmation confirmation;
    private List<Option> options = new ArrayList<>();
}
