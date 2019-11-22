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
    @Builder.Default
    private String okText = OKAY;
    @Builder.Default
    private String dismissText = DISMISS;
}
