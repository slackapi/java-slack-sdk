package com.github.seratch.jslack.api.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Confirmation {
    public static final String OKAY = "Okay";
    public static final String DISMISS = "Cancel";
    private String title;
    private String text;
    private String ok_text = OKAY;
    private String dismiss_text = DISMISS;
}
