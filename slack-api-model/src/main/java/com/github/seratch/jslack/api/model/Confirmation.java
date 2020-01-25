package com.github.seratch.jslack.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
